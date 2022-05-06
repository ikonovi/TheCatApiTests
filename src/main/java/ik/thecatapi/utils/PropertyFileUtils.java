package ik.thecatapi.utils;

import lombok.experimental.UtilityClass;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Properties;

@UtilityClass
public class PropertyFileUtils {
    /**
     * @param filePath - file path relative to resource directory of the project.
     * @return object with properties read from file
     * @throws IOException when incorrect file path provided
     */
    public static Properties getFromResources(String filePath) throws IOException, URISyntaxException {
        String propFilePath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource(filePath))
                .toURI().getPath();
        Properties properties = new Properties();
        try (InputStream inputStream = new FileInputStream(propFilePath)) {
            properties.load(inputStream);
        }
        return properties;
    }
}
