package br.com.giannatech.giannaschool.modules.course.usecases;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import br.com.giannatech.giannaschool.modules.course.dtos.CreateCourseDTO;
import br.com.giannatech.giannaschool.modules.course.entities.Course;
import br.com.giannatech.giannaschool.modules.course.repositories.CourseRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
class CreateCourseUseCaseTest {

  @Mock
  CourseRepository courseRepository;

  @Mock
  ModelMapper modelMapper;

  @InjectMocks
  CreateCourseUseCase createCourseUseCase;

  @Test
  @DisplayName("test execute -> should create a course with success")
  void testExecute_shouldCreateACourseWithSuccess() {
    var courseDTO = CreateCourseDTO.builder().name("Bar course").category("java").active(true)
        .build();

    var course = Course.builder()
        .name(courseDTO.getName())
        .category(courseDTO.getCategory())
        .active(courseDTO.isActive())
        .createdAt(LocalDateTime.now())
        .updatedAt(LocalDateTime.now())
        .build();

    given(modelMapper.map(courseDTO, Course.class)).willReturn(course);
    given(courseRepository.save(course)).willReturn(course);

    var result = createCourseUseCase.execute(courseDTO);

    verify(modelMapper, times(1)).map(courseDTO, Course.class);
    verify(courseRepository, times(1)).save(course);
    assertInstanceOf(Course.class, result);
  }
}