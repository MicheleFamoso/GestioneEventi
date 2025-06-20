package it.Epicode.GestioneEventi.repository;

import it.Epicode.GestioneEventi.model.Evento;
import it.Epicode.GestioneEventi.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventoRepository extends JpaRepository<Evento,Integer> {

    List<Evento> findByOrganizzatore(Utente organizzatore);
}
