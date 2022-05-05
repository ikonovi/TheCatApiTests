package ik.thecatapi.models.requests.favourites;

import ik.thecatapi.models.requests.base.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
public class GetFavouritesRequest extends BaseRequest {
    private GetFavouritesRequestQueryParams queryParams;
}
