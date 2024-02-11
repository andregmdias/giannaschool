package br.com.giannatech.giannaschool.modules.course.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import br.com.giannatech.giannaschool.modules.course.entities.Course;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
class CourseSpecificationTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private CourseRepository courseRepository;

  private Course course1;
  private Course course2;
  private Course course3;

  @BeforeEach
  public void setup() {
    course1 = Course.builder()
        .name("Course 1")
        .category("Category A")
        .active(true)
        .createdAt(LocalDateTime.now())
        .updatedAt(LocalDateTime.now())
        .build();

    course2 = Course.builder()
        .name("Course 2")
        .category("Category A")
        .active(true)
        .createdAt(LocalDateTime.now())
        .updatedAt(LocalDateTime.now())
        .build();

    course3 = Course.builder()
        .name("Another Course")
        .category("Another Category")
        .active(true)
        .createdAt(LocalDateTime.now())
        .updatedAt(LocalDateTime.now())
        .build();

    entityManager.persist(course1);
    entityManager.persist(course2);
    entityManager.persist(course3);
  }

  @Test
  @DisplayName("test filterCourse -> when filtering by category, should return all courses with the given filter")
  public void testFilterCourse_WhenFilteringByCategoryShouldReturnAllCoursesWithTheGivenFilter() {
    List<Course> courses = courseRepository.findAll(
        CourseSpecification.filterCourse(null, "Category A"));

    assertEquals(2, courses.size());
    assertEquals(course1.getName(), courses.get(0).getName());
    assertEquals(course2.getName(), courses.get(1).getName());
  }

  @Test
  @DisplayName("test filterCourse -> when filtering by name, should return all courses with the given filter")
  public void testFilterCourse_WhenFilteringByCouseShouldReturnAllCoursesWithTheGivenFilter() {
    List<Course> courses = courseRepository.findAll(
        CourseSpecification.filterCourse("Course 1", null));

    assertEquals(1, courses.size());
    assertEquals(course1.getName(), courses.get(0).getName());
  }


  @Test
  @DisplayName("test filterCourse -> when filtering by category and name, should return all courses with the given filter")
  public void testFilterCourse_WhenFilteringByCategoryAndNameShouldReturnAllCoursesWithTheGivenFilter() {
    List<Course> courses = courseRepository.findAll(
        CourseSpecification.filterCourse("Another Course", "Another Category"));

    assertEquals(1, courses.size());
    assertEquals(course3.getName(), courses.get(0).getName());
  }
}