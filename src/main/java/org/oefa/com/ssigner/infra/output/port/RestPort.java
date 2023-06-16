package org.oefa.com.ssigner.infra.output.port;

import org.oefa.com.ssigner.domain.FileModel;
import org.oefa.com.ssigner.domain.SignConfigurationModel;

import java.util.ArrayList;

public interface RestPort {
    SignConfigurationModel getSignConfiguration() throws Exception;
    void uploadFilesSigned(ArrayList<FileModel> fileList) throws Exception;
    void cancelSignProcess() throws Exception;
}
