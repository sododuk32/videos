package goldstarproject.template.question.mapper;

import goldstarproject.template.common.generic.GenericMapper;
import goldstarproject.template.question.entity.Question;
import goldstarproject.template.question.dto.QuestionRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface QuestionRequestMapper extends GenericMapper<QuestionRequestDto, Question> {
    QuestionRequestMapper INSTANCE = Mappers.getMapper(QuestionRequestMapper.class);
}
