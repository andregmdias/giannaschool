package br.com.giannatech.giannaschool.modules.course.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCourseDTO {

  @NotBlank(message = "Field [name] is required")
  private String name;

  @NotBlank(message = "Field [category] is required")
  private String category;
  private boolean active;
}
