package ik.thecatapi.models.requests.images_search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ik.thecatapi.models.requests.breeds_search.ResponseBodyBreed;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ResponseBodyImage {
    private List<ResponseBodyBreed> breeds;
    private String id;
    private String url;
}
