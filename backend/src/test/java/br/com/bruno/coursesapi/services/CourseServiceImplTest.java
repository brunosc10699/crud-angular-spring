package br.com.bruno.coursesapi.services;

import br.com.bruno.coursesapi.dto.CourseDTO;
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

    private final Page<Course> page = new PageImpl<>(Collections.singletonList(course));

    private final PageRequest pageRequest = PageRequest.of(0, 1);

    @Test
    @DisplayName("(1) Should return a page of Course elements")
    void whenFindAllIsCalledThenReturnAPageOfCourses() {
        when(courseRepository.findAll(pageRequest)).thenReturn(page);

        Page<CourseDTO> pageDTO = courseService.findAll(pageRequest);
        assertAll(
                () -> assertThat(pageDTO.getTotalPages(), is(equalTo(1))),
                () -> assertThat(pageDTO.getSize(), is(equalTo(1))),
                () -> assertThat(pageDTO.getContent().get(0).getId(), is(equalTo(1L))),
                () -> assertThat(pageDTO.getContent().get(0).getName(), is(equalTo("Java"))),
                () -> assertThat(pageDTO.getContent().get(0).getCategory(), is(equalTo("Backend Development")))
        );
    }

    @Test
    @DisplayName("(2) Should return an empty elements page")
    void whenFindAllIsCalledThenReturnAnEmptyPage() {
        when(courseRepository.findAll(pageRequest)).thenReturn(Page.empty());

        Page<CourseDTO> page = courseService.findAll(pageRequest);

        assertThat(page.getContent(), is(empty()));
    }

    @Test
    @DisplayName("(3) Should return a course DTO object")
    void whenFindByIdIsCalledThenReturnADTOObject() {
        when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));

        CourseDTO courseDTO = courseService.findById(1L);

        assertAll(
                () -> assertThat(courseDTO.getId(), is(equalTo(course.getId()))),
                () -> assertThat(courseDTO.getName(), is(equalTo(course.getName()))),
                () -> assertThat(courseDTO.getCategory(), is(equalTo(course.getCategory())))
        );
    }

    @Test
    @DisplayName("(4) Should throw a ResourceNotFoundException exception when looking for a non-existent course")
    void whenFindByIdIsCalledThenThrowAException() {
        when(courseRepository.findById(course.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> courseService.findById(course.getId()));
    }
}
