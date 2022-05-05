package ik.thecatapi.models.requests.favourites;

import lombok.Data;

@Data
public class PostFavouritesResponseBody {
    private String message;
    private long id;

    // error response
    private int status;
    private String level;
}
