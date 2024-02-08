package br.com.giannatech.giannaschool.modules.course.controllers;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.giannatech.giannaschool.modules.course.dtos.CreateCourseDTO;
import br.com.giannatech.giannaschool.modules.course.entities.Course;
import br.com.giannatech.giannaschool.modules.course.usecases.CreateCourseUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest
class CourseControllerTest {

  @MockBean
  CreateCourseUseCase createCourseUseCase;
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  @DisplayName("test create -> when all params are valid, should return a course")
  void testCreate_WhenAllParamsAreValidShouldReturnACourse() throws Exception {
    var courseDTO = CreateCourseDTO.builder()
        .name("Bar course")
        .category("java")
        .active(true)
        .build();

    var course = Course.builder()
        .id(UUID.randomUUID())
        .name(courseDTO.getName())
        .category(courseDTO.getCategory())
        .active(courseDTO.isActive())
        .createdAt(LocalDateTime.now())
        .updatedAt(LocalDateTime.now())
        .build();

    given(createCourseUseCase.execute(courseDTO)).willReturn(course);

    ResultActions response = mockMvc.perform(post("/courses")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(course)));

    response.andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id", notNullValue()))
        .andExpect(jsonPath("$.name", is(courseDTO.getName())))
        .andExpect(jsonPath("$.category", is(courseDTO.getCategory())))
        .andExpect(jsonPath("$.active", is(courseDTO.isActive())));
  }
}