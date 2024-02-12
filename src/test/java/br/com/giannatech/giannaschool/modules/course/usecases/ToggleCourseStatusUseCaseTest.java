package br.com.giannatech.giannaschool.modules.course.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import br.com.giannatech.giannaschool.exceptions.CourseNotFoundException;
import br.com.giannatech.giannaschool.modules.course.entities.Course;
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
class ToggleCourseStatusUseCaseTest {

  @Mock
  CourseRepository courseRepository;

  @Mock
  GetCourseByIdUseCase getCourseByIdUseCase;

  @InjectMocks
  ToggleCourseStatusUseCase toggleCourseStatusUseCase;

  @Test
  @DisplayName("test execute -> should toggle the active status")
  void testExecute_ShouldToggletoActiveFalse() {
    var id = UUID.randomUUID();

    var course = Course.builder()
        .id(id)
        .name("Foo")
        .category("Bar")
        .active(true)
        .createdAt(LocalDateTime.now())
        .updatedAt(LocalDateTime.now())
        .build();

    given(getCourseByIdUseCase.execute(course.getId())).willReturn(course);

    given(courseRepository.save(course)).willReturn(course);

    var result = toggleCourseStatusUseCase.execute(id);

    verify(courseRepository, times(1)).save(course);
    assertEquals(false, course.isActive());
  }

  @Test
  @DisplayName("test execute -> should should throw an exception when the course with the given param doesnt exists")
  void testExecute_shouldThrowAnExceptionWhenTheCourseWithTheGivenIdDoesntExists() {
    var id = UUID.randomUUID();
    given(getCourseByIdUseCase.execute(id)).willThrow(
        new CourseNotFoundException("Course not found"));

    var result = assertThrows(CourseNotFoundException.class,
        () -> toggleCourseStatusUseCase.execute(id)
    );

    verify(courseRepository, never()).save(any(Course.class));

    assertEquals("Course not found", result.getMessage());
  }
}