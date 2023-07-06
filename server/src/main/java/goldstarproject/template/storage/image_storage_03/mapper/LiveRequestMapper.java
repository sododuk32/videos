package goldstarproject.template.storage.image_storage_03.mapper;


import goldstarproject.template.common.generic.GenericMapper;
import goldstarproject.template.storage.image_storage_03.dto.LiveRequestDto;

import goldstarproject.template.storage.image_storage_03.entity.Live;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

// 제네릭 매퍼를 상속받아 매핑해주는 역할
@Mapper(componentModel = "spring")
public interface LiveRequestMapper extends GenericMapper<LiveRequestDto, Live> {
    LiveRequestMapper INSTANCE = Mappers.getMapper(LiveRequestMapper.class);
}
