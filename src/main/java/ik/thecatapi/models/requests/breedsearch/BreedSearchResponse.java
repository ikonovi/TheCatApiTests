package ik.thecatapi.models.requests.breedsearch;

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
public class BreedSearchResponse extends BaseResponse {
    private List<ResponseBodyBreed> body;

    public BreedSearchResponse(int statusCode) {
        super(statusCode);
    }
}
