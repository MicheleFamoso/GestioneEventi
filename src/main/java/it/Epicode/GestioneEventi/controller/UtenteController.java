package it.Epicode.GestioneEventi.controller;

import it.Epicode.GestioneEventi.exception.NonTrovatoException;
import it.Epicode.GestioneEventi.model.Evento;
import it.Epicode.GestioneEventi.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/utenti")
public class UtenteController {

    @Autowired
    private UtenteService utenteService;

    @GetMapping("/{id}/eventi-prenotati")
    @PreAuthorize("hasAuthority('UTENTE_SEMPLICE')")
    public ResponseEntity<List<Evento>> eventiPrenotati(@PathVariable int id) throws NonTrovatoException {
        return ResponseEntity.ok(utenteService.getEventiPrenotati(id));
    }

    @GetMapping("/{id}/eventi-creati")
    @PreAuthorize("hasAuthority('ORGANIZZATORE')")
    public ResponseEntity<List<Evento>> eventiCreati(@PathVariable int id) throws NonTrovatoException {
        return ResponseEntity.ok(utenteService.getEventiCreati(id));
    }
}
