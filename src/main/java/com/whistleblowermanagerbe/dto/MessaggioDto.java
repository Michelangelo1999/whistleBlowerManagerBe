package com.whistleblowermanagerbe.dto;

import com.whistleblowermanagerbe.model.Allegato;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
public class MessaggioDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "data_ora")
    private LocalDateTime dataOra;
    private String messaggio;
    private String allegato;
}
