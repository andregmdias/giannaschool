package br.com.giannatech.giannaschool.modules.course.usecases;

import br.com.giannatech.giannaschool.modules.course.entities.Course;
import br.com.giannatech.giannaschool.modules.course.repositories.CourseRepository;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ToggleCourseStatusUseCase {

  @Autowired
  private CourseRepository courseRepository;

  @Autowired
  private GetCourseByIdUseCase getCourseByIdUseCase;

  public Course execute(UUID id) {
    var course = getCourseByIdUseCase.execute(id);
    
    if (course.isActive()) {
      course.setActive(false);
    } else {
      course.setActive(false);
    }

    return courseRepository.save(course);
  }
}
