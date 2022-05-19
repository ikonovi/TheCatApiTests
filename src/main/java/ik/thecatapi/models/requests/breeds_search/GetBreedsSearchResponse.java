package ik.thecatapi.models.requests.breeds_search;

import ik.thecatapi.models.requests.CommonResponse;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class GetBreedsSearchResponse extends CommonResponse {
    private List<ResponseBodyBreed> body;

    public GetBreedsSearchResponse(int statusCode) {
        super(statusCode);
    }
}
