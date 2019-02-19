package br.com.devdojo.awesome.model;

import javax.persistence.Entity;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

@Entity //mapeia a class para uma tabela

public class Student extends AbstractEntity { //classe de parametros do estudante

    @NotEmpty(message = "O campo nome é obrigatório")//validação de tamanho com mensagem personalizada
    private String name;

    @NotEmpty
    @Email(message = "Digite um email válido")
    private String email;
    
    public Student() {
   
    }
    
    public Student(String name, String email) {
        this.name = name;
        this.email = email;
    }
    
     public Student(Long id,String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Student{" + "name=" + name + ", email=" + email + '}';
    }
}
