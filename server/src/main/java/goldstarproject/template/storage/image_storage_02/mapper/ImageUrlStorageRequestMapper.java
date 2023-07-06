package goldstarproject.template.storage.image_storage_02.mapper;


import goldstarproject.template.storage.image_storage_02.dto.ImageUrlStorageRequestDto;
import goldstarproject.template.storage.image_storage_02.entity.ImageUrlStorage;
import goldstarproject.template.common.generic.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

// 제네릭 매퍼를 상속받아 매핑해주는 역할
@Mapper(componentModel = "spring")
public interface ImageUrlStorageRequestMapper extends GenericMapper<ImageUrlStorageRequestDto, ImageUrlStorage> {
    ImageUrlStorageRequestMapper INSTANCE = Mappers.getMapper(ImageUrlStorageRequestMapper.class);
}
