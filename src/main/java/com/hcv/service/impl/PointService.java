package com.hcv.service.impl;

import com.hcv.constant.TypePointConst;
import com.hcv.converter.IPointMapper;
import com.hcv.dto.request.PointInsertInput;
import com.hcv.dto.request.PointInsertListInput;
import com.hcv.dto.request.PointUpdateInput;
import com.hcv.dto.response.PointResponse;
import com.hcv.entity.*;
import com.hcv.exception.AppException;
import com.hcv.exception.ErrorCode;
import com.hcv.repository.IPointRepository;
import com.hcv.repository.IStudentRepository;
import com.hcv.repository.ITypePointRepository;
import com.hcv.service.IPointService;
import com.hcv.service.IUserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PointService implements IPointService {

    IUserService userService;
    IStudentRepository studentRepository;
    IPointRepository pointRepository;
    ITypePointRepository typePointRepository;
    IPointMapper pointMapper;

    @Override
    @Transactional
    public List<PointResponse> insertList(PointInsertListInput pointInsertListInput) {
        String teacherId = userService.getClaimsToken().get("sub").toString();

        String typePointId = pointInsertListInput.getTypePointId();
        TypePoint typePoint = typePointRepository.findById(typePointId)
                .orElseThrow(() -> new AppException(ErrorCode.POINT_TYPE_INVALID));

        return pointInsertListInput.getPoints().stream()
                .map(pointInsertInput -> this.insert(teacherId, typePoint, pointInsertInput))
                .toList();
    }

    @Override
    public PointResponse insert(String teacherId, TypePoint typePoint, PointInsertInput pointInsertInput) {
        String studentId = pointInsertInput.getStudentId();
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOT_EXIST));

        this.checkPointTeacher(teacherId, student, typePoint.getName());

        Point point = pointMapper.toEntity(pointInsertInput);
        point.setTeacherId(teacherId);
        point.setTypePoint(typePoint);

        List<Point> pointListOfStudent = student.getPoints();
        pointListOfStudent.forEach(point1 -> {
            if (point1.getTeacherId().equals(teacherId)) {
                throw new AppException(ErrorCode.POINT_EXIST);
            }
        });
        point.setStudent(student);

        point = pointRepository.save(point);
        return pointMapper.toDTO(point);
    }

    @Override
    public PointResponse update(String oldPointId, PointUpdateInput newPointDTO) {
        Point oldPoint = pointRepository.findById(oldPointId)
                .orElseThrow(() -> new AppException(ErrorCode.POINT_NOT_EXIST));

        this.checkPointTeacher(userService.getClaimsToken().get("sub").toString(),
                oldPoint.getStudent(),
                oldPoint.getTypePoint().getName());

        oldPoint.setPoint(newPointDTO.getPoint());

        oldPoint = pointRepository.save(oldPoint);
        return pointMapper.toDTO(oldPoint);
    }

    @Override
    public void delete(String[] ids) {
        pointRepository.deleteAllById(Arrays.stream(ids).toList());
    }

    @Override
    public void checkPointTeacher(String teacherId, Student student, String typePointName) {
        Research research = student.getGroup().getResearch();

        List<String> instructorsIds = research.getInstructorsIds();
        String thesisAdvisorsId = research.getThesisAdvisorId();

        boolean isValid;
        switch (typePointName) {
            case TypePointConst.POINT_INSTRUCTORS -> isValid = instructorsIds.contains(teacherId);
            case TypePointConst.POINT_THESIS_ADVISOR -> isValid = thesisAdvisorsId != null && thesisAdvisorsId.equals(teacherId);
            case TypePointConst.POINT_COUNCIL -> {
                List<String> councilIdList = research.getTeachers().stream()
                        .filter(teacherEntity -> instructorsIds.contains(teacherEntity.getId())
                                || teacherEntity.getId().equals(thesisAdvisorsId)
                        )
                        .map(BaseEntity::getId)
                        .toList();
                isValid = councilIdList.contains(teacherId);
            }
            default -> throw new AppException(ErrorCode.POINT_TYPE_INVALID);
        }

        if (!isValid) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
    }
}
