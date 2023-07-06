package goldstarproject.template.storage.image_storage_03.mapper;


import goldstarproject.template.common.generic.GenericMapper;
import goldstarproject.template.storage.image_storage_03.dto.LiveListDto;

import goldstarproject.template.storage.image_storage_03.entity.Live;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface LiveListResponseMapper extends GenericMapper<LiveListDto, Live> {

    LiveListResponseMapper INSTANCE = Mappers.getMapper(LiveListResponseMapper.class);
}
