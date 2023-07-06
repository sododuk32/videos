package goldstarproject.template.adconnect.mapper;

import goldstarproject.template.adconnect.dto.AdConnectResponseDto;
import goldstarproject.template.adconnect.entity.AdConnect;
import goldstarproject.template.common.generic.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AdConnectResponseMapper extends GenericMapper<AdConnectResponseDto, AdConnect> {
    AdConnectResponseMapper INSTANCE = Mappers.getMapper(AdConnectResponseMapper.class);
}
