package ik.thecatapi.models.requests.breedsearch;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BreedSearchRequestQueryParams {
    private String q;
}
