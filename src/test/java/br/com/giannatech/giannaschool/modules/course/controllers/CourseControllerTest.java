package br.com.giannatech.giannaschool.modules.course.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.giannatech.giannaschool.modules.course.dtos.CreateCourseDTO;
import br.com.giannatech.giannaschool.modules.course.entities.Course;
import br.com.giannatech.giannaschool.modules.course.usecases.CreateCourseUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(CourseController.class)
@AutoConfigureMockMvc
public class CourseControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private CreateCourseUseCase createCourseUseCase;

  @Test
  void testCreate_WhenAllParamsAreValidShouldReturnACourse() throws Exception {

    CreateCourseDTO dto = CreateCourseDTO.builder()
        .name("Java Course")
        .category("Programming")
        .active(true)
        .build();

    LocalDateTime now = LocalDateTime.now();

    Course createdCourse = Course.builder()
        .id(UUID.randomUUID())
        .name(dto.getName())
        .category(dto.getCategory())
        .active(dto.isActive())
        .createdAt(now)
        .updatedAt(now)
        .build();
    when(createCourseUseCase.execute(any(CreateCourseDTO.class))).thenReturn(createdCourse);

    ResultActions response = mockMvc.perform(post("/courses")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(dto)));

    response.andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.name").value(dto.getName()))
        .andExpect(jsonPath("$.category").value(dto.getCategory()))
        .andExpect(jsonPath("$.active").value(dto.isActive()));
  }
}
