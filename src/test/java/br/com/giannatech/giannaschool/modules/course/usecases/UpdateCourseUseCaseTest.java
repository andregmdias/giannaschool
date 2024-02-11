package br.com.giannatech.giannaschool.modules.course.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import br.com.giannatech.giannaschool.exceptions.CourseNotFoundException;
import br.com.giannatech.giannaschool.modules.course.dtos.UpdateCourseDTO;
import br.com.giannatech.giannaschool.modules.course.entities.Course;
import br.com.giannatech.giannaschool.modules.course.mappers.UpdateCourseMapper;
import br.com.giannatech.giannaschool.modules.course.repositories.CourseRepository;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UpdateCourseUseCaseTest {


  @Mock
  CourseRepository courseRepository;

  @Mock
  UpdateCourseMapper updateCourseMapper;

  @Mock
  GetCourseByIdUseCase getCourseByIdUseCase;

  @InjectMocks
  UpdateCourseUseCase updateCourseUseCase;

  @Test
  @DisplayName("test execute -> should update the fetched course with the given params")
  void testExecute_shouldCreateACourseWithSuccess() {
    var id = UUID.randomUUID();
    var updateCourseDTO = UpdateCourseDTO.builder().name("Bar course").category("java").build();

    var course = Course.builder()
        .id(id)
        .name(updateCourseDTO.getName())
        .category(updateCourseDTO.getCategory())
        .active(true)
        .createdAt(LocalDateTime.now())
        .updatedAt(LocalDateTime.now())
        .build();

    given(updateCourseMapper.updateCourseFromDTO(course, updateCourseDTO)).willReturn(course);
    given(getCourseByIdUseCase.execute(course.getId())).willReturn(course);
    given(courseRepository.save(course)).willReturn(course);

    var result = updateCourseUseCase.execute(id, updateCourseDTO);

    verify(getCourseByIdUseCase, times(1)).execute(id);
    verify(updateCourseMapper, times(1)).updateCourseFromDTO(course, updateCourseDTO);
    verify(courseRepository, times(1)).save(course);
    assertInstanceOf(Course.class, result);
  }

  @Test
  @DisplayName("test execute -> should should throw an exception when the course with the given param doesnt exists")
  void testExecute_shouldThrowAnExceptionWhenTheCourseWithTheGivenIdDoesntExists() {
    var id = UUID.randomUUID();
    var updateCourseDTO = UpdateCourseDTO.builder().name("Bar course").category("java").build();

    given(getCourseByIdUseCase.execute(id)).willThrow(
        new CourseNotFoundException("Course not found"));

    var result = assertThrows(CourseNotFoundException.class,
        () -> updateCourseUseCase.execute(id, updateCourseDTO));

    verify(updateCourseMapper, never()).updateCourseFromDTO(new Course(), updateCourseDTO);
    verify(courseRepository, never()).save(any(Course.class));

    assertEquals("Course not found", result.getMessage());

  }
}