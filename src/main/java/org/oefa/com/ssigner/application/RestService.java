package org.oefa.com.ssigner.application;

import org.oefa.com.ssigner.domain.FileModel;
import org.oefa.com.ssigner.domain.SignConfigurationModel;
import org.oefa.com.ssigner.infra.output.adapter.WSSFDAdapter;

import java.util.ArrayList;

public class RestService {

    private static final WSSFDAdapter wssfdAdapter = new WSSFDAdapter();


    public static SignConfigurationModel getSigConfiguration() throws Exception {
        return wssfdAdapter.getSignConfiguration();
    }

    public static void uploadFilesSigned(ArrayList<FileModel> signedFiles) throws Exception{
        wssfdAdapter.uploadFilesSigned(signedFiles);
    }

    public static void cancelSignProcess() throws Exception{
       wssfdAdapter.cancelSignProcess();
    }
}
