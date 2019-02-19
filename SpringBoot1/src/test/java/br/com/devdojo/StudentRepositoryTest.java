package br.com.devdojo;

import br.com.devdojo.awesome.model.Student;
import br.com.devdojo.awesome.repository.StudentRepository;
import java.util.List;
import javax.validation.ConstraintViolationException;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest   //teste com o banco de dados na memoria

//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) //teste com o banco de dados real
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void createShouldPersistData() {

        Student student = new Student("Henrique", "henrique@gmail.com");
        this.studentRepository.save(student);

        Assertions.assertThat(student.getId()).isNotNull();
        Assertions.assertThat(student.getName()).isEqualTo("Henrique");
        Assertions.assertThat(student.getEmail()).isEqualTo("henrique@gmail.com");
    }

    @Test
    public void deleteShouldRemoveData() {

        Student student = new Student("Henrique", "henrique@gmail.com");
        this.studentRepository.save(student);
        studentRepository.delete(student);

        Assertions.assertThat(studentRepository.findOne(student.getId())).isNull();
    }

    @Test
    public void updateShouldChangeAndPersistData() {

        Student student = new Student("Henrique", "henrique@gmail.com");
        this.studentRepository.save(student);
        student.setName("Henrique2");
        student.setEmail("henrique2@gmail.com");
        student = this.studentRepository.save(student);

        this.studentRepository.findOne(student.getId());

        Assertions.assertThat(student.getName()).isEqualTo("Henrique2");
        Assertions.assertThat(student.getEmail()).isEqualTo("henrique2@gmail.com");
    }

    @Test
    public void findByNameIgnoreCaseContainingShouldIgnoreCase() {

        Student student = new Student("Henrique", "henrique@gmail.com");
        Student student2 = new Student("henrique", "henrique@gmail.com");
        this.studentRepository.save(student);
        this.studentRepository.save(student2);

        List<Student> studentList = studentRepository.findByNameIgnoreCaseContaining("henrique");

        Assertions.assertThat(studentList.size()).isEqualTo(2);
        Assertions.assertThat(student.getEmail()).isEqualTo("henrique2@gmail.com");
    }

    @Test
    public void createWhenNameIsNullShouldThrowConstraintViolationException() {

        thrown.expect(ConstraintViolationException.class);
        thrown.expectMessage("O campo nome do estudante é obrigatório");

        this.studentRepository.save(new Student());
    }

    @Test
    public void createWhenEmailIsNullShouldThrowConstraintViolationException() {

        thrown.expect(ConstraintViolationException.class);
        Student student = new Student();
        student.setName("Henrique");
        this.studentRepository.save(student);
    }

    @Test
    public void createWhenEmailIsNotValidShouldThrowConstraintViolationException() {

        thrown.expect(ConstraintViolationException.class);
        thrown.expectMessage("Digite um email válido");
        Student student = new Student();
        student.setName("Henrique");
        student.setEmail("henrique");
        this.studentRepository.save(student);
    }

}
