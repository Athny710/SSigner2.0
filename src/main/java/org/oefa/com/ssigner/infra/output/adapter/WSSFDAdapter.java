package org.oefa.com.ssigner.infra.output.adapter;

import org.bouncycastle.util.encoders.Base64;
import org.oefa.com.ssigner.configuration.ApplicationConfiguration;
import org.oefa.com.ssigner.configuration.ApplicationConstants;
import org.oefa.com.ssigner.domain.FileModel;
import org.oefa.com.ssigner.domain.SignConfigurationModel;
import org.oefa.com.ssigner.domain.rest.*;
import org.oefa.com.ssigner.domain.util.Log;
import org.oefa.com.ssigner.infra.output.port.RestPort;
import org.oefa.com.ssigner.util.LogUtil;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import com.google.gson.Gson;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.Key;
import java.security.Signature;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class WSSFDAdapter implements RestPort {


    private final String urlServer = ApplicationConfiguration.getKey("SSFD_URL_SERVER");
    private final RestTemplate restTemplate = new RestTemplate();
    private final Gson gson = new Gson();


    @Override
    public SignConfigurationModel getSignConfiguration() throws Exception {
        GrupoProcesFirma grupoProcesoFirma = this.getServerSignInformation();

        SignConfigurationModel signConfiguration = this.getConfigurationFromProperties(grupoProcesoFirma.getBytesConf(), grupoProcesoFirma.getFechaRegistro());
        signConfiguration.setFilesBytes(
                grupoProcesoFirma.getListDocs()
                        .stream()
                        .map(DocProcesoFirma::getBytes)
                        .collect(Collectors.toCollection(ArrayList::new))
        );

        SignatureImage signatureImage = this.getSignatureImage(signConfiguration);
        if(signatureImage.getEstado() == 0)
            signConfiguration.setImagenFirma(signatureImage.getImagen());

        return signConfiguration;

    }


    @Override
    public void uploadFilesSigned(ArrayList<FileModel> fileList) throws Exception {
        String url = this.urlServer + "uploadGrupoFirmado/";

        ArrayList<byte[]> fileSignedBytes = fileList.stream().
                map(FileModel::getBytes).
                collect(Collectors.toCollection(ArrayList::new));

        ArrayList<String> fileSignedNames = fileList.stream().
                map(FileModel::getName).
                collect(Collectors.toCollection(ArrayList::new));

        GrupoFirmado grupoFirmado = new GrupoFirmado(
                Integer.parseInt(ApplicationConfiguration.ID_GROUP),
                fileSignedNames,
                fileSignedBytes
        );

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "application/json");

        HttpEntity<GrupoFirmado> requestEntity = new HttpEntity<>(grupoFirmado, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);

        if(!response.getStatusCode().is2xxSuccessful())
            throw new Exception("Error conectando al servicio: " + url);

    }


    @Override
    public void cancelSignProcess() throws Exception {
        String url = this.urlServer + "cancelarGrupoProcesoFirma/" + ApplicationConfiguration.ID_GROUP;
        LogUtil.setInfo(new Log("Cancelando el proceso de firma: " + url, this.getClass().getName()));

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, null, String.class);
        if(!response.getStatusCode().is2xxSuccessful())
            throw new Exception("Error conectando al servicio: " + url);

    }


    private GrupoProcesFirma getServerSignInformation() throws Exception {
        String url = this.urlServer + "verificarNuevoGrupoFirmado/" + ApplicationConfiguration.ID_CLIENT;

        ResponseEntity<String> response =  restTemplate.getForEntity(url, String.class);
        if(!response.getStatusCode().is2xxSuccessful()){
            throw new Exception("Error conectando al servicio: " + url);
        }

        ResponseProcesoFirma responseWSEntity =  gson.fromJson(response.getBody(), ResponseProcesoFirma.class);
        if(!responseWSEntity.isStatus()) {
            throw new Exception(responseWSEntity.getMessage());
        }

        return responseWSEntity.getGpf();

    }


    private SignatureImage getSignatureImage(SignConfigurationModel signConfiguration) throws Exception{
        String url = this.urlServer + "devolverImage/" + signConfiguration.getProceso() + "/" + signConfiguration.getUsuarioId() + "/" + signConfiguration.getTipoFirma();

        ResponseEntity<String> response =  restTemplate.getForEntity(url, String.class);
        if(!response.getStatusCode().is2xxSuccessful())
            throw new Exception("Error conectando al servicio: " + url);

        return gson.fromJson(response.getBody(), SignatureImage.class);

    }


    private SignConfigurationModel getConfigurationFromProperties(byte[] confBytes, String fechaRegistro) throws Exception {
        Properties p = this.loadUserConfiguration(confBytes);

        boolean visible = p.getProperty("firmavisible").equals("true");
        boolean selloTiempo = p.getProperty("sellotiempo").equals("1");
        boolean ltv = p.getProperty("tipo.duracion").equals("1") && selloTiempo;
        String selloTiempoUrl = selloTiempo ? p.getProperty("urlTsa") : "";
        String selloTiempoUser = selloTiempo ? p.getProperty("usuariotsa") : "";
        String selloTiempoPass = selloTiempo ? p.getProperty("clavetsa") : "";
        String locacion = p.getProperty("locacion");
        String cargo = p.getProperty("cargo");
        String razon = p.getProperty("motivo");
        String proceso = p.getProperty("proceso_id");
        String usuarioId = p.getProperty("usuario_id");
        String dni = p.getProperty("DNI");
        String tipoFirma = p.getProperty("firmado.visado");
        List<Float> listX = Arrays.stream(p.getProperty("x").split(Pattern.quote(">"))).map(Float::parseFloat).collect(Collectors.toList());
        List<Float> listY = Arrays.stream(p.getProperty("y").split(Pattern.quote(">"))).map(Float::parseFloat).collect(Collectors.toList());
        List<Float> listHeight = Arrays.stream(p.getProperty("alto").split(Pattern.quote(">"))).map(Float::parseFloat).collect(Collectors.toList());
        List<Float> listWidth = Arrays.stream(p.getProperty("ancho").split(Pattern.quote(">"))).map(Float::parseFloat).collect(Collectors.toList());
        List<Integer> listPage = Arrays.stream(p.getProperty("pagina").split(Pattern.quote(">"))).map(Integer::parseInt).collect(Collectors.toList());
        List<String> listNames = Arrays.stream(p.getProperty("nombrePdf").split(Pattern.quote(">"))).collect(Collectors.toList());

        ApplicationConfiguration.DNI_CLIENT = dni;

        String texto = "Firmado digitalmente por: # " + "\n";
        if (!cargo.equals(""))
            texto = texto + "Cargo: " + cargo + "\n";
        if (!locacion.equals(""))
            texto = texto + "Lugar: " + locacion + "\n";
        if(!razon.equals(""))
            texto = texto + "Motivo: " + razon + "\n";
        texto = texto +"Fecha/Hora: " + fechaRegistro;

        return new SignConfigurationModel(
                visible,
                ltv,
                selloTiempo,
                selloTiempoUrl,
                selloTiempoUser,
                this.decryptTsaPassword(selloTiempoPass),
                texto,
                locacion,
                razon,
                proceso,
                usuarioId,
                dni,
                tipoFirma,
                new ArrayList<>(listX),
                new ArrayList<>(listY),
                new ArrayList<>(listWidth),
                new ArrayList<>(listHeight),
                new ArrayList<>(listPage),
                new ArrayList<>(listNames)
        );
    }

    private String decryptTsaPassword(String encryptedPassword) throws Exception{
        if (encryptedPassword == null || encryptedPassword.length() == 0)
            return "";

        Key key = new SecretKeySpec(ApplicationConstants.TSA_PASS_HASH_KEY, "AES");
        Cipher c = Cipher.getInstance("AES");
        c.init(2, key);
        byte[] decodedValue = c.doFinal(Base64.decode(encryptedPassword));

        return new String(decodedValue);

    }

    private Properties loadUserConfiguration(byte[] propertiesBytes) throws Exception{
        Properties properties = new Properties();
        InputStream input = new ByteArrayInputStream(propertiesBytes);
        properties.load(input);

        return properties;

    }

}
