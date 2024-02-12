package br.com.giannatech.giannaschool.modules.course.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.giannatech.giannaschool.exceptions.CourseNotFoundException;
import br.com.giannatech.giannaschool.modules.course.dtos.CreateCourseDTO;
import br.com.giannatech.giannaschool.modules.course.dtos.UpdateCourseDTO;
import br.com.giannatech.giannaschool.modules.course.entities.Course;
import br.com.giannatech.giannaschool.modules.course.usecases.CreateCourseUseCase;
import br.com.giannatech.giannaschool.modules.course.usecases.DeleteCourseByIdUseCase;
import br.com.giannatech.giannaschool.modules.course.usecases.GetCoursesUseCase;
import br.com.giannatech.giannaschool.modules.course.usecases.ToggleCourseStatusUseCase;
import br.com.giannatech.giannaschool.modules.course.usecases.UpdateCourseUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
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

  @MockBean
  private GetCoursesUseCase getCoursesUseCase;

  @MockBean
  private UpdateCourseUseCase updateCourseUseCase;

  @MockBean
  private ToggleCourseStatusUseCase toggleCourseStatusUseCase;

  @MockBean
  private DeleteCourseByIdUseCase deleteCourseByIdUseCase;

  @Test
  @DisplayName("test create -> when all params are valid, should return a Course")
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
    given(createCourseUseCase.execute(any(CreateCourseDTO.class))).willReturn(createdCourse);

    ResultActions response = mockMvc.perform(post("/cursos")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(dto)));

    response.andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.name").value(dto.getName()))
        .andExpect(jsonPath("$.category").value(dto.getCategory()))
        .andExpect(jsonPath("$.active").value(dto.isActive()));
  }

  @Test
  @DisplayName("test create -> when required params are missing, should return an error")
  void testCreate_WhenRequiredParamsAreMissingShouldReturnAnError() throws Exception {
    CreateCourseDTO dto = CreateCourseDTO.builder()
        .name("")
        .category("")
        .active(true)
        .build();

    ResultActions response = mockMvc.perform(post("/cursos")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(dto)));

    response.andExpect(status().isUnprocessableEntity())
        .andExpect(jsonPath("$.name").value("Field [name] is required"))
        .andExpect(jsonPath("$.category").value("Field [category] is required"));

    verify(createCourseUseCase, never()).execute(any(CreateCourseDTO.class));
  }

  @Test
  @DisplayName("test index -> when has no search params, should return a list with the existent courses")
  void testIndex_ShouldReturnAnListWithTheExistentCourses() throws Exception {

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

    given(getCoursesUseCase.execute(null, null)).willReturn(List.of(course0, course1));

    ResultActions response = mockMvc.perform(
        get("/cursos").contentType(MediaType.APPLICATION_JSON)
    );

    response.andExpectAll(
        status().isOk(),
        jsonPath("$", hasSize(2)),
        jsonPath("$[0].name", is(course0.getName())),
        jsonPath("$[1].name", is(course1.getName()))
    );
  }

  @Test
  @DisplayName("test index -> when has search params, should return a list according the given params")
  void testIndex_ShouldReturnAnListAccordingTheGivenParams() throws Exception {
    var course0 = Course.builder()
        .id(UUID.randomUUID())
        .name("Foo course")
        .category("Bar category")
        .active(true)
        .createdAt(LocalDateTime.now())
        .updatedAt(LocalDateTime.now())
        .build();

    given(getCoursesUseCase.execute("Foo", null)).willReturn(List.of(course0));

    ResultActions response = mockMvc.perform(
        get("/cursos?name=Foo")
    );

    response.andExpectAll(
        status().isOk(),
        jsonPath("$", hasSize(1)),
        jsonPath("$[0].name", is(course0.getName()))
    );
  }

  @Test
  @DisplayName("test update -> when the course exists with the given id and the update params are valid, should update the course")
  void testUpdate_WhenTheCourseExistsWithTheGivenIdAndTheParamsUpdateAreValidShouldUpdateTheCourse()
      throws Exception {
    var course0 = Course.builder()
        .id(UUID.randomUUID())
        .name("Foo")
        .category("Baz")
        .active(true)
        .createdAt(LocalDateTime.now())
        .updatedAt(LocalDateTime.now())
        .build();

    var updateCourseParams = UpdateCourseDTO.builder().name("Foo").category("Baz").build();

    given(updateCourseUseCase.execute(course0.getId(), updateCourseParams)).willReturn(course0);

    ResultActions response = mockMvc.perform(
        put("/cursos/" + course0.getId().toString())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updateCourseParams))
    );

    response.andExpectAll(
        status().isOk(),
        jsonPath("$.id", is(course0.getId().toString())),
        jsonPath("$.name", is(updateCourseParams.getName())),
        jsonPath("$.category", is(updateCourseParams.getCategory()))
    );
  }

  @Test
  @DisplayName("test update -> when the course with the given Id doesnt exists, should thrown an exception")
  void testUpdate_WhenTheCourseWithTheGivenIdDoesntExistsShouldThrowAnException()
      throws Exception {

    var id = UUID.randomUUID();
    var updateCourseParams = UpdateCourseDTO.builder().name("Foo").category("Baz").build();

    given(updateCourseUseCase.execute(id, updateCourseParams)).willThrow(
        new CourseNotFoundException("Course not found"));

    ResultActions response = mockMvc.perform(
        put("/cursos/" + id.toString())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updateCourseParams))
    );

    response.andExpectAll(
        status().isNotFound(),
        jsonPath("$", is("Course not found"))
    );
  }

  @Test
  @DisplayName("test deleteById -> should delete the course with the given id")
  void testDeleteById_ShouldDeleteTheCourseWithTheGivenId()
      throws Exception {

    var id = UUID.randomUUID();

    var course0 = Course.builder()
        .id(id)
        .name("Foo")
        .category("Baz")
        .active(true)
        .createdAt(LocalDateTime.now())
        .updatedAt(LocalDateTime.now())
        .build();

    doNothing().when(deleteCourseByIdUseCase).execute(id);

    ResultActions response = mockMvc.perform(delete("/cursos/" + id.toString()));

    response.andExpect(status().isNoContent());
  }

  @Test
  @DisplayName("test active -> when the course exists with the given id, should toggle the active status")
  void testCreate_WhenTheCourseExistsWithTheGivenIdAShouldToggleTheActiveStatus()
      throws Exception {
    var course0 = Course.builder()
        .id(UUID.randomUUID())
        .name("Foo")
        .category("Baz")
        .active(false)
        .createdAt(LocalDateTime.now())
        .updatedAt(LocalDateTime.now())
        .build();

    given(toggleCourseStatusUseCase.execute(course0.getId())).willReturn(course0);

    ResultActions response = mockMvc.perform(
        patch("/cursos/" + course0.getId().toString() + "/active")
            .contentType(MediaType.APPLICATION_JSON)
    );

    response.andExpectAll(
        status().isOk(),
        jsonPath("$.id", is(course0.getId().toString())),
        jsonPath("$.name", is(course0.getName())),
        jsonPath("$.category", is(course0.getCategory())),
        jsonPath("$.active", is(false))
    );
  }

  @Test
  @DisplayName("test active -> when the course with the given Id doesnt exists, should thrown an exception")
  void testActive_WhenTheCourseWithTheGivenIdDoesntExistsShouldThrowAnException()
      throws Exception {

    var id = UUID.randomUUID();

    given(toggleCourseStatusUseCase.execute(id)).willThrow(
        new CourseNotFoundException("Course not found")
    );

    ResultActions response = mockMvc.perform(
        patch("/cursos/" + id.toString() + "/active")
            .contentType(MediaType.APPLICATION_JSON)
    );

    response.andExpectAll(
        status().isNotFound(),
        jsonPath("$", is("Course not found"))
    );
  }
}
