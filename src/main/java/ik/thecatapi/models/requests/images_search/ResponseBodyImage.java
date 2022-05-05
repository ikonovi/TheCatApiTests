package ik.thecatapi.models.requests.images_search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ik.thecatapi.models.requests.ResponseBodyBreed;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseBodyImage {
    private List<ResponseBodyBreed> breeds;
    private String id;
    private String url;
}
