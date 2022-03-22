package br.com.bruno.coursesapi.services;

import br.com.bruno.coursesapi.dto.CourseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CourseService {

    Page<CourseDTO> findAll(Pageable pageable);
}
