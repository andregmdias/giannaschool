package br.com.giannatech.giannaschool.modules.course.usecases;

import br.com.giannatech.giannaschool.modules.course.dtos.UpdateCourseDTO;
import br.com.giannatech.giannaschool.modules.course.entities.Course;
import br.com.giannatech.giannaschool.modules.course.mappers.UpdateCourseMapper;
import br.com.giannatech.giannaschool.modules.course.repositories.CourseRepository;
import java.util.UUID;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateCourseUseCase {


  @Autowired
  private CourseRepository courseRepository;

  @Autowired
  private UpdateCourseMapper updateCourseMapper;

  @Autowired
  private GetCourseByIdUseCase getCourseByIdUseCase;

  @Autowired
  private ModelMapper modelMapper;

  public Course execute(UUID id, UpdateCourseDTO dto) {
    var actualCourse = getCourseByIdUseCase.execute(id);

    var updatedCourse = updateCourseMapper.updateCourseFromDTO(actualCourse, dto);

    return courseRepository.save(updatedCourse);
  }
}
