package ik.thecatapi.models.requests.favourites;

import ik.thecatapi.models.requests.CommonResponse;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PostFavouritesResponse extends CommonResponse {
    private PostFavouritesResponseBody body;

    public PostFavouritesResponse(int statusCode) {
        super(statusCode);
    }
}
