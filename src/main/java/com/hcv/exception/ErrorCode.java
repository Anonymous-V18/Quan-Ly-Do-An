package com.hcv.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    INVALID_KEY(1000, "Invalid key in message validation !"),
    INVALID_USERNAME(1001, "UserName must be at least 8 characters !"),
    INVALID_PASSWORD(1001, "Password must be at least 8 characters !"),
    INVALID_NAME_ROLE(1001, "Role is null or not exist name of the role !"),
    USER_EXISTED(1002, "User existed !"),
    USER_NOT_EXISTED(1003, "User not existed !"),
    INVALID_USERNAME_OR_PASSWORD(1004, "Username or password is invalid !"),
    INVALID_NAME_PARAM(1005, "Name must be at least 3 characters !"),
    DEPARTMENT_EXISTED(1005, "Department existed !"),
    STUDENT_EXISTED(1006, "Student existed !"),
    INVALID_STUDENT_INPUT_PARAM(1006, "Have one or more attribute of student is null or invalid !"),
    SUBJECT_EXISTED(1007, "Subject existed !"),
    INVALID_SUBJECT_NAME_PARAM(1007, "Subject name is invalid !"),
    INVALID_DEPARTMENT_NAME_PARAM(1007, "Department name is invalid !"),
    TEACHER_EXISTED(1008, "Teacher existed !"),
    INVALID_JOB_FOR_TEACHER_PARAM(1009, "Have one or more attribute of job is null or invalid !"),
    INVALID_TEACHER_INPUT_PARAM(1006, "Have one or more attribute of teacher is null or invalid !"),
    EXPIRATION_TOKEN(9996, "Token expired !"),
    INVALID_PAGE_PARAM(9997, "Page parameter in show all items is null or must be at least 1 !"),
    INVALID_LIMIT_PARAM(9997, "Limit of page parameter in show all items is null or must be at least 1 !"),
    INVALID_ORDER_BY_PARAM(9997, "OrderBy parameter in show all items is invalid !"),
    INVALID_ORDER_DIRECTION_PARAM(9997, "OrderDirection parameter in show all items is invalid !"),
    UNAUTHORIZED(9998, "User is not permitted !"),
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error !");

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private final int code;
    private final String message;

}
