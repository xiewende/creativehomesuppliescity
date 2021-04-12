package com.xiewende.creativehomesuppliescity;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(FdfsClientConfig.class)
@SpringBootApplication
public class CreativehomesuppliescityApplication {

    public static void main(String[] args) {
        SpringApplication.run(CreativehomesuppliescityApplication.class, args);
    }

}
