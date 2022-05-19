package ik.thecatapi.models.requests;

import ik.thecatapi.models.requests.AuthorizationHeader;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class CommonRequest {
    private AuthorizationHeader authorizationHeader;
}
