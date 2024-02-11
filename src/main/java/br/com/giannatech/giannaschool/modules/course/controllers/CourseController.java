package br.com.giannatech.giannaschool.modules.course.controllers;

import br.com.giannatech.giannaschool.modules.course.dtos.CreateCourseDTO;
import br.com.giannatech.giannaschool.modules.course.dtos.UpdateCourseDTO;
import br.com.giannatech.giannaschool.modules.course.entities.Course;
import br.com.giannatech.giannaschool.modules.course.usecases.CreateCourseUseCase;
import br.com.giannatech.giannaschool.modules.course.usecases.GetCoursesUseCase;
import br.com.giannatech.giannaschool.modules.course.usecases.UpdateCourseUseCase;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/courses")
public class CourseController {

  @Autowired
  private CreateCourseUseCase createCourseUseCase;

  @Autowired
  private GetCoursesUseCase getCoursesUseCase;

  @Autowired
  private UpdateCourseUseCase updateCourseUseCase;

  @PostMapping
  public ResponseEntity<Course> create(@Valid @RequestBody CreateCourseDTO dto) {
    var course = createCourseUseCase.execute(dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(course);
  }

  @GetMapping
  public ResponseEntity<List<Course>> index(
      @RequestParam(required = false, name = "name") String name,
      @RequestParam(required = false, name = "category") String category
  ) {
    var courseList = getCoursesUseCase.execute(name, category);
    return ResponseEntity.status(HttpStatus.OK).body(courseList);
  }

  @PutMapping(value = "{id}")
  public ResponseEntity<Course> update(@PathVariable UUID id,
      @RequestBody UpdateCourseDTO updateCourseDTO) {
    var updatedCourse = updateCourseUseCase.execute(id, updateCourseDTO);
    return ResponseEntity.ok(updatedCourse);
  }
}
