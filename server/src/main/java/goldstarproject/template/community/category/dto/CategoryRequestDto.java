package goldstarproject.template.community.category.dto;

import goldstarproject.template.community.category.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequestDto {
    @NotBlank(message = "내용을 입력해주세요.")
    private String name;
    public CategoryRequestDto(Category category) {
        this.name = category.getName();
    }
}
