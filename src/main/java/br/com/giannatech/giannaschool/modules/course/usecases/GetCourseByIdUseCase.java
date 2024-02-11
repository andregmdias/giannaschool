package br.com.giannatech.giannaschool.modules.course.usecases;

import br.com.giannatech.giannaschool.exceptions.CourseNotFoundException;
import br.com.giannatech.giannaschool.modules.course.entities.Course;
import br.com.giannatech.giannaschool.modules.course.repositories.CourseRepository;
import java.util.UUID;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetCourseByIdUseCase {

  @Autowired
  private CourseRepository courseRepository;

  @Autowired
  private ModelMapper modelMapper;

  public Course execute(UUID id) {
    var course = courseRepository
        .findById(id)
        .orElseThrow(() -> new CourseNotFoundException("Course not found"));

    return course;
  }
}
