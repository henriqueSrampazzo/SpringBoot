package br.com.devdojo.awesome.repository;

import br.com.devdojo.awesome.model.Student;
import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface StudentRepository extends PagingAndSortingRepository<Student, Long>{
    
    List<Student> findByNameIgnoreCaseContaining(String name);
}
