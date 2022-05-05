package ik.thecatapi.models.requests.base;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthorizationHeader {
    private String name;
    private String value;
}
