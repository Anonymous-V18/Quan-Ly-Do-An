package com.hcv.service;

import com.hcv.dto.request.PointInsertInput;
import com.hcv.dto.request.PointInsertListInput;
import com.hcv.dto.request.PointUpdateInput;
import com.hcv.dto.response.PointResponse;
import com.hcv.entity.Student;
import com.hcv.entity.TypePoint;

import java.util.List;

public interface IPointService {

    List<PointResponse> insertList(PointInsertListInput pointInsertListInput);

    PointResponse insert(String teacherId, TypePoint typePoint, PointInsertInput pointInsertInput);

    PointResponse update(String oldPointId, PointUpdateInput newPointDTO);

    void checkPointTeacher(String teacherId, Student student, String typePoint);

    void delete(String[] ids);

}
