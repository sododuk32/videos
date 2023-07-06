package goldstarproject.template.common.exception;

import lombok.Getter;

public enum ExceptionCode {
    NOTICE_NOT_FOUND(404, "Notice not found"),
    ADMIN_NOT_FOUND(404,"Admin not found"),
    ADMIN_EXIST (404, "Admin exist"),
    MEMBER_NOT_FOUND(404, "Member not found"),
    MEMBER_EXIST(404, "Member exists"),
    CATEGORY_NOT_FOUND(404,"Category not found"),
    HEART_NOT_FOUND(404,"Heart not found"),
    COMMENT_NOT_FOUND(404,"Comment not found"),
    PARENT_NOT_FOUND(404,"Parent not found"),
    BOARD_NOT_FOUND(404,"Board not found"),
    QUESTION_NOT_FOUND(404,"Question not found"),
    IMAGE_NOT_FOUND(404,"Image not found"),
    IMAGE_URL_NOT_FOUND(404,"Image_url not found"),
    PASSWORDS_DO_NOT_MATCH(404,"Password do not match"),
    MISSING_PASSWORD(404,"Missing password"),
    ADCONNECT_NOT_FOUND(404, "AdConnect not found");



    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
