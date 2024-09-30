package com.movierama.lite;

import com.movierama.lite.shared.MyRuntimeHints;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportRuntimeHints;

@SpringBootApplication
@ImportRuntimeHints(MyRuntimeHints.class)
public class MovieRamaLiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieRamaLiteApplication.class, args);
    }

}
