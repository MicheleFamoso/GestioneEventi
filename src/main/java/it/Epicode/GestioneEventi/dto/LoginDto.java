package it.Epicode.GestioneEventi.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginDto {
    @NotEmpty(message = "Username non puo essere vuoto")
    private String username;
    @NotEmpty(message = "Password non puo essere vuoto")
    private String password;

}
