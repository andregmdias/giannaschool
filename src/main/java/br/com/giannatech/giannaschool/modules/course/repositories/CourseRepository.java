package br.com.giannatech.giannaschool.modules.course.repositories;

import br.com.giannatech.giannaschool.modules.course.entities.Course;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CourseRepository extends JpaRepository<Course, UUID>,
    JpaSpecificationExecutor<Course> {

  List<Course> findAll(Specification<Course> specification);
}
