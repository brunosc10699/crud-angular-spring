package br.com.bruno.coursesapi.services;

import br.com.bruno.coursesapi.dto.CourseDTO;
import br.com.bruno.coursesapi.entities.Course;
import br.com.bruno.coursesapi.repositories.CourseRepository;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CourseServiceImplTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseServiceImpl courseService;

    @Test
    @DisplayName("(1) Should return a page of Course elements")
    void whenFindAllIsCalledThenReturnAPageOfCourses() {
        Course course = Course.builder()
                .id(1L)
                .name("Java")
                .category("Backend Development")
                .build();
        Page<Course> page = new PageImpl<Course>(Collections.singletonList(course));
        PageRequest pageRequest = PageRequest.of(0, 1);

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
}
