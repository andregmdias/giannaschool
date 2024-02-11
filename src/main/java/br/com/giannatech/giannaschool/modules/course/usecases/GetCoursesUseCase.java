package br.com.giannatech.giannaschool.modules.course.usecases;

import br.com.giannatech.giannaschool.modules.course.entities.Course;
import br.com.giannatech.giannaschool.modules.course.repositories.CourseRepository;
import br.com.giannatech.giannaschool.modules.course.repositories.CourseSpecification;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class GetCoursesUseCase {

  @Autowired
  private CourseRepository courseRepository;

  public List<Course> execute(String name, String category) {
    final Specification<Course> specification = CourseSpecification.filterCourse(name, category);
    final List<Course> courses = courseRepository.findAll(specification);
    return courses;
  }
}
