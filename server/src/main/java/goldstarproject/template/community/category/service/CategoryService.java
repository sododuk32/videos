package goldstarproject.template.community.category.service;

import goldstarproject.template.community.category.dto.CategoryRequestDto;
import goldstarproject.template.community.category.dto.CategoryResponseDto;
import goldstarproject.template.community.category.entity.Category;
import goldstarproject.template.community.category.mapper.CategoryMapper;
import goldstarproject.template.community.category.repository.CategoryRepository;
import goldstarproject.template.common.exception.ExceptionCode;
import goldstarproject.template.common.exception.RestControllerException;
import goldstarproject.template.member.entity.Member;
import goldstarproject.template.member.service.MemberService;
import goldstarproject.template.security.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final MemberService memberService;

    public CategoryResponseDto insertCategory(CategoryRequestDto categoryRequestDto, PrincipalDetails principalDetails) {
        //관리자 조회 후 등록한 매니저 조회
        Member member = memberService.validateMember(principalDetails.getMember().getId());
        Category category = categoryMapper.toEntity(categoryRequestDto);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toDto(savedCategory);
    }


    public void deleteCategory(Long categoryId,PrincipalDetails principalDetails) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new RestControllerException(ExceptionCode.CATEGORY_NOT_FOUND));
        categoryRepository.delete(category);
    }
}
