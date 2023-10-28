package com.whistleblowermanagerbe.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "chat_asincrona")
@Data
public class ChatAsincrona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne()
    @JoinColumn(name = "fk_segnalazione")
    private Segnalazione segnalazione;
    @OneToMany
    @JoinColumn(name = "fk_chat_asincrona")
    private List<Messaggio> messaggi;

}
