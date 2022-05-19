package ik.thecatapi.models.requests;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class CommonRequest {
    private AuthorizationHeader authorizationHeader;
}
