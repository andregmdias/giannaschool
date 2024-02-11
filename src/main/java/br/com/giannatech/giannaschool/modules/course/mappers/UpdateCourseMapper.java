package br.com.giannatech.giannaschool.modules.course.mappers;

import br.com.giannatech.giannaschool.modules.course.dtos.UpdateCourseDTO;
import br.com.giannatech.giannaschool.modules.course.entities.Course;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateCourseMapper {

  @Autowired
  private ModelMapper modelMapper;
  
  public Course updateCourseFromDTO(Course existingCourse, UpdateCourseDTO updateDTO) {
    modelMapper.map(updateDTO, existingCourse);
    return existingCourse;
  }
}
