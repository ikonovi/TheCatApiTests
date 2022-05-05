package ik.thecatapi.models.requests.base;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class BaseRequest {
    private AuthorizationHeader authorizationHeader;
}
