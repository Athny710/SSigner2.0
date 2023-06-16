package org.oefa.com.ssigner.util;

import org.junit.jupiter.api.Test;
import org.oefa.com.ssigner.domain.CertificateModel;

import java.sql.Time;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
class CertificateUtilTest {

    @Test
    void loadCertificates() {
        try {
            TimeUtil.showTime("-----------");
            CertificateUtil.loadCertificates();
            System.out.println(CertificateUtil.certificateList.size());
            TimeUtil.showTime(":_______________");
        }catch (Exception e){

        }
    }
}