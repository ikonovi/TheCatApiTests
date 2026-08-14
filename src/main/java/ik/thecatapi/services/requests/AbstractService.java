package ik.thecatapi.services.requests;

import ik.thecatapi.config.ApiProperties;
import ik.thecatapi.models.requests.AuthorizationHeader;

public abstract class AbstractService {
    private final AuthorizationHeader authHeader;

    protected AbstractService(String apiKey) {
        this.authHeader = createAuthorizationHeader(apiKey);
    }

    public AuthorizationHeader getAuthorizationHeader() {
        return authHeader;
    }

    private AuthorizationHeader  createAuthorizationHeader(String apiKey) {
        final String AUTH_HEADER_NAME = ApiProperties.getAuthHeaderName();
        return AuthorizationHeader.builder()
                .name(AUTH_HEADER_NAME)
                .value(apiKey)
                .build();
    }
}
