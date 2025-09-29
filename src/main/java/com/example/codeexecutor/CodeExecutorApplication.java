package com.example.codeexecutor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class CodeExecutorApplication {
    public static void main(String[] args) {
        SpringApplication.run(CodeExecutorApplication.class, args);
    }
}
