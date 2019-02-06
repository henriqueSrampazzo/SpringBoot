package br.com.devdojo.awesome.model;

 import javax.persistence.Entity;

@Entity //mapeia a class para uma tabela

public class Student extends AbstractEntity{ //classe de parametros do estudante

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
  
}
