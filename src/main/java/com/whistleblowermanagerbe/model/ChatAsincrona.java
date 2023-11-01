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
    @JoinColumn(name = "fk_info_segnalazione")
    private InfoSegnalazione infoSegnalazione;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_chat_asincrona")
    private List<Messaggio> messaggi;

}
