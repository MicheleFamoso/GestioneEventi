package it.Epicode.GestioneEventi.service;


import it.Epicode.GestioneEventi.dto.UtenteDto;
import it.Epicode.GestioneEventi.enumeration.Role;
import it.Epicode.GestioneEventi.exception.NonTrovatoException;
import it.Epicode.GestioneEventi.model.Evento;
import it.Epicode.GestioneEventi.model.Utente;
import it.Epicode.GestioneEventi.repository.UtenteRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public Utente saveUtente(UtenteDto utenteDto){
        Utente utente = new Utente();
        utente.setNome(utenteDto.getNome());
        utente.setCognome(utenteDto.getCognome());
        utente.setUsername(utenteDto.getUsername());
        utente.setPassword(passwordEncoder.encode(utenteDto.getPassword()));
        utente.setRole(Role.UTENTE_SEMPLICE);
        return utenteRepository.save(utente);
    }

    public List<Utente> getAllUtenti(){

        return utenteRepository.findAll();
    }

    public Utente getUtente(int id) throws NonTrovatoException {
        return utenteRepository.findById(id).
                orElseThrow(()-> new NonTrovatoException("Utente con id" + id + "non trovato"));
    }

    public Utente updateUtente(int id, UtenteDto utenteDto) throws NonTrovatoException {
        Utente utente = getUtente(id);
        utente.setNome(utenteDto.getNome());
        utente.setCognome(utenteDto.getCognome());
        utente.setUsername(utenteDto.getUsername());
        if (!passwordEncoder.matches(utenteDto.getPassword(), utente.getPassword() )){
            utente.setPassword(passwordEncoder.encode(utenteDto.getPassword()));
        }
        return utenteRepository.save(utente);
    }

    public void deleteUtente(int id) throws NonTrovatoException {
        Utente utente = getUtente(id);
        utenteRepository.delete(utente);

    }

    public List<Evento> getEventiPrenotati(int idUtente) throws NonTrovatoException {
        Utente utente = getUtente(idUtente);
        return utente.getEventiPrenotati();
    }

    public List<Evento> getEventiCreati(int IdUtente) throws NonTrovatoException {
        Utente utente = getUtente(IdUtente);
        return utente.getEventiCreati();

    }

    public Utente getByUsername(String username) throws NonTrovatoException {
        return utenteRepository.findByUsername(username)
                .orElseThrow(() -> new NonTrovatoException("Utente con username '" + username + "' non trovato"));
    }

}
