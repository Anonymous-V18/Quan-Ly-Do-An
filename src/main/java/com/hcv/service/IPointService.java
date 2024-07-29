package com.hcv.service;

import com.hcv.dto.request.Point.PointInsertInput;
import com.hcv.dto.request.Point.PointUpdateInput;
import com.hcv.dto.response.PointResponse;

public interface IPointService {

    PointResponse insert(PointInsertInput pointInsertInput);

    PointResponse update(String idOldPoint, PointUpdateInput newPointDTO);

    void checkTypePoint(String typePoint);

    void checkPointTeacherValid(String teacherId, String studentId, String typePoint);

    void delete(String[] ids);

}
