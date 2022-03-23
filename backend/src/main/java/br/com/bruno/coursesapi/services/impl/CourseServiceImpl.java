package br.com.bruno.coursesapi.services.impl;

import br.com.bruno.coursesapi.dto.CourseDTO;
import br.com.bruno.coursesapi.entities.Course;
import br.com.bruno.coursesapi.repositories.CourseRepository;
import br.com.bruno.coursesapi.services.CourseService;
import br.com.bruno.coursesapi.services.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    @Override
    public Page<CourseDTO> findAll(Pageable pageable) {
        return courseRepository.findAll(pageable).map(this::toDTO);
    }

    @Override
    public CourseDTO findById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found by id: " + id));
        return this.toDTO(course);
    }

    @Override
    public CourseDTO findByName(String name) {
        Course course = courseRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found by name: " + name));
        return this.toDTO(course);
    }

    private CourseDTO toDTO(Course course) {
        return CourseDTO.builder()
                .id(course.getId())
                .name(course.getName())
                .category(course.getCategory())
                .build();
    }
}
