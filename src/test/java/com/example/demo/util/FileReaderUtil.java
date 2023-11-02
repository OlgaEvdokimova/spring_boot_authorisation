package com.example.demo.util;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.testcontainers.shaded.org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FileReaderUtil {
    public static String readFromJsonFile(String path) throws IOException {
        Resource resource = new ClassPathResource(path);
        return FileUtils.readFileToString(new File(String.valueOf(resource.getFile())), StandardCharsets.UTF_8);
    }
}
