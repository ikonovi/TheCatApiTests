package ik.thecatapi.models.requests.favourites;

import ik.thecatapi.models.requests.CommonResponse;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DeleteFavouritesResponse extends CommonResponse {
    private DeleteFavouritesResponseBody body;

    public DeleteFavouritesResponse(int statusCode) {
        super(statusCode);
    }
}
