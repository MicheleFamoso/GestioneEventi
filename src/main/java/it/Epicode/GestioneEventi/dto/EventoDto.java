package it.Epicode.GestioneEventi.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EventoDto {
    @NotEmpty(message = "Il campo Titolo non puo essere vuoto")
    private String titolo;
    @NotEmpty(message = "Il campo Descrizione non puo essere vuoto")
    private String descrizione;
    @NotEmpty(message = " Data necessaria")
    private LocalDate data;
    @NotEmpty(message = "Luogo necessario")
    private String luogo;
    @Min(value = 1,message = "Posti disponibili necessario")
    private int postiDisponibili;

}
