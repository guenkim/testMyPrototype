package com.guen.common.file.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FileStorageProperties {

    @Value("${upload.serverDir}")
    private String serverDir;

    public String getServerDir() {
        return serverDir;
    }

}