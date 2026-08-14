package ik.thecatapi.config;

import ik.thecatapi.utils.PropertyFileUtils;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.util.Properties;

@UtilityClass
public class TheCatApiConfiguration {
    @Getter
    private static String theCatApiBaseUri;
    @Getter
    private static String theCatApiBasePath;
    @Getter
    private static String authHeaderName;
    @Getter
    private static String authHeaderValue;

    private static final String PROPS_FILE = "api-config.properties";

    static {
        initProperties();
    }

    @SneakyThrows
    private static void initProperties() {
        Properties props = PropertyFileUtils.getFromResources(PROPS_FILE);
        theCatApiBaseUri = props.getProperty("theCatApi.baseUri");
        theCatApiBasePath = props.getProperty("theCatApi.basePath");
        authHeaderName = props.getProperty("theCatApi.http.authorization.header.name");
        authHeaderValue = System.getenv("api_key");
        if (authHeaderValue == null) {
            throw new IllegalStateException("Environment variable " + authHeaderName +" is not set");
        }
    }

}
