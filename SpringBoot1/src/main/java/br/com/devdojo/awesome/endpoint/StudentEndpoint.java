package br.com.devdojo.awesome.endpoint;

//import br.com.devdojo.awesome.error.ResourceNotFoundException;
import br.com.devdojo.awesome.model.Student;
import br.com.devdojo.awesome.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("students") //anotação de mapeamento /utilizar nome no request no plural

public class StudentEndpoint { //ponto final da classe Student 

    private final StudentRepository StudentDAO;

    @Autowired //faz a injeção de dependencias 

    public StudentEndpoint(StudentRepository StudentDAO) {

        this.StudentDAO = StudentDAO;
    }

    @GetMapping//get vai retornar tudo 
    public ResponseEntity<?> listAll() {

        return new ResponseEntity<>(StudentDAO.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}") //pegar
    public ResponseEntity<?> getStudentById(@PathVariable("id") Long id) {
        verifyIfStudentExists(id);
        Student student = StudentDAO.findOne(id);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @GetMapping(path = "/findByName/{name}")
    public ResponseEntity<?> findStudentsByName(@PathVariable String name) {

        return new ResponseEntity<>(StudentDAO.findByNameIgnoreCaseContaining(name), HttpStatus.OK);

    }

    @PostMapping//criar 
    public ResponseEntity<?> save(@RequestBody Student student) {
        return new ResponseEntity<>(StudentDAO.save(student), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")//deletar 
    public ResponseEntity<?> delete(@PathVariable Long id) {
        verifyIfStudentExists(id);
        StudentDAO.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping  //atualizar
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
