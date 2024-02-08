package br.com.giannatech.giannaschool.modules.course.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCourseDTO {

  private String name;
  private String category;
  private boolean active;
}
