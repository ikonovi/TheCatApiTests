package ik.thecatapi.config;

import ik.thecatapi.utils.PropertyFileUtils;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.util.Properties;

@UtilityClass
public class TheCatApiConfiguration {
    private static String theCatApiBaseUri;
    private static String theCatApiBasePath;

    private static final String PROPS_FILE = "env_config.properties";

    static {
        initProperties();
    }

    @SneakyThrows
    private static void initProperties() {
        Properties props = PropertyFileUtils.getFromResources(PROPS_FILE);
        theCatApiBaseUri = props.getProperty("theCatApi.baseUri");
        theCatApiBasePath = props.getProperty("theCatApi.basePath");
    }

    public static String getTheCatApiBaseUri() {
        return theCatApiBaseUri;
    }

    public static String getTheCatApiBasePath() {
        return theCatApiBasePath;
    }
}
