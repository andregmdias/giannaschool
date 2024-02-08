package br.com.giannatech.giannaschool.modules.course.controllers;

import br.com.giannatech.giannaschool.modules.course.dtos.CreateCourseDTO;
import br.com.giannatech.giannaschool.modules.course.entities.Course;
import br.com.giannatech.giannaschool.modules.course.usecases.CreateCourseUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/courses")
public class CourseController {

  @Autowired
  private CreateCourseUseCase createCourseUseCase;

  @PostMapping
  public ResponseEntity<Course> create(@RequestBody CreateCourseDTO dto) {
    var course = createCourseUseCase.execute(dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(course);
  }
}
