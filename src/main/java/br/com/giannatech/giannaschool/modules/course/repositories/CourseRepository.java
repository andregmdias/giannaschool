package br.com.giannatech.giannaschool.modules.course.repositories;

import br.com.giannatech.giannaschool.modules.course.entities.Course;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<UUID, Course> {

}
