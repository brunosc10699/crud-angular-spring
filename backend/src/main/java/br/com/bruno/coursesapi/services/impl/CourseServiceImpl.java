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
        return courseRepository.findAll(pageable).map(CourseDTO::toDTO);
    }

    @Override
    public CourseDTO findById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found by id: " + id));
        return CourseDTO.toDTO(course);
    }
}
