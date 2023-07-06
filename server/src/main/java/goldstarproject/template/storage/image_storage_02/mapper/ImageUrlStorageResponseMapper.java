package goldstarproject.template.storage.image_storage_02.mapper;


import goldstarproject.template.storage.image_storage_02.dto.ImageUrlStorageResponseDto;
import goldstarproject.template.storage.image_storage_02.entity.ImageUrlStorage;
import goldstarproject.template.common.generic.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ImageUrlStorageResponseMapper extends GenericMapper<ImageUrlStorageResponseDto, ImageUrlStorage> {
    ImageUrlStorageResponseMapper INSTANCE = Mappers.getMapper(ImageUrlStorageResponseMapper.class);
}

