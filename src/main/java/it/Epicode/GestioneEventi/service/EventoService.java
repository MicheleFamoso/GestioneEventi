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

   public Evento updateEvento(int id, EventoDto eventoDto) throws NonTrovatoException {
        Evento evento = getEvento(id);
       evento.setTitolo(eventoDto.getTitolo());
       evento.setDescrizione(eventoDto.getDescrizione());
       evento.setData(eventoDto.getData());
       evento.setLuogo(eventoDto.getLuogo());
       evento.setPostiDisponibili(eventoDto.getPostiDisponibili());
       return eventoRepository.save(evento);
   }

   public void deleteEvento(int id) throws NonTrovatoException {
        Evento evento = getEvento(id);
        eventoRepository.delete(evento);
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

}
