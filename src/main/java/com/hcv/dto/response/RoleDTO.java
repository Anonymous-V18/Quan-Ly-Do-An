package com.hcv.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hcv.dto.CodeRole;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleDTO {

    String id;
    String name;
    CodeRole code;

}
