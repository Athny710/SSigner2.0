package org.oefa.com.ssigner.util;

import java.util.ArrayList;
import java.util.Collections;

public class StringUtil {

    public static ArrayList<String> getParametersFromUrl(String url) throws Exception{
        ArrayList<String> paramsList = new ArrayList<>();

        String concatenatedParams = url
                .replaceFirst("osigner://", "")
                .replaceFirst("/", "");

        String[] params = concatenatedParams.split("%");
        Collections.addAll(paramsList, params);

        return paramsList;

    }
}
