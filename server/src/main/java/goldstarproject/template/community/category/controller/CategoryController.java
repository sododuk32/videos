package goldstarproject.template.community.category.controller;

import goldstarproject.template.common.dto.SingleResponseDto;
import goldstarproject.template.community.category.dto.CategoryRequestDto;
import goldstarproject.template.community.category.dto.CategoryResponseDto;
import goldstarproject.template.community.category.service.CategoryService;
import goldstarproject.template.security.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@Validated
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;



    //ROLE_ADMIN
    @PostMapping("/admin/category/insert")
    public ResponseEntity insertCategory(@Valid @RequestBody CategoryRequestDto categoryRequestDto,
                                         Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        CategoryResponseDto response = categoryService.insertCategory(categoryRequestDto,principalDetails);
        return new ResponseEntity(new SingleResponseDto<>(response), HttpStatus.CREATED);
    }



    //ROLE_ADMIN
    @DeleteMapping("/admin/category/{category-id}/delete")
    public ResponseEntity deleteCategory(@PathVariable("category-id") @Positive Long categoryId,Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        categoryService.deleteCategory(categoryId,principalDetails);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }
}
