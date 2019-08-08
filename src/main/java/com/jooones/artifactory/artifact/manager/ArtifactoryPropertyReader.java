package com.jooones.artifactory.artifact.manager;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ArtifactoryPropertyReader {

    public static String readProperty(String propertyName) {
        try (InputStream input = new FileInputStream("artifactory.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            return properties.getProperty(propertyName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
