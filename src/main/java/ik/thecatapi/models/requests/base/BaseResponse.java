package ik.thecatapi.models.requests.base;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BaseResponse {
    private int statusCode;
}
