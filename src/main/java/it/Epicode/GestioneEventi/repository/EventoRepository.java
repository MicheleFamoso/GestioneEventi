package it.Epicode.GestioneEventi.repository;

import it.Epicode.GestioneEventi.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoRepository extends JpaRepository<Evento,Integer> {
}
