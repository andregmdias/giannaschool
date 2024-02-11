package br.com.giannatech.giannaschool.modules.course.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import br.com.giannatech.giannaschool.exceptions.CourseNotFoundException;
import br.com.giannatech.giannaschool.modules.course.entities.Course;
import br.com.giannatech.giannaschool.modules.course.repositories.CourseRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetCourseByIdUseCaseTest {

  @Mock
  CourseRepository courseRepository;

  @InjectMocks
  GetCourseByIdUseCase getCourseByIdUseCase;

  @Test
  @DisplayName("test execute -> should return the course with the given id")
  void testExecute_shouldTheCourseWithTheGivenId() {
    var course0 = Course.builder()
        .id(UUID.randomUUID())
        .name("Foo course")
        .category("Bar category")
        .active(true)
        .createdAt(LocalDateTime.now())
        .updatedAt(LocalDateTime.now())
        .build();

    given(courseRepository.findById(course0.getId())).willReturn(Optional.of(course0));

    var result = getCourseByIdUseCase.execute(course0.getId());
    verify(courseRepository, times(1)).findById(course0.getId());

    assertInstanceOf(Course.class, result);
  }

  @Test
  @DisplayName("test execute -> should throw an exception when the course with the given id doesn't exists")
  void testExecute_shouldThrowAnExceptionWhenTheCourseWithTheGivenIdDoesntExists() {
    var id = UUID.randomUUID();

    given(courseRepository.findById(id)).willReturn(Optional.empty());

    var result = assertThrows(CourseNotFoundException.class, () -> {
      getCourseByIdUseCase.execute(id);
    });

    verify(courseRepository, times(1)).findById(id);

    assertEquals(result.getMessage(), "Course not found");
  }
}