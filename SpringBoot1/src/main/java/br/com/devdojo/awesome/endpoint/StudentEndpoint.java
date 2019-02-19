package br.com.devdojo.awesome.endpoint;

import br.com.devdojo.awesome.model.Student;
import br.com.devdojo.awesome.repository.StudentRepository;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("v1") //anotação de mapeamento /utilizar nome no request no plural

public class StudentEndpoint { //ponto final da classe Student 

    private final StudentRepository StudentDAO;

    @Autowired //faz a injeção de dependencias 

    public StudentEndpoint(StudentRepository StudentDAO) {

        this.StudentDAO = StudentDAO;
    }

    @GetMapping(path = "admin/students")//get vai retornar tudo 
    @ApiOperation(value = "descrição da classe", response = Student[].class)
    
    public ResponseEntity<?> listAll(Pageable pageable) {

        return new ResponseEntity<>(StudentDAO.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping(path = "protected/students/{id}") //pegar
    public ResponseEntity<?> getStudentById(@PathVariable("id") Long id, Authentication authentication) {
        verifyIfStudentExists(id);
        Student student = StudentDAO.findOne(id);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @GetMapping(path = "protected/students/findByName/{name}")
    public ResponseEntity<?> findStudentsByName(@PathVariable String name) { //verifica se é valido

        return new ResponseEntity<>(StudentDAO.findByNameIgnoreCaseContaining(name), HttpStatus.OK);

    }

    @PostMapping(path = "admin/students")//criar 
    @Transactional(rollbackFor = Exception.class) //método em transação
    public ResponseEntity<?> save(@Valid @RequestBody Student student) {
       
        return new ResponseEntity<>(StudentDAO.save(student), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "admin/students/{id}")//deletar 
    @PreAuthorize("hasRole('ADMIN')") //exige autorização de ADM 
    public ResponseEntity<?> delete(@PathVariable Long id) {
        verifyIfStudentExists(id);
        StudentDAO.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path = "admin/students")  //atualizar
    public ResponseEntity<?> update(@RequestBody Student student) {
        verifyIfStudentExists(student.getId());
        StudentDAO.save(student);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void verifyIfStudentExists(Long id) {

        Student student = StudentDAO.findOne(id);
        if (StudentDAO.findOne(id) == null) {
//            throw new ResourceNotFoundException("Student not found for ID: " + id);
        }
    }

}
