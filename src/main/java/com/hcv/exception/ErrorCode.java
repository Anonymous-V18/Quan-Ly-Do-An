package com.hcv.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    INVALID_KEY(1000, "Invalid key in message validation !", HttpStatus.BAD_REQUEST),
    INVALID_USERNAME(1001, "Username must be at least 8 characters !", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1002, "Password must be at least 8 characters !", HttpStatus.BAD_REQUEST),
    INVALID_NAME_ROLE(1003, "Role is null or not exist name of the role !", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1004, "User existed !", HttpStatus.CONFLICT),
    USER_NOT_EXISTED(1005, "User not existed !", HttpStatus.NOT_FOUND),
    INVALID_USERNAME_OR_PASSWORD(1006, "Username or password is invalid !", HttpStatus.BAD_REQUEST),
    INVALID_NAME_PARAM(1007, "Name must be at least 3 characters !", HttpStatus.BAD_REQUEST),
    DEPARTMENT_EXISTED(1008, "Department existed !", HttpStatus.CONFLICT),
    DEPARTMENT_NOT_EXISTED(1009, "Department didn't exist !", HttpStatus.NOT_FOUND),
    STUDENT_EXISTED(1010, "Student existed !", HttpStatus.CONFLICT),
    INVALID_STUDENT_INPUT_PARAM(1011, "Have one or more attribute of student is null or invalid !", HttpStatus.BAD_REQUEST),
    INVALID_LIST_STUDENT(1012, "List of students is null or invalid !", HttpStatus.BAD_REQUEST),
    SUBJECT_EXISTED(1013, "Subject existed !", HttpStatus.CONFLICT),
    SUBJECT_NOT_EXISTED(1014, "Subject isn't exist !", HttpStatus.NOT_FOUND),
    INVALID_SUBJECT_NAME_PARAM(1015, "Subject name is invalid !", HttpStatus.BAD_REQUEST),
    INVALID_DEPARTMENT_NAME_PARAM(1016, "Department name is invalid !", HttpStatus.BAD_REQUEST),
    TEACHER_EXISTED(1017, "Teacher existed !", HttpStatus.CONFLICT),
    TEACHER_NOT_EXISTED(1018, "Teacher isn't exist !", HttpStatus.NOT_FOUND),
    INVALID_LIST_TEACHER(1019, "List of teachers is null or invalid !", HttpStatus.BAD_REQUEST),
    INVALID_JOB_FOR_TEACHER_PARAM(1020, "Have one or more attribute of job is null or invalid !", HttpStatus.BAD_REQUEST),
    INVALID_TEACHER_INPUT_PARAM(1021, "Have one or more attribute of teacher is null or invalid !", HttpStatus.BAD_REQUEST),
    //    INVALID_PAGE_PARAM(1022, "Page parameter in show all items is null or must be at least 1 !"),
    //    INVALID_LIMIT_PARAM(1023, "Limit of page parameter in show all items is null or must be at least 1 !"),
    //    INVALID_ORDER_BY_PARAM(1024, "OrderBy parameter in show all items is invalid !"),
    //    INVALID_ORDER_DIRECTION_PARAM(1025, "OrderDirection parameter in show all items is invalid !"),
    EXPIRATION_TOKEN(9990, "Token expired !", HttpStatus.REQUEST_TIMEOUT),
    UNAUTHORIZED(9991, "User is not permitted !", HttpStatus.UNAUTHORIZED),
    UNAUTHENTICATED(9992, "Unauthenticated error !", HttpStatus.UNAUTHORIZED),
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error !", HttpStatus.INTERNAL_SERVER_ERROR);

    private final int code;
    private final String message;
    private final HttpStatusCode httpStatusCode;

    ErrorCode(int code, String message, HttpStatusCode httpStatusCode) {
        this.code = code;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }

}
