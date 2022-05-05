package ik.thecatapi.config;

import java.util.StringJoiner;

public enum EndpointPath {
    BREEDS_SEARCH(Constants.BREEDS_SEARCH);

    private final String uriPath;

    EndpointPath(String uriPath) {
        this.uriPath = uriPath;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", EndpointPath.class.getSimpleName() + "[", "]")
                .add("uriPath='" + uriPath + "'")
                .toString();
    }

    public static class Constants {
        public static final String BREEDS_SEARCH = "breeds/search";
    }

    public String getUriPath() {
        return this.uriPath;
    }
}
