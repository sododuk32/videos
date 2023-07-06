package goldstarproject.template.adconnect.mapper;

import goldstarproject.template.adconnect.dto.AdConnectRequestDto;
import goldstarproject.template.adconnect.entity.AdConnect;
import goldstarproject.template.common.generic.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AdConnectRequestMapper extends GenericMapper<AdConnectRequestDto, AdConnect> {
    AdConnectRequestMapper INSTANCE = Mappers.getMapper(AdConnectRequestMapper.class);
}
