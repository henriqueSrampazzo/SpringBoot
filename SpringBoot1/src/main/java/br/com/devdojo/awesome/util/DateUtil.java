package br.com.devdojo.awesome.util;

import org.springframework.stereotype.Component;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

@Component //mostra ao spring que essa classe pode ser utilizada na injeção de dependencias 
//@Repository //utilizada com DAO, trata excessoes
//@Service //utilizada em classes de seriço

public class DateUtil { //classe de formatação de data
    
    public String formatLocalDataTimeToDatabaseStyle(LocalDateTime localDateTime){
        
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(localDateTime);  
    }
    
}
