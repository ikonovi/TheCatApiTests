package ik.thecatapi.services.requests;

import ik.thecatapi.config.TheCatApiConfiguration;
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
        final String AUTH_HEADER_NAME = TheCatApiConfiguration.getHttpAuthorizationHeaderName();
        return AuthorizationHeader.builder()
                .name(AUTH_HEADER_NAME)
                .value(apiKey)
                .build();
    }
}
