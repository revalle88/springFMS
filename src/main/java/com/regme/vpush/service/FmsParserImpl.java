package com.regme.vpush.service;

import java.io.*;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import com.regme.vpush.DemoApplication;
import com.regme.vpush.domain.Department;
import com.regme.vpush.repository.DepartmentRepository;
import org.apache.commons.io.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Service;

/**
 * Created by admin on 13.08.2018.
 */
@Service
public class FmsParserImpl implements FmsParser {
    private File zipFile;
    File home = new ApplicationHome().getDir();
    private String downloadDir = home.getAbsolutePath()+"/";
    private String zipFileName = "fms.zip";
    private String sourceURL = "http://webzato.com/fms/fms_structure_10012018.zip";
    private String uncompressedDir = downloadDir+"uncompressed/";
    private String csvFile;

    @Autowired
    private DepartmentRepository departmentRepository;
    //Загружаем файл с URL и сохраняем
    public File load(){
        try {
            System.out.println("downloadDir: ");
            System.out.println(downloadDir);
            zipFile = new File(downloadDir + zipFileName);
            if (zipFile.createNewFile()) {
                System.out.println("File is created!");
            } else {
                System.out.println("File already exists.");
            }
            FileUtils.copyURLToFile(new URL(sourceURL), zipFile);
        }
        catch (Exception e) { e.printStackTrace(); }
        return zipFile;
    }
    //Распаковка файла
    public String unzip(){


        try(ZipFile file = new ZipFile(downloadDir + zipFileName))
        {
            Enumeration<? extends ZipEntry> entries = file.entries();
            String uncompressedDirectory = uncompressedDir;
            new File(uncompressedDirectory).mkdir();
            while (entries.hasMoreElements())
            {
                ZipEntry entry = entries.nextElement();
                //Если папка, то создаем папку
                if (entry.isDirectory())
                {
                    System.out.println("Creating Directory:" + uncompressedDirectory + entry.getName());
                    new File(uncompressedDirectory + entry.getName()).mkdir();

                }
                //Если файл то создаем файл
                else
                {

                    InputStream is = file.getInputStream(entry);
                    BufferedInputStream bis = new BufferedInputStream(is);
                    String uncompressedFileName = uncompressedDirectory + entry.getName();
                    csvFile = uncompressedFileName;
                    System.out.println("Creating File:" + uncompressedFileName);
                    new File(uncompressedFileName);
                    FileOutputStream fileOutput = new FileOutputStream(uncompressedFileName);
                    while (bis.available() > 0)
                    {
                        fileOutput.write(bis.read());
                    }
                    fileOutput.close();
                    System.out.println("Written :" + entry.getName());
                }
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }


        return csvFile;
    }

    public String parse(){
        //чистим таблицу
        //TODO сделать не полное удаление а апдейт по коду
        departmentRepository.deleteAll();
        String line = "";
        String cvsSplitBy = ",";
        //задаем кодировку для чтения из файла
        String charset = "cp1251";
        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader (new FileInputStream(csvFile), charset));
            while ((line = br.readLine()) != null) {
                String[] depStr = line.split(cvsSplitBy);
                String code = depStr[1].replaceAll("\"","");
                code = code.replaceAll(" ","");
                String name = depStr[0].replaceAll("\"","");
                //String utf8name= new String(name.getBytes("utf-8"),"utf-8");
                System.out.println("FMS [code= " + code + " , name=" + name + "]");
                Department department = new Department(name, code);
                this.departmentRepository.save(department);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "OK";
    }

    //обновить справочник ФМС
    public String update(){
        this.load();
        this.unzip();
        this.parse();
        return csvFile;

    }



}
