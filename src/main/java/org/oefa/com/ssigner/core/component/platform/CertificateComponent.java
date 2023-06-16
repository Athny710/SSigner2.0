package org.oefa.com.ssigner.core.component.platform;

import javafx.collections.FXCollections;
import org.oefa.com.ssigner.domain.app.PlatformModel;
import java.util.ArrayList;
import java.util.List;

public class CertificateComponent {

    private final PlatformModel platformModel;

    public CertificateComponent(PlatformModel platformModel){
        this.platformModel = platformModel;
    }

    public void loadCertificates(ArrayList<String> certList){
        this.platformModel.getCertificateComboBox().getItems().removeAll(this.platformModel.getCertificateComboBox().getItems());
        List<String> list = (certList.size() == 0) ? List.of("No se encontraron certificados") : certList;
        this.platformModel.getCertificateComboBox().setItems(FXCollections.observableList(list));

    }
}
