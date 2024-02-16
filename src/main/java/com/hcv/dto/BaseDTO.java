package com.hcv.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public abstract class BaseDTO<T> {

    protected Long id;

    protected String createdBy;

    protected Date createdDate;

    protected String modifiedBy;

    protected Date modifiedDate;

}
