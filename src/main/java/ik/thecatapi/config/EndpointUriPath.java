package ik.thecatapi.config;

import java.util.StringJoiner;

public enum EndpointUriPath {
    DELIMITER(Constants.DELIMITER),
    BREEDS_SEARCH(Constants.BREEDS_SEARCH),
    IMAGES_SEARCH(Constants.IMAGES_SEARCH),
    FAVOURITES(Constants.FAVOURITES),
    CATEGORIES(Constants.CATEGORIES);

    private final String value;

    EndpointUriPath(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", EndpointUriPath.class.getSimpleName() + "[", "]")
                .add("uriPath='" + value + "'")
                .toString();
    }

    public static class Constants {
        public static final String DELIMITER = "/";
        public static final String BREEDS_SEARCH = "/breeds/search";
        public static final String IMAGES_SEARCH = "/images/search";
        public static final String FAVOURITES = "/favourites";
        public static final String CATEGORIES = "/categories";

        private Constants() {
        }
    }

    public String getValue() {
        return this.value;
    }
}
