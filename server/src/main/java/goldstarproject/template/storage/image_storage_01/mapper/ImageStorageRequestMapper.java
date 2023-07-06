package goldstarproject.template.storage.image_storage_01.mapper;


import goldstarproject.template.storage.image_storage_01.dto.ImageStorageRequestDto;
import goldstarproject.template.storage.image_storage_01.entity.ImageStorage;
import goldstarproject.template.common.generic.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

// 제네릭 매퍼를 상속받아 매핑해주는 역할
@Mapper(componentModel = "spring")
public interface ImageStorageRequestMapper extends GenericMapper<ImageStorageRequestDto, ImageStorage> {
    ImageStorageRequestMapper INSTANCE = Mappers.getMapper(ImageStorageRequestMapper.class);
}
