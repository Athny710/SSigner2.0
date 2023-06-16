package org.oefa.com.ssigner.application;

import org.oefa.com.ssigner.domain.CertificateModel;
import org.oefa.com.ssigner.domain.FileModel;
import org.oefa.com.ssigner.domain.SignConfigurationModel;
import org.oefa.com.ssigner.infra.output.adapter.IText7Adapter;

import java.util.ArrayList;

public class SignService {

    private final IText7Adapter iText7Adapter;
    private final SignConfigurationModel signConfigurationModel;
    private final CertificateModel certificateModel;


    public SignService(SignConfigurationModel signConfigurationModel, CertificateModel certificateModel){
        this.iText7Adapter = new IText7Adapter(signConfigurationModel);
        this.signConfigurationModel = signConfigurationModel;
        this.certificateModel = certificateModel;
    }


    public ArrayList<FileModel> signFiles() throws Exception {
        return iText7Adapter.signFiles(this.certificateModel);
        /*
        if(signConfigurationModel.isSelloTiempo() && signConfigurationModel.isLtv())
            this.addLtv();
         */


    }


    public void addLtv(){
        //iText7Adapter.addLtv(this.signConfigurationModel);

    }
}
