package org.oefa.com.ssigner.infra.output.adapter;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.StampingProperties;
import com.itextpdf.signatures.*;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.oefa.com.ssigner.configuration.ApplicationConstants;
import org.oefa.com.ssigner.domain.CertificateModel;
import org.oefa.com.ssigner.domain.FileModel;
import org.oefa.com.ssigner.domain.SignConfigurationModel;
import org.oefa.com.ssigner.domain.util.Log;
import org.oefa.com.ssigner.util.LogUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class IText7Adapter {

    private final SignConfigurationModel signConfigurationModel;

    public IText7Adapter(SignConfigurationModel signConfigurationModel){
        this.signConfigurationModel = signConfigurationModel;

    }


    public ArrayList<FileModel> signFiles(CertificateModel certificate) throws Exception {
        ArrayList<FileModel> fileList = new ArrayList<>();

        ITSAClient tsa = null;
        int index = 0;

        if(this.signConfigurationModel.isSelloTiempo())
            tsa = new TSAClientBouncyCastle(
                    signConfigurationModel.getSelloTiempoUrl(),
                    signConfigurationModel.getSelloTiempoUser(),
                    signConfigurationModel.getSelloTiempoPass(),
                    ApplicationConstants.TSA_TOKEN_SIZE,
                    ApplicationConstants.HASH
            );

        for(byte[] fileBytes : this.signConfigurationModel.getFilesBytes()) {
            FileModel fileSigned = new FileModel();
            try {
                ByteArrayInputStream bis = new ByteArrayInputStream(fileBytes);
                ByteArrayOutputStream bout = new ByteArrayOutputStream();

                PdfReader reader = new PdfReader(bis);
                PdfSigner signer = new PdfSigner(reader, bout, new StampingProperties().useAppendMode());
                PdfSignatureAppearance sap = signer.getSignatureAppearance();

                if (signConfigurationModel.isVisible()) {
                    if (signConfigurationModel.getImagenFirma() == null) {
                        sap.setRenderingMode(PdfSignatureAppearance.RenderingMode.DESCRIPTION);
                    } else {
                        sap.setRenderingMode(PdfSignatureAppearance.RenderingMode.GRAPHIC_AND_DESCRIPTION);
                        sap.setSignatureGraphic(ImageDataFactory.create(signConfigurationModel.getImagenFirma()));
                    }
                    sap.setLayer2Text(signConfigurationModel.getTexto().replace("#", certificate.getNombre()));
                    sap.setReason(signConfigurationModel.getRazon());
                    sap.setLocation(signConfigurationModel.getLocacion());
                    sap.setPageRect(new Rectangle(
                            signConfigurationModel.getX().get(index),
                            signConfigurationModel.getY().get(index),
                            signConfigurationModel.getWidth().get(index),
                            signConfigurationModel.getHeight().get(index)
                    )).setPageNumber(signConfigurationModel.getPage().get(index));
                }

                PrivateKeySignature pks = new PrivateKeySignature(
                        certificate.getKey(),
                        ApplicationConstants.HASH_FORMAT_2,
                        ApplicationConstants.PROVIDER
                );

                signer.signDetached(
                        new BouncyCastleDigest(),
                        pks, certificate.getChain(),
                        null,
                        null,
                        tsa,
                        ApplicationConstants.SIGNATURE_SIZE,
                        PdfSigner.CryptoStandard.CADES
                );

                fileSigned.setName(signConfigurationModel.getFilesName().get(index));
                fileSigned.setBytes(bout.toByteArray());

                fileList.add(fileSigned);

                index++;
                reader.close();
                bout.flush();
                bout.close();
            }catch (Exception e ){
                System.out.println(ExceptionUtils.getStackTrace(e));
            }

        }

        return fileList;
    }

    /*
    public void addLtv(ArrayList<File> files) throws Exception {

        ICrlClient crlClient = new CrlClientOnline();
        IOcspClient ocspClient = new OcspClientBouncyCastle(null);

        for(File file : files) {
            PdfDocument pdfDoc = new PdfDocument(
                    new PdfReader(appProperties.getTempForlderPath() + file.getFileName()),
                    new PdfWriter(appProperties.getSignedFolderPath() + file.getFileName()),
                    new StampingProperties().useAppendMode());

            LtvVerification v = new LtvVerification(pdfDoc);
            SignatureUtil signatureUtil = new SignatureUtil(pdfDoc);

            List<String> names = signatureUtil.getSignatureNames();
            String sigName = names.get(names.size() - 1);

            PdfPKCS7 pkcs7 = signatureUtil.verifySignature(sigName);

            if (pkcs7.isTsp()) {
                v.addVerification(sigName, ocspClient, crlClient, LtvVerification.CertificateOption.WHOLE_CHAIN,
                        LtvVerification.Level.OCSP_CRL, LtvVerification.CertificateInclusion.YES);

            } else {
                for (String name : names) {
                    v.addVerification(name, ocspClient, crlClient, LtvVerification.CertificateOption.WHOLE_CHAIN,
                            LtvVerification.Level.OCSP_CRL, LtvVerification.CertificateInclusion.YES);

                }
            }

            v.merge();
            pdfDoc.close();

            LogUtil.setInfoLog(new Log("Se aagregó validación LTV al archivo: " + file.getFileName(), this.getClass().getName()));
        }
    }

     */
}
