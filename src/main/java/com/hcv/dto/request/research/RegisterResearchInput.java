package com.hcv.dto.request.research;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterResearchInput extends BaseResearchRegistrationProcessInput {

    private String studentID;
}
