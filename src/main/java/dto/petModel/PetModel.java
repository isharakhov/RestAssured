package dto.petModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@Builder
@EqualsAndHashCode(exclude = {"id"})
public class PetModel {

    @JsonProperty("id")
    private final Long id;

    @JsonProperty("category")
    private final CategoryAndTagsItem category;

    @JsonProperty("name")
    private final String name;
    private final List<String> photoUrls;
    private final List<CategoryAndTagsItem> tags;
    private final String status;

    @Data
    @Builder
    public static class CategoryAndTagsItem {
        private final int id;
        private final String name;
    }
}