package it.Epicode.GestioneEventi.service;


import it.Epicode.GestioneEventi.dto.LoginDto;
import it.Epicode.GestioneEventi.exception.NonTrovatoException;
import it.Epicode.GestioneEventi.model.Utente;
import it.Epicode.GestioneEventi.repository.UtenteRepository;
import it.Epicode.GestioneEventi.security.JwTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {


    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private JwTool jwTool;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public String login(LoginDto loginDto) throws NonTrovatoException {
        Utente utente = utenteRepository.findByUsername(loginDto.getUsername()).
                orElseThrow(()->new NonTrovatoException("Utente non trovato"));
        if (passwordEncoder.matches(loginDto.getPassword(),utente.getPassword())){
            return jwTool.createToken(utente);
        }else {throw new NonTrovatoException("Utente non trovato");}
    }
}
