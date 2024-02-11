package br.com.giannatech.giannaschool.modules.course.usecases;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
class DeleteCourseByIdUseCaseTest {

  @Mock
  CourseRepository courseRepository;

  @InjectMocks
  DeleteCourseByIdUseCase deleteCourseByIdUseCase;

  @Test
  @DisplayName("teste execute -> Should delete the course with the given id")
  void testExecute_ShouldDeleteTheCourseWithTheGivenId() {
    var course = Course.builder()
        .id(UUID.randomUUID())
        .name("Foo")
        .category("Bar")
        .active(true)
        .createdAt(LocalDateTime.now())
        .updatedAt(LocalDateTime.now())
        .build();

    doNothing().when(courseRepository).deleteById(course.getId());

    deleteCourseByIdUseCase.execute(course.getId());

    verify(courseRepository, times(1)).deleteById(course.getId());
  }
}