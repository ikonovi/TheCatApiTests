package ik.thecatapi.config;

import java.util.StringJoiner;

public enum EndpointPath {
    BREEDS_SEARCH(Constants.BREEDS_SEARCH),
    IMAGES_SEARCH(Constants.IMAGES_SEARCH),
    FAVOURITES(Constants.FAVOURITES);

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
        public static final String IMAGES_SEARCH = "/images/search";
        public static final String FAVOURITES = "/favourites";

        private Constants() {
        }
    }

    public String getUriPath() {
        return this.uriPath;
    }
}
