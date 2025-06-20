package it.Epicode.GestioneEventi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Evento {
    @Id
    @GeneratedValue
    private int id;
    private String titolo;
    private String descrizione;
    private LocalDate data;
    private String luogo;
    private int postiDisponibili;
    @ManyToOne
    @JoinColumn(name = "organizzatore_id")
    private Utente organizzatore;

    @ManyToMany
    @JoinTable(name = "partecipanti",
            joinColumns = @JoinColumn(name = "evento_id"),
            inverseJoinColumns = @JoinColumn(name = "utente_id"))
    private List<Utente> utentiPartecipanti;


}
