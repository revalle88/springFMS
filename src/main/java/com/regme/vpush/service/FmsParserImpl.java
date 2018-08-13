package com.regme.vpush.service;

import java.io.File;
import java.net.URL;
import org.apache.commons.io.*;
import org.springframework.stereotype.Service;

/**
 * Created by admin on 13.08.2018.
 */
@Service
public class FmsParserImpl implements FmsParser {
    private File zipFile;
    private String downloadDir = ClassLoader.getSystemResource(".").getFile();
    private String zipFileName = "fms.zip";
    private String sourceURL = "http://webzato.com/fms/fms_structure_10012018.zip";
    public String load(){


        try {
            System.out.println(ClassLoader.getSystemResource("."));
            //TODO project resources
            zipFile = new File(downloadDir + zipFileName);
            if (zipFile.createNewFile()) {
                System.out.println("File is created!");
            } else {
                System.out.println("File already exists.");
            }
            FileUtils.copyURLToFile(new URL(sourceURL), zipFile);
        }
        catch (Exception e) { e.printStackTrace(); }
        return "OK";
    }
}
