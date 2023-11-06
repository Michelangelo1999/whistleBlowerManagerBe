package com.whistleblowermanagerbe.dto;

import com.whistleblowermanagerbe.model.InfoSegnalazione;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
public class FascicoloDto {

    private Integer id;
    private Integer idPrimaSegnalazioneInfo;
    private String descrizione;
    private String stato;
    private String tipologia;
    private String areaInteressata;
    private String fondatezza;
}
