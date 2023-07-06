package goldstarproject.template.adconnect.mapper;


import goldstarproject.template.adconnect.dto.AdConnectListDto;
import goldstarproject.template.adconnect.entity.AdConnect;
import goldstarproject.template.common.generic.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AdConnectListResponseMapper extends GenericMapper<AdConnectListDto, AdConnect> {

    AdConnectListResponseMapper INSTANCE = Mappers.getMapper(AdConnectListResponseMapper.class);

}
