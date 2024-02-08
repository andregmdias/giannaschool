package br.com.giannatech.giannaschool.modules.course.usecases;

import br.com.giannatech.giannaschool.modules.course.dtos.CreateCourseDTO;
import br.com.giannatech.giannaschool.modules.course.entities.Course;
import br.com.giannatech.giannaschool.modules.course.repositories.CourseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateCourseUseCase {

  @Autowired
  private CourseRepository courseRepository;

  @Autowired
  private ModelMapper modelMapper;

  public Course execute(CreateCourseDTO dto){
    var course = modelMapper.map(dto, Course.class);
   return courseRepository.save(course);
  }
}
