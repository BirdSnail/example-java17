/**
 * Copyright©2022,QiZhiDaoNetworkCo.,Ltd.Allrights
 * reserved.
 * QiZhiDaoPROPRIETARY/CONFIDENTIAL.Useissubjecttolicense
 * terms.
 */
package birdsnail.example.算法;

import java.io.File;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

/**
 * @author fangxia.hu
 * @version 1.0.0
 * @Description:
 * @Date: 2022/11/8 9:34 AM
 */
public class Tess4jOcrTest {

    public static void main(String[] args) {
        // System.out.println(System.getProperty("sun.arch.data.model"));

        String path = "C:\\Users\\huadong.yang\\Desktop\\lQDPJxbaYqZjU4vMyMzIsFeMopaD1U_iA2bebcpAcAA_200_200.jpg";
        File file = new File(path);
        ITesseract it = new Tesseract();
        it.setLanguage("chi_sim");
        try {
            String result = it.doOCR(file);
            System.out.println("识别结果:" + result);
        } catch (TesseractException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}

