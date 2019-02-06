package br.com.devdojo.awesome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication //=={
//@EnableAutoConfiguration //spring boot sabe o que configurar com base nas dependencias
//@ComponentScan //escaneia componentes
//@Configuration //configurações em XML 
//}
public class ApplicationStart { //classe de inicio da aplicação
    
    public static void main(String[] args) {
        
        SpringApplication.run(ApplicationStart.class,args); //roda a aplicação     
    } 
}
