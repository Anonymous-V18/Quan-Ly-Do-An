package com.hcv.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseDTO<T> {

    protected Long id;

}
