package ik.thecatapi.models.requests.images_search;

import ik.thecatapi.models.requests.CommonRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
public class GetImagesSearchRequest extends CommonRequest {
    private GetImagesSearchRequestQueryParams queryParams;
}
