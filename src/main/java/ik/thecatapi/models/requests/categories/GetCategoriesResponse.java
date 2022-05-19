package ik.thecatapi.models.requests.categories;

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
public class GetCategoriesResponse extends CommonResponse {
    private List<ResponseBodyCategory> body;

    public GetCategoriesResponse(int statusCode) {
        super(statusCode);
    }
}
