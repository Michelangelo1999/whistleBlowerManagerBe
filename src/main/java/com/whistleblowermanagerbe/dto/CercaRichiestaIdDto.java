package com.whistleblowermanagerbe.dto;

import com.whistleblowermanagerbe.model.DatiUtente;
import com.whistleblowermanagerbe.model.RichiestaIdentita;
import lombok.Data;

@Data
public class CercaRichiestaIdDto {

    RichiestaIdentita richiesta;
    DatiUtente identita;
}
