package ik.thecatapi.models.requests.breed_search;

import ik.thecatapi.models.requests.base.BaseResponse;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class GetBreedsSearchResponse extends BaseResponse {
    private List<ResponseBodyBreed> body;

    public GetBreedsSearchResponse(int statusCode) {
        super(statusCode);
    }
}
