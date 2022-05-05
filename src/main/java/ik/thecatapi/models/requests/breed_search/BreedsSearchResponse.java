package ik.thecatapi.models.requests.breed_search;

import ik.thecatapi.models.requests.base.BaseResponse;
import ik.thecatapi.models.requests.ResponseBodyBreed;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BreedsSearchResponse extends BaseResponse {
    private List<ResponseBodyBreed> body;

    public BreedsSearchResponse(int statusCode) {
        super(statusCode);
    }
}
