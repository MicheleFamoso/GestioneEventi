package it.Epicode.GestioneEventi.controller;

import it.Epicode.GestioneEventi.dto.EventoDto;
import it.Epicode.GestioneEventi.exception.NonTrovatoException;
import it.Epicode.GestioneEventi.model.Evento;
import it.Epicode.GestioneEventi.model.Utente;
import it.Epicode.GestioneEventi.service.EventoService;
import it.Epicode.GestioneEventi.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/eventi")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @Autowired
    private UtenteService utenteService;


    @GetMapping
    public ResponseEntity<List<Evento>> getEventiDisponibili() {
        return ResponseEntity.ok(eventoService.getEventiDisponibili());
    }


    @PostMapping
    @PreAuthorize("hasAuthority('ORGANIZZATORE')")
    public ResponseEntity<Evento> creaEvento(@RequestBody EventoDto eventoDto, Principal principal) throws NonTrovatoException {
        Utente organizzatore = utenteService.getByUsername(principal.getName());
        Evento evento = eventoService.saveEvento(eventoDto, organizzatore);
        return ResponseEntity.ok(evento);
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ORGANIZZATORE')")
    public ResponseEntity<Evento> modificaEvento(@PathVariable int id, @RequestBody EventoDto eventoDto, Principal principal) throws NonTrovatoException {
        Utente organizzatore = utenteService.getByUsername(principal.getName());
        Evento eventoAggiornato = eventoService.updateEventoByOrganizzatore(id, eventoDto, organizzatore);
        return ResponseEntity.ok(eventoAggiornato);
    }

    // DELETE /eventi/{id}: solo per organizzatori
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ORGANIZZATORE')")
    public ResponseEntity<Void> eliminaEvento(@PathVariable int id, Principal principal) throws NonTrovatoException {
        Utente organizzatore = utenteService.getByUsername(principal.getName());
        eventoService.deleteEventoByOrganizzatore(id, organizzatore);
        return ResponseEntity.noContent().build();
    }

    // POST /eventi/{id}/prenota: solo per utenti semplici
    @PostMapping("/{id}/prenota")
    @PreAuthorize("hasAuthority('UTENTE_SEMPLICE')")
    public ResponseEntity<String> prenotaEvento(@PathVariable int id, Principal principal) throws NonTrovatoException {
        Utente utente = utenteService.getByUsername(principal.getName());
        eventoService.partecipaEvento(id, utente.getId());
        return ResponseEntity.ok("Prenotazione avvenuta con successo.");
    }
}
