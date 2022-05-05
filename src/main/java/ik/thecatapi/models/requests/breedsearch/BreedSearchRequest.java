package ik.thecatapi.models.requests.breedsearch;

import ik.thecatapi.models.requests.base.BaseRequest;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class BreedSearchRequest extends BaseRequest {
    private BreedSearchRequestQueryParams queryParams;
}
