package ik.thecatapi.models.requests.favourites;

import ik.thecatapi.models.requests.base.BaseResponse;
import lombok.*;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class GetFavouritesResponse extends BaseResponse {
    private List<ResponseBodyFavourite> body;

    public GetFavouritesResponse(int statusCode) {
        super(statusCode);
    }
}
