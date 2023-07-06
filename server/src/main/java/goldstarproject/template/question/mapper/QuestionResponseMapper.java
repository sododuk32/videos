package goldstarproject.template.question.mapper;

import goldstarproject.template.common.generic.GenericMapper;
import goldstarproject.template.question.dto.QuestionResponseDto;
import goldstarproject.template.question.entity.Question;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface QuestionResponseMapper extends GenericMapper<QuestionResponseDto, Question> {
    QuestionResponseMapper INSTANCE = Mappers.getMapper(QuestionResponseMapper.class);
}
