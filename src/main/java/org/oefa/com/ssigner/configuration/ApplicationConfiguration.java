package org.oefa.com.ssigner.configuration;

import javafx.application.Application;
import javafx.scene.Scene;
import org.oefa.com.ssigner.core.stage.StageBuilder;
import org.oefa.com.ssigner.domain.util.Log;
import org.oefa.com.ssigner.util.LogUtil;
import org.oefa.com.ssigner.util.StringUtil;
import java.io.InputStream;
import java.security.cert.X509CRL;
import java.util.ArrayList;
import java.util.Properties;

public class ApplicationConfiguration {

    public static ExecutionType EXECUTION_TYPE;
    public static String ID_CLIENT;
    public static String ID_GROUP;
    public static String DNI_CLIENT;
    public static ArrayList<String> ON_START_NOTIFICATIONS = new ArrayList<>();

    public enum ExecutionType{
        PLATFORM, LOCAL
    }


    public static String getKey(String key) {
        Properties props = new Properties();
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            InputStream resourceStream = loader.getResourceAsStream("application.properties");
            props.load(resourceStream);

            return props.getProperty(key);

        } catch (Exception e) {
            LogUtil.setError(
                    new Log(
                            "Error obteniendo propiedad del archivo de configuración",
                            ApplicationConfiguration.class.getName(),
                            e
                    )
            );
            return "";
        }
    }
}
