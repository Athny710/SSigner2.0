package org.oefa.com.ssigner.infra.input.adapter;

import org.oefa.com.ssigner.configuration.ApplicationConfiguration;
import org.oefa.com.ssigner.core.component.platform.CertificateComponent;
import org.oefa.com.ssigner.core.component.platform.StepComponent;
import org.oefa.com.ssigner.domain.app.PlatformModel;
import org.oefa.com.ssigner.task.platform.PlatformTaskManager;
import org.oefa.com.ssigner.util.CertificateUtil;


public class PlatformService {

    private final PlatformTaskManager platformTaskManager;
    private final PlatformModel platformModel;


    public PlatformService(PlatformModel platformModel) {
        this.platformTaskManager = new PlatformTaskManager(new StepComponent(platformModel), new CertificateComponent(platformModel));
        this.platformModel = platformModel;
    }


    public void start() {
        platformTaskManager.startSigningProcess();

    }


    public void onConfirm(String certificateSelected) {

        try {
            platformTaskManager.confirmSigningProcess(CertificateUtil.getCertificateToSignByAlias(certificateSelected));
        }catch (Exception e){}

    }


    public void onCancel() {
        platformTaskManager.cancelSignProcess();
    }


    public void onReloadCertificates() {
        CertificateComponent certificateComponent = new CertificateComponent(platformModel);
        certificateComponent.loadCertificates(CertificateUtil.getUserCertificateList(ApplicationConfiguration.DNI_CLIENT));

    }

}
