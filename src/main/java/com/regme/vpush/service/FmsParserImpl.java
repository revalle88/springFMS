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
    //File file = new File(".");
    File home = new ApplicationHome().getDir();
   // private String downloadDir = ClassLoader.getSystemResource(".").getFile()+ "/";
   // private String downloadDir = file.getAbsolutePath();
   private String downloadDir = home.getAbsolutePath()+"/";

    private String zipFileName = "fms.zip";
    private String sourceURL = "http://webzato.com/fms/fms_structure_10012018.zip";
    private String uncompressedDir = downloadDir+"uncompressed/";
    private String csvFile;




    @Autowired
    private DepartmentRepository departmentRepository;
    //Загрузка файла
    public File load(){


        try {
            System.out.println("downloadDir: ");
            System.out.println(downloadDir);
          //  System.out.println(ClassLoader.getSystemResource("."));
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
        return zipFile;
    }

    public String unzip(){


        try(ZipFile file = new ZipFile(downloadDir + zipFileName))
        {
            //Get file entries
            Enumeration<? extends ZipEntry> entries = file.entries();

            //We will unzip files in this folder
            String uncompressedDirectory = uncompressedDir;
            //Files.createDirectory(fileSystem.getPath(uncompressedDirectory));
            new File(uncompressedDirectory).mkdir();
            //Iterate over entries
            while (entries.hasMoreElements())
            {
                ZipEntry entry = entries.nextElement();
                //If directory then create a new directory in uncompressed folder
                if (entry.isDirectory())
                {
                    System.out.println("Creating Directory:" + uncompressedDirectory + entry.getName());
                    new File(uncompressedDirectory + entry.getName()).mkdir();
                    //  Files.createDirectories(fileSystem.getPath(uncompressedDirectory + entry.getName()));
                }
                //Else create the file
                else
                {
                    System.out.println("Creating InputStream:");
                    InputStream is = file.getInputStream(entry);
                    System.out.println("Creating BufferedInputStream:");
                    BufferedInputStream bis = new BufferedInputStream(is);
                    String uncompressedFileName = uncompressedDirectory + entry.getName();
                    csvFile = uncompressedFileName;
                    //Path uncompressedFilePath = fileSystem.getPath(uncompressedFileName);
                    //Files.createFile(uncompressedFilePath);
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
        String charset = "cp1251"; // or what corresponds
        try {


            BufferedReader br = new BufferedReader(
                    new InputStreamReader (new FileInputStream(csvFile), charset));


            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] depStr = line.split(cvsSplitBy);
                String code = depStr[1].replaceAll("\"","");//проверь!
                code = code.replaceAll(" ","");//проверь!
                String name = depStr[0].replaceAll("\"","");//проверь!
                String utf8name= new String(name.getBytes("utf-8"),"utf-8");

                System.out.println("Country [code= " + depStr[1] + " , name=" + name + "]");
                Department department = new Department(name, code);
                this.departmentRepository.save(department);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "OK";
    }

    public String convert(){

        return "OK";
    }

    public String update(){
        this.load();
        this.unzip();
        this.parse();
        return csvFile;

    }



}
