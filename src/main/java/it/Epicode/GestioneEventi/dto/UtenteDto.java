package it.Epicode.GestioneEventi.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UtenteDto {

    @NotEmpty(message = "Il campo nome non puo essere vuoto ")
    private String nome;
    @NotEmpty(message = "Il campo cognome non puo essere vuoto ")
    private String cognome;
    @NotEmpty(message = "Il campo username non puo essere vuoto ")
    private String username;
    @NotEmpty(message = "Il campo password non puo essere vuoto ")
    private String password;




}
