package goldstarproject.template.question.mapper;

import goldstarproject.template.common.generic.GenericMapper;
import goldstarproject.template.question.dto.QuestionListDto;
import goldstarproject.template.question.entity.Question;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface QuestionListResponseMapper extends GenericMapper<QuestionListDto, Question> {

    QuestionListResponseMapper INSTANCE = Mappers.getMapper(QuestionListResponseMapper.class);
}
