module org.oefa.com.ssigner {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.apache.logging.log4j;
    requires org.apache.commons.lang3;
    requires mfx.resources;
    requires MaterialFX;
    requires java.sql;
    requires com.h2database;
    requires spring.web;
    requires com.google.gson;
    requires org.bouncycastle.provider;
    requires java.naming;
    requires sign;
    requires kernel;
    requires io;

    opens org.oefa.com.ssigner to javafx.fxml;
    exports org.oefa.com.ssigner;

    opens org.oefa.com.ssigner.infra.input.adapter to javafx.fxml;
    exports org.oefa.com.ssigner.infra.input.adapter;

    opens org.oefa.com.ssigner.domain.rest to com.google.gson;
}