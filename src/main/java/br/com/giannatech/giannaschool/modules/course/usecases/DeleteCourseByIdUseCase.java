package br.com.giannatech.giannaschool.modules.course.usecases;

import br.com.giannatech.giannaschool.modules.course.repositories.CourseRepository;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteCourseByIdUseCase {

  @Autowired
  private CourseRepository courseRepository;

  public void execute(UUID id) {
    courseRepository.deleteById(id);
  }

}
