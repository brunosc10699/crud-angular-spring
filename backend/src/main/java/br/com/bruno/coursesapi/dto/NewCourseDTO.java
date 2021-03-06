package br.com.bruno.coursesapi.dto;

import br.com.bruno.coursesapi.entities.Course;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class NewCourseDTO {

    @NotEmpty(message = "Enter the course name")
    private String name;

    @NotEmpty(message = "Enter the category name")
    private String category;

    public static NewCourseDTO toDTO(Course course) {
        return NewCourseDTO.builder()
                .name(course.getName())
                .category(course.getCategory())
                .build();
    }
}
