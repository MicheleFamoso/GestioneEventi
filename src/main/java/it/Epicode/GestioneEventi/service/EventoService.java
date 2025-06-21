package it.Epicode.GestioneEventi.service;

import it.Epicode.GestioneEventi.dto.EventoDto;
import it.Epicode.GestioneEventi.exception.NonTrovatoException;
import it.Epicode.GestioneEventi.exception.ValidationException;
import it.Epicode.GestioneEventi.model.Evento;
import it.Epicode.GestioneEventi.model.Utente;
import it.Epicode.GestioneEventi.repository.EventoRepository;
import it.Epicode.GestioneEventi.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventoService {
    @Autowired
    private EventoRepository eventoRepository;
    @Autowired
    private UtenteRepository utenteRepository;


    public Evento saveEvento(EventoDto eventoDto, Utente organizzatore){
        Evento evento = new Evento();
        evento.setTitolo(eventoDto.getTitolo());
        evento.setDescrizione(eventoDto.getDescrizione());
        evento.setData(eventoDto.getData());
        evento.setLuogo(eventoDto.getLuogo());
        evento.setPostiDisponibili(eventoDto.getPostiDisponibili());
        evento.setOrganizzatore(organizzatore);

        return eventoRepository.save(evento);


    }

   public Evento getEvento(int id) throws NonTrovatoException {
        return eventoRepository.findById(id).
                orElseThrow(()->new NonTrovatoException("Evento con id " + id + "non trovato"));
   }




   public void partecipaEvento(int idEvento, int idUtente) throws NonTrovatoException {
        Evento evento = eventoRepository.findById(idEvento).
                orElseThrow(()-> new NonTrovatoException("evento non trovato"));

        Utente utente = utenteRepository.findById(idUtente). orElseThrow(()-> new NonTrovatoException("Utente non trovato"));

        if(evento.getPostiDisponibili()<=0){
            throw new NonTrovatoException("Posti esauriti");
        }

        if(!evento.getUtentiPartecipanti().contains(utente)){
            evento.getUtentiPartecipanti().add(utente);
            evento.setPostiDisponibili(evento.getPostiDisponibili()-1);
            utente.getEventiPrenotati().add(evento);
        }
        eventoRepository.save(evento);
        utenteRepository.save(utente);
   }

   public List<Evento> getEventiCreatiDaOrganizzatore(int idUtente) throws NonTrovatoException {
       Utente organizzatore = utenteRepository.findById(idUtente)
               .orElseThrow(() -> new NonTrovatoException("Organizzatore non trovato"));
       return eventoRepository.findByOrganizzatore(organizzatore);
   }

   public Evento updateEventoByOrganizzatore(int idEvento, EventoDto dto, Utente organizzatore) throws NonTrovatoException {
       Evento evento = getEvento(idEvento);
       if (evento.getOrganizzatore().getId() !=(organizzatore.getId())) {
           throw new RuntimeException("Non puoi modificare un evento che non hai creato.");
       }
       evento.setTitolo(dto.getTitolo());
       evento.setDescrizione(dto.getDescrizione());
       evento.setData(dto.getData());
       evento.setLuogo(dto.getLuogo());
       evento.setPostiDisponibili(dto.getPostiDisponibili());
       return eventoRepository.save(evento);
   }

   public void deleteEventoByOrganizzatore(int idEvento, Utente organizzatore) throws NonTrovatoException {
       Evento evento = getEvento(idEvento);
       if (evento.getOrganizzatore().getId() !=(organizzatore.getId())) {
           throw new RuntimeException("Non puoi eliminare un evento che non hai creato.");
       }
       eventoRepository.delete(evento);
   }
    public List<Evento> getEventiDisponibili() {
        return eventoRepository.findByPostiDisponibiliGreaterThan(0);
    }

}
