package org.oefa.com.ssigner.util;

import org.oefa.com.ssigner.MainApplication;

import java.net.URL;

public class ResourceLoaderUtil {

    public static URL loadURL(String path) {
        return MainApplication.class.getResource(path);
    }

    public static String load(String path) {
        return loadURL(path).toString();
    }

}
