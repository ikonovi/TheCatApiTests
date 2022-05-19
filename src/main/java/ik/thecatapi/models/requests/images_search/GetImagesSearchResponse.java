package ik.thecatapi.models.requests.images_search;

import ik.thecatapi.models.requests.CommonResponse;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class GetImagesSearchResponse extends CommonResponse {
    private List<ResponseBodyImage> body;

    public GetImagesSearchResponse(int statusCode) {
        super(statusCode);
    }
}
