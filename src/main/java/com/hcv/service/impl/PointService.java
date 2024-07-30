package com.hcv.service.impl;

import com.hcv.constant.PointType;
import com.hcv.converter.IPointMapper;
import com.hcv.dto.request.point.PointInsertInput;
import com.hcv.dto.request.point.PointUpdateInput;
import com.hcv.dto.response.PointResponse;
import com.hcv.entity.BaseEntity;
import com.hcv.entity.PointEntity;
import com.hcv.entity.ResearchEntity;
import com.hcv.entity.StudentEntity;
import com.hcv.exception.AppException;
import com.hcv.exception.ErrorCode;
import com.hcv.repository.IPointRepository;
import com.hcv.repository.IStudentRepository;
import com.hcv.service.IPointService;
import com.hcv.service.IUserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PointService implements IPointService {

    IUserService currentUser;
    IStudentRepository studentRepository;
    IPointRepository pointRepository;
    IPointMapper pointMapper;

    @Override
    public PointResponse insert(PointInsertInput pointInsertInput) {
        this.checkTypePoint(pointInsertInput.getType());
        this.checkPointTeacherValid(currentUser.getSubToken()
                , pointInsertInput.getStudentId(),
                pointInsertInput.getType());


        PointEntity pointEntity = pointMapper.toEntity(pointInsertInput);

        String teacherId = currentUser.getSubToken();
        pointEntity.setTeacherId(teacherId);

        String studentId = pointInsertInput.getStudentId();
        StudentEntity studentEntity = studentRepository.findOneById(studentId);
        if (studentEntity == null) {
            throw new AppException(ErrorCode.STUDENT_NOT_EXIST);
        }
        List<PointEntity> pointEntityListOfStudent = studentEntity.getPoints();
        pointEntityListOfStudent.forEach(pointEntity1 -> {
            if (pointEntity1.getTeacherId().equals(teacherId)) {
                throw new AppException(ErrorCode.POINT_EXIST);
            }
        });
        pointEntity.setStudents(studentEntity);

        pointEntity = pointRepository.save(pointEntity);

        return pointMapper.toDTO(pointEntity);
    }

    @Override
    public PointResponse update(String oldPointId, PointUpdateInput newPointDTO) {
        PointEntity oldPointEntity = pointRepository.findOneById(oldPointId);
        if (oldPointEntity == null) {
            throw new AppException(ErrorCode.POINT_NOT_EXIST);
        }

        this.checkPointTeacherValid(currentUser.getSubToken()
                , oldPointEntity.getStudents().getId(),
                oldPointEntity.getType());

        oldPointEntity.setPoint(newPointDTO.getPoint());
        oldPointEntity = pointRepository.save(oldPointEntity);

        return pointMapper.toDTO(oldPointEntity);
    }

    @Override
    public void delete(String[] ids) {
        pointRepository.deleteAllById(Arrays.stream(ids).toList());
    }


    @Override
    public void checkTypePoint(String typePoint) {
        List<String> listOfTypePoint = List.of(PointType.POINT_INSTRUCTORS, PointType.POINT_THESIS_ADVISOR, PointType.POINT_COUNCIL);
        boolean isTypePointValid = listOfTypePoint.contains(typePoint);
        if (!isTypePointValid) {
            throw new AppException(ErrorCode.POINT_TYPE_INVALID);
        }
    }

    @Override
    public void checkPointTeacherValid(String teacherId, String studentId, String typePoint) {
        StudentEntity studentEntity = studentRepository.findOneById(studentId);
        if (studentEntity == null) {
            throw new AppException(ErrorCode.STUDENT_NOT_EXIST);
        }
        ResearchEntity researchEntity = studentEntity.getGroups().getResearches();

        String instructorsId = researchEntity.getGvhd();
        String thesisAdvisorsId = researchEntity.getGvpb();

        boolean isValid = false;
        switch (typePoint) {
            case PointType.POINT_INSTRUCTORS -> isValid = instructorsId.equals(teacherId);
            case PointType.POINT_THESIS_ADVISOR -> isValid = thesisAdvisorsId.equals(teacherId);
            case PointType.POINT_COUNCIL -> {
                List<String> councilIdList = researchEntity.getTeachers().stream()
                        .filter(teacherEntity -> teacherEntity.getId().equals(instructorsId)
                                || teacherEntity.getId().equals(thesisAdvisorsId)
                        )
                        .map(BaseEntity::getId)
                        .toList();
                isValid = councilIdList.contains(teacherId);
            }
        }
        if (!isValid) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
    }
}
