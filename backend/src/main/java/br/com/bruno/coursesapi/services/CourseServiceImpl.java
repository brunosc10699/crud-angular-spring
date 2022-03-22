package br.com.bruno.coursesapi.services;

import br.com.bruno.coursesapi.dto.CourseDTO;
import br.com.bruno.coursesapi.repositories.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService{

    private final CourseRepository courseRepository;

    @Override
    public Page<CourseDTO> findAll(Pageable pageable) {
        return courseRepository.findAll(pageable).map(CourseDTO::toDTO);
    }
}
