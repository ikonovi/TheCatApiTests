package ik.thecatapi.models.requests.favourites;

import ik.thecatapi.models.requests.CommonRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
public class DeleteFavouritesRequest extends CommonRequest {
    private long favouriteId;
}
