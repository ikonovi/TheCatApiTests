package ik.thecatapi.models.requests.images_search;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ImagesSearchRequestQueryParams {
    private String breedId;
}
