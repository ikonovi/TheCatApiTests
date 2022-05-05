package ik.thecatapi.models.requests.images_search;

import ik.thecatapi.models.requests.base.BaseResponse;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class GetImagesSearchResponse extends BaseResponse {
    private List<ResponseBodyImage> body;

    public GetImagesSearchResponse(int statusCode) {
        super(statusCode);
    }
}
