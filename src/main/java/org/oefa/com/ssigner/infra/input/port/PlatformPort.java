package org.oefa.com.ssigner.infra.input.port;

import javafx.event.ActionEvent;

public interface PlatformPort {
    public void onCancel(ActionEvent event);
    public void onConfirm(ActionEvent event);
    public void onReloadCertificates(ActionEvent event);
}
