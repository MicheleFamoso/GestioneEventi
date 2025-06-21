package it.Epicode.GestioneEventi.repository;

import it.Epicode.GestioneEventi.model.Utente;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtenteRepository extends JpaRepository<Utente,Integer> {
    public Optional<Utente> findByUsername(String username);

}
