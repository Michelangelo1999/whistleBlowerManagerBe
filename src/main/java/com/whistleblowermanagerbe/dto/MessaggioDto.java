package com.whistleblowermanagerbe.dto;

import com.whistleblowermanagerbe.model.Allegato;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
public class MessaggioDto {

    private Integer id;
    private String messaggio;
    private String allegato;
}
