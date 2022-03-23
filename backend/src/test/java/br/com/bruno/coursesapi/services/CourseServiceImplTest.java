package br.com.bruno.coursesapi.services;

import br.com.bruno.coursesapi.dto.CourseDTO;
import br.com.bruno.coursesapi.dto.NewCourseDTO;
import br.com.bruno.coursesapi.entities.Course;
import br.com.bruno.coursesapi.repositories.CourseRepository;
import br.com.bruno.coursesapi.services.exceptions.ResourceNotFoundException;
import br.com.bruno.coursesapi.services.impl.CourseServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CourseServiceImplTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseServiceImpl courseService;

    private final Course course = Course.builder()
            .id(1L)
            .name("Java")
            .category("Backend Development")
            .build();

    private final NewCourseDTO newCourseDTO = NewCourseDTO.builder()
            .name("Java")
            .category("Backend Development")
            .build();

    private final CourseDTO courseDTO = CourseDTO.builder()
            .id(1L)
            .name("Java")
            .category("Backend Development")
            .build();

    private final Page<Course> page = new PageImpl<>(Collections.singletonList(course));

    private final PageRequest pageRequest = PageRequest.of(0, 1);

    @Test
    @DisplayName("(1) Must return a page of Course elements")
    void whenFindAllIsCalledThenReturnAPageOfCourses() {
        when(courseRepository.findAll(pageRequest)).thenReturn(page);

        Page<CourseDTO> pageDTO = courseService.findAll(pageRequest);
        assertAll(
                () -> assertThat(pageDTO.getTotalPages(), is(equalTo(1))),
                () -> assertThat(pageDTO.getSize(), is(equalTo(1))),
                () -> assertThat(pageDTO.getContent().get(0).getId(), is(equalTo(course.getId()))),
                () -> assertThat(pageDTO.getContent().get(0).getName(), is(equalTo(course.getName()))),
                () -> assertThat(pageDTO.getContent().get(0).getCategory(), is(equalTo(course.getCategory())))
        );
    }

    @Test
    @DisplayName("(2) Must return an empty elements page")
    void whenFindAllIsCalledThenReturnAnEmptyPage() {
        when(courseRepository.findAll(pageRequest)).thenReturn(Page.empty());

        Page<CourseDTO> page = courseService.findAll(pageRequest);

        assertThat(page.getContent(), is(empty()));
    }

    @Test
    @DisplayName("(3) Must return a course DTO object")
    void whenFindByIdIsCalledThenReturnADTOObject() {
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        CourseDTO newCourseDTO = courseService.findById(1L);

        assertAll(
                () -> assertThat(newCourseDTO.getId(), is(equalTo(course.getId()))),
                () -> assertThat(newCourseDTO.getName(), is(equalTo(course.getName()))),
                () -> assertThat(newCourseDTO.getCategory(), is(equalTo(course.getCategory())))
        );
    }

    @Test
    @DisplayName("(4) Must throw a ResourceNotFoundException exception when looking for a non-existent course")
    void whenFindByIdIsCalledThenThrowAException() {
        when(courseRepository.findById(course.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> courseService.findById(course.getId()));
    }

    @Test
    @DisplayName("(5) Must return the DTO object of a course by its 'name' property")
    void whenFindByNameIsCalledThenReturnACourse() {
        when(courseRepository.findByName("Java")).thenReturn(Optional.of(course));

        CourseDTO researchedCourse = courseService.findByName("Java");

        assertAll(
                () -> assertThat(researchedCourse.getId(), is(equalTo(course.getId()))),
                () -> assertThat(researchedCourse.getName(), is(equalTo(course.getName()))),
                () -> assertThat(researchedCourse.getCategory(), is(equalTo(course.getCategory())))
        );
    }

    @Test
    @DisplayName("(6) Must throw a ResourceNotFoundException exception when looking for a non-existent course by name")
    void whenFindByNameIsCalledThenThrowAnException() {
        when(courseRepository.findByName("Delphi")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> courseService.findByName("Delphi"));
    }
}
