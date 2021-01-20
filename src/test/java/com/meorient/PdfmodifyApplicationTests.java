package com.meorient;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

@SpringBootTest
class PdfmodifyApplicationTests {
    @Test
    void contextLoads() {
        System.out.println(TimeUnit.MINUTES + "要上传的文件名:");
    }

}
