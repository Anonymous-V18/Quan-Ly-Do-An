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
    INVALID_PAGE_PARAM(1022, "Page parameter in show all items is null or must be at least 1 !", HttpStatus.BAD_REQUEST),
    INVALID_LIMIT_PARAM(1023, "Limit of page parameter in show all items is null or must be at least 1 !", HttpStatus.BAD_REQUEST),
    INVALID_ORDER_BY_PARAM(1024, "OrderBy parameter in show all items is invalid !", HttpStatus.BAD_REQUEST),
    INVALID_ORDER_DIRECTION_PARAM(1025, "OrderDirection parameter in show all items is invalid !", HttpStatus.BAD_REQUEST),
    RESEARCH_NOT_EXISTED(1026, "Research isn't exist !", HttpStatus.NOT_FOUND),
    RESEARCH_EXISTED(1027, "Research have already existed !", HttpStatus.CONFLICT),
    TEACHER_DUPLICATED(1028, "Teacher can't duplicated in the same research !", HttpStatus.CONFLICT),
    NAME_PARAM_RESEARCH_INVALID(1029, "Name of research is invalid !", HttpStatus.BAD_REQUEST),
    MADETAI_PARAM_RESEARCH_INVALID(1030, "MaDeTai of research is null or invalid !", HttpStatus.BAD_REQUEST),
    MAX_MEMBER_PRAM_RESEARCH_INVALID(1031, "Max Member of research is less than 4 member !", HttpStatus.BAD_REQUEST),
    MIN_MEMBER_PRAM_RESEARCH_INVALID(1032, "Min Member of research is more than 1 member !", HttpStatus.BAD_REQUEST),
    GVHD_PARAM_RESEARCH_INVALID(1033, "GVHD of research is null or invalid !", HttpStatus.BAD_REQUEST),
    GVPB_PARAM_RESEARCH_INVALID(1034, "GVPB of research is null or invalid !", HttpStatus.BAD_REQUEST),
    DOTDANGKY_PARAM_RESEARCH_INVALID(1035, "DotDangKy of research is null or invalid !", HttpStatus.BAD_REQUEST),
    NAMHOC_PARAM_RESEARCH_INVALID(1036, "NamHoc of research is null or invalid !", HttpStatus.BAD_REQUEST),
    SUBJECTS_PARAM_RESEARCH_INVALID(1037, "Subject of research is null or invalid !", HttpStatus.BAD_REQUEST),
    MESSAGE_PARAM_FEEDBACK_INVALID(1038, "Message of feedback is null or invalid !", HttpStatus.BAD_REQUEST),
    SENDTO_PARAM_FEEDBACK_INVALID(1039, "SendTo of feedback is null or invalid !", HttpStatus.BAD_REQUEST),
    SENDFROM_PARAM_FEEDBACK_INVALID(1040, "SendFrom of feedback is null or invalid !", HttpStatus.BAD_REQUEST),
    RESEARCHES_PARAM_FEEDBACK_INVALID(1041, "Researches of feedback is null or invalid !", HttpStatus.BAD_REQUEST),
    GROUP_NOT_CHANGE_MEMBER(1042, "Nhóm không được phép thay đổi thành viên sau khi đã đăng ký đề tài !", HttpStatus.METHOD_NOT_ALLOWED),
    STUDENT_EXISTED_IN_OTHER_GROUP(1043, "Có sinh viên đã tham gia trong nhóm khác !", HttpStatus.CONFLICT),
    STUDENT_NOT_EXIST(1044, "Students didn't exist !", HttpStatus.NOT_FOUND),
    MASO_INVALID(1045, "MaSo is invalid !", HttpStatus.BAD_REQUEST),
    NAME_INVALID(1046, "Name is invalid !", HttpStatus.BAD_REQUEST),
    EMAIL_INVALID(1047, "Email is invalid !", HttpStatus.BAD_REQUEST),
    PHONE_NUMBER_INVALID(1048, "Phone number is invalid !", HttpStatus.BAD_REQUEST),
    SUBJECT_NAME_INVALID(1049, "SubjectName is invalid !", HttpStatus.BAD_REQUEST),
    DEPARTMENT_NAME_INVALID(1050, "DepartmentName is invalid !", HttpStatus.BAD_REQUEST),
    MY_CLASS_INVALID(1045, "My class is invalid !", HttpStatus.BAD_REQUEST),
    HOCVI_INVALID(1045, "HocVi is invalid !", HttpStatus.BAD_REQUEST),
    POSITION_INVALID(1045, "ChucVu is invalid !", HttpStatus.BAD_REQUEST),
    POINT_TYPE_INVALID(1046, "Type of Point is invalid !", HttpStatus.BAD_REQUEST),
    POINT_INVALID(1047, "Point parameter is invalid !", HttpStatus.BAD_REQUEST),
    POINT_NOT_EXIST(1048, "Point isn't exist !", HttpStatus.NOT_FOUND),
    POINT_EXIST(1049, "Point existed !", HttpStatus.CONFLICT),
    FEEDBACK_NOT_EXISTED(1049, "Feedback isn't exist !", HttpStatus.NOT_FOUND),
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
