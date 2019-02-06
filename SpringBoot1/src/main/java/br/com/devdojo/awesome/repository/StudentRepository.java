package br.com.devdojo.awesome.repository;

import br.com.devdojo.awesome.model.Student;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, Long>{
    
    List<Student> findByNameIgnoreCaseContaining(String name);
}
