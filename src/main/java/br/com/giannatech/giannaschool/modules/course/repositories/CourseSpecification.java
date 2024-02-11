package br.com.giannatech.giannaschool.modules.course.repositories;

import br.com.giannatech.giannaschool.modules.course.entities.Course;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public class CourseSpecification {

  public static Specification<Course> filterCourse(String name, String category) {
    return ((root, query, criteriaBuilder) -> {
      Predicate namePredicate = criteriaBuilder.like(
          root.get("name"),
          StringUtils.isBlank(name) ? likePattern("") : name
      );

      Predicate categoryPredicate = criteriaBuilder.like(
          root.get("category"),
          StringUtils.isBlank(category) ? likePattern("") : category
      );

      return criteriaBuilder.and(namePredicate, categoryPredicate);
    });
  }

  private static String likePattern(String value) {
    return "%" + value + "%";
  }
}
