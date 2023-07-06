package goldstarproject.template.storage.image_storage_01.mapper;


import goldstarproject.template.storage.image_storage_01.dto.ImageStorageListDto;
import goldstarproject.template.storage.image_storage_01.entity.ImageStorage;
import goldstarproject.template.common.generic.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ImageStorageListResponseMapper extends GenericMapper<ImageStorageListDto, ImageStorage> {

    ImageStorageListResponseMapper INSTANCE = Mappers.getMapper(ImageStorageListResponseMapper.class);
}
