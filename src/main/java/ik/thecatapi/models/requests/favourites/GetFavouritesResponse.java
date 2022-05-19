package ik.thecatapi.models.requests.favourites;

import ik.thecatapi.models.requests.CommonResponse;
import lombok.*;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class GetFavouritesResponse extends CommonResponse {
    private List<ResponseBodyFavourite> body;

    public GetFavouritesResponse(int statusCode) {
        super(statusCode);
    }
}
