package com.hcv.service.impl;

import com.hcv.constant.PointTypeConst;
import com.hcv.converter.IPointMapper;
import com.hcv.dto.request.PointInsertInput;
import com.hcv.dto.request.PointUpdateInput;
import com.hcv.dto.response.PointResponse;
import com.hcv.entity.BaseEntity;
import com.hcv.entity.Point;
import com.hcv.entity.Research;
import com.hcv.entity.Student;
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

    IUserService userService;
    IStudentRepository studentRepository;
    IPointRepository pointRepository;
    IPointMapper pointMapper;

    @Override
    public PointResponse insert(PointInsertInput pointInsertInput) {
        this.checkTypePoint(pointInsertInput.getType());
        this.checkPointTeacherValid(userService.getClaimsToken().get("sub").toString()
                , pointInsertInput.getStudentId()
                , pointInsertInput.getType());

        Point point = pointMapper.toEntity(pointInsertInput);

        String teacherId = userService.getClaimsToken().get("sub").toString();
        point.setTeacherId(teacherId);

        String studentId = pointInsertInput.getStudentId();
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOT_EXIST));

        List<Point> pointListOfStudent = student.getPoints();
        pointListOfStudent.forEach(pointEntity1 -> {
            if (pointEntity1.getTeacherId().equals(teacherId)) {
                throw new AppException(ErrorCode.POINT_EXIST);
            }
        });
        point.setStudents(student);

        point = pointRepository.save(point);

        return pointMapper.toDTO(point);
    }

    @Override
    public PointResponse update(String oldPointId, PointUpdateInput newPointDTO) {
        Point oldPoint = pointRepository.findById(oldPointId)
                .orElseThrow(() -> new AppException(ErrorCode.POINT_NOT_EXIST));

        this.checkPointTeacherValid(userService.getClaimsToken().get("sub").toString(),
                oldPoint.getStudents().getId(),
                oldPoint.getType());

        oldPoint.setPoint(newPointDTO.getPoint());
        oldPoint = pointRepository.save(oldPoint);

        return pointMapper.toDTO(oldPoint);
    }

    @Override
    public void delete(String[] ids) {
        pointRepository.deleteAllById(Arrays.stream(ids).toList());
    }


    @Override
    public void checkTypePoint(String typePoint) {
        List<String> listOfTypePoint = List.of(PointTypeConst.POINT_INSTRUCTORS, PointTypeConst.POINT_THESIS_ADVISOR, PointTypeConst.POINT_COUNCIL);
        boolean isTypePointValid = listOfTypePoint.contains(typePoint);
        if (!isTypePointValid) {
            throw new AppException(ErrorCode.POINT_TYPE_INVALID);
        }
    }

    @Override
    public void checkPointTeacherValid(String teacherId, String studentId, String typePoint) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOT_EXIST));

        Research research = student.getGroups().getResearches();

        List<String> instructorsIds = research.getInstructorsIds();
        String thesisAdvisorsId = research.getThesisAdvisorId();

        boolean isValid;
        switch (typePoint) {
            case PointTypeConst.POINT_INSTRUCTORS -> isValid = instructorsIds.contains(teacherId);
            case PointTypeConst.POINT_THESIS_ADVISOR -> isValid = thesisAdvisorsId.equals(teacherId);
            case PointTypeConst.POINT_COUNCIL -> {
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
