package com.regme.vpush;

import com.regme.vpush.service.FmsParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Created by admin on 14.08.2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ZipFileDownloadTest {

    @Autowired
    private FmsParser fmsParser;

    @Test
    public void whenDownload_thenCheckZipFile() {

        File file = fmsParser.load();
        assertThat(fmsParser.load().exists());

    }

    @Test
    public void whenUnzip_thenCheckCsvFile() {

        fmsParser.load();
        File file = new File(fmsParser.unzip());
        assertThat(file.exists());

    }
}
