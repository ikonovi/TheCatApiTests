package ik.thecatapi.models.requests.images_search;

import ik.thecatapi.models.requests.base.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
public class GetImagesSearchRequest extends BaseRequest {
    private GetImagesSearchRequestQueryParams queryParams;
}
