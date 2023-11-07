package com.whistleblowermanagerbe.dto;

import lombok.Data;

import java.util.List;

@Data
public class ChatAsincronaDto {

    private Integer id;
    private Integer infoSegnalazione;
    private List<MessaggioDto> messaggi;
}
