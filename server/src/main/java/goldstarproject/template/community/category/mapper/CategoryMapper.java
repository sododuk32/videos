package goldstarproject.template.community.category.mapper;


import goldstarproject.template.community.category.dto.CategoryRequestDto;
import goldstarproject.template.community.category.dto.CategoryResponseDto;
import goldstarproject.template.community.category.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category toEntity(CategoryRequestDto categoryRequestDto);

    CategoryResponseDto toDto(Category category);
}
