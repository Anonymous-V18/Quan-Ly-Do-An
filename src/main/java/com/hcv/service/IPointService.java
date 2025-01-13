package com.hcv.service;

import com.hcv.dto.request.PointInsertInput;
import com.hcv.dto.request.PointInsertListInput;
import com.hcv.dto.request.PointUpdateInput;
import com.hcv.dto.request.PointUpdateListInput;
import com.hcv.dto.response.PointResponse;
import com.hcv.entity.Point;
import com.hcv.entity.Research;
import com.hcv.entity.TypePoint;

import java.util.List;

public interface IPointService {

    List<PointResponse> insertList(PointInsertListInput pointInsertListInput);

    Point insert(String teacherId, TypePoint typePoint, PointInsertInput pointInsertInput);

    List<PointResponse> updateList(PointUpdateListInput pointUpdateListInput);

    Point update(String teacherId, PointUpdateInput newPointDTO);

    void checkPointTeacher(String teacherId, Research research, String typePoint);

    void delete(String[] ids);

}
