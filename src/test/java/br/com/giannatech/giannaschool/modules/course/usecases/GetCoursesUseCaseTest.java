package br.com.giannatech.giannaschool.modules.course.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import br.com.giannatech.giannaschool.modules.course.entities.Course;
import br.com.giannatech.giannaschool.modules.course.repositories.CourseRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetCoursesUseCaseTest {

  @Mock
  CourseRepository courseRepository;

  @InjectMocks
  GetCoursesUseCase getCoursesUseCase;

  @Test
  @DisplayName("test execute -> should return all existent courses")
  void testExecute_shouldCreateACourseWithSuccess() {
    var course0 = Course.builder()
        .id(UUID.randomUUID())
        .name("Foo course")
        .category("Bar category")
        .active(true)
        .createdAt(LocalDateTime.now())
        .updatedAt(LocalDateTime.now())
        .build();

    var course1 = Course.builder()
        .id(UUID.randomUUID())
        .name("Baz course")
        .category("Bar category")
        .active(true)
        .createdAt(LocalDateTime.now())
        .updatedAt(LocalDateTime.now())
        .build();

    var allCourseList = List.of(course0, course1);

    given(courseRepository.findAll()).willReturn(allCourseList);

    var result = getCoursesUseCase.execute(null, null);
    verify(courseRepository, times(1)).findAll();

    assertEquals(2, result.size());
    assertInstanceOf(Course.class, result.get(0));
    assertInstanceOf(Course.class, result.get(1));
  }
}