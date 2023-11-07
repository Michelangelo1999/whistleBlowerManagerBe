package com.whistleblowermanagerbe.dto;

import com.whistleblowermanagerbe.model.Allegato;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessaggioDto {

    private Integer id;
    private Integer writer;
    private String messaggio;
    private Integer idAllegato;
    private String allegato;
}
