package com.whistleblowermanagerbe.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Lob;

@Data
public class SoggettoCoinvoltoDto {

    private String ragioneSocialeCognome;
    private String nome;
    private String ente;
    private String qualifica;   //picklist
    private String ruoloCoinvolgimento;
    private String telefono;
    private String email;
    private String beneficioAccaduto;   //picklist
    private String note;
}
