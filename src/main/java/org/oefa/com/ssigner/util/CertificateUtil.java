package org.oefa.com.ssigner.util;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.oefa.com.ssigner.configuration.ApplicationConfiguration;
import org.oefa.com.ssigner.configuration.ApplicationConstants;
import org.oefa.com.ssigner.domain.CertificateModel;
import org.oefa.com.ssigner.domain.util.Log;
import org.oefa.com.ssigner.exception.CrlException;
import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.CertificateFactory;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.stream.Collectors;

public class CertificateUtil {

    public static List<CertificateModel> certificateList = null;

    public static void loadCertificates() throws Exception{
        certificateList = getCertificates();

    }

    public static ArrayList<String> getUserCertificateList(String userDNI){
        return certificateList
                .stream()
                .filter(cert -> cert.getNumeroDocumento().equals(userDNI))
                .map(CertificateModel::getAlias)
                .collect(Collectors.toCollection(ArrayList::new));

    }

    public static ArrayList<String> reloadCertificateList(String userDNI) throws Exception{
        return getCertificates()
                .stream()
                .filter(cert -> cert.getNumeroDocumento().equals(userDNI))
                .map(CertificateModel::getAlias)
                .collect(Collectors.toCollection(ArrayList::new));

    }
    public static CertificateModel getCertificateToSignByAlias(String alias) throws Exception{
        CertificateModel cert = certificateList
                .stream()
                .filter(c -> c.getAlias().equals(alias))
                .findFirst()
                .orElseThrow();


        Security.addProvider(new BouncyCastleProvider());
        KeyStore ks = KeyStore.getInstance(ApplicationConstants.KEY_STORE);
        ks.load(null, null);

        PrivateKey key = (PrivateKey) ks.getKey(alias, null);
        java.security.cert.Certificate[] chain = ks.getCertificateChain(alias);

        if(key == null || chain == null)
            throw new Exception("Error al obtener la key o chain del certificado");

        cert.setChain(chain);
        cert.setKey(key);

        return cert;
    }

    private static List<CertificateModel> getCertificates() throws Exception {
        if (!ApplicationConstants.OS.contains("win"))
            return Collections.emptyList();

        Optional<KeyStore> ksOpt = loadKeyStore(ApplicationConstants.KEY_STORE);
        if(ksOpt.isEmpty())
            return Collections.emptyList();

        return buildAndGetCertificates(ksOpt.get());
    }


    private static List<CertificateModel> buildAndGetCertificates(KeyStore keyStore) throws Exception {
        List<CertificateModel> list = new ArrayList<>();
        X509CRL crl = loadServerCrl();

        Enumeration<String> certificatesListFromKs = keyStore.aliases();
        while(certificatesListFromKs.hasMoreElements()){
            Optional<CertificateModel> certificate = buildAndValidCertificate(certificatesListFromKs.nextElement(), keyStore, crl);
            certificate.ifPresent(list::add);
        }

        return list;

    }


    private static Optional<CertificateModel> buildAndValidCertificate(String alias, KeyStore keyStore, X509CRL crl) throws Exception {

        X509Certificate x509Certificate = (X509Certificate) keyStore.getCertificate(alias);
        if(!isCertificateValid(x509Certificate, crl))
            return Optional.empty();
        try {
            String nombre = getCertUserName(x509Certificate);
            String numeroDocumento = getSerialNumber(x509Certificate);
            String creationDate = ApplicationConstants.DATE_FORMAT.format(x509Certificate.getNotBefore().getTime());
            String expirationDate = ApplicationConstants.DATE_FORMAT.format(x509Certificate.getNotAfter().getTime());
            CertificateModel certificate = new CertificateModel(alias, nombre, creationDate, expirationDate, numeroDocumento);

            return Optional.of(certificate);
        }catch (Exception e){
            return Optional.empty();
        }

    }


    private static Optional<KeyStore> loadKeyStore(String instance) throws Exception{
        KeyStore ks = KeyStore.getInstance(instance);
        ks.load(null);
        return Optional.of(ks);

    }


    private static boolean isCertificateValid(X509Certificate certificate, X509CRL crl){
        try {
            certificate.checkValidity();
            if (crl.isRevoked(certificate))
                throw new Exception("Certificado revocado");

            return true;

        }catch (Exception e){
            return false;
        }

    }


    private static String getCertUserName(X509Certificate certificate) throws Exception {
        String dn = certificate.getSubjectDN().toString();
        LdapName ln = new LdapName(dn);
        Rdn rdn = ln.getRdns().stream().filter(r -> r.getType().equalsIgnoreCase("CN")).findFirst().orElseThrow();

        return rdn.getValue().toString();

    }


    private static String getSerialNumber(X509Certificate certificate) throws Exception {
        String dn = certificate.getSubjectDN().toString();
        String[] tagserie = new String[]{"SERIALNUMBER"};

        LdapName ln = new LdapName(dn);
        Rdn rdn = ln.getRdns().stream().filter(r -> r.getType().equalsIgnoreCase(tagserie[0])).findFirst().orElseThrow();
        String value = rdn.getValue().toString();

        return value.contains("DNI") ? value.split(":")[1] : value;

    }

    private static X509CRL loadServerCrl() throws CrlException {
        try {
            URL url = new URL(ApplicationConfiguration.getKey("CRL_URL_SERVER"));
            LogUtil.setInfo(new Log("Obteniendo crl desde: " + url, CertificateUtil.class.getName()));

            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");

            InputStream inputStream = url.openStream();
            X509CRL crl = (X509CRL) certificateFactory.generateCRL(inputStream);
            inputStream.close();

            return crl;

        }catch (Exception e){
            throw new CrlException("Error obteniendo crl");
        }
    }
}
