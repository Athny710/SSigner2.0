package org.oefa.com.ssigner.util;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilTest {

    @Test
    void getParametersFromUrl() {
        // GIVEN
        String url = "osigner://FirmaDigital%AROJAS%12345/";

        try {
            // WHEN
            ArrayList<String> params = StringUtil.getParametersFromUrl(url);

            // THEN
            assertEquals(params.size(), 3);
            assertEquals(params.get(2), "12345");

        }catch (Exception e){

        }

    }
}