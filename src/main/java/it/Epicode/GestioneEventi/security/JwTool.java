package it.Epicode.GestioneEventi.security;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import it.Epicode.GestioneEventi.exception.NonTrovatoException;
import it.Epicode.GestioneEventi.model.Utente;
import it.Epicode.GestioneEventi.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwTool {

    @Value("${jwt.duration}")
    private long durata;
    @Value("${jwt.secret}")
    private String chiaveSegreta;

    @Autowired
    private UtenteService utenteService;

    public String createToken(Utente utente){
        return    Jwts.builder().issuedAt(new Date()).expiration(new Date(System.currentTimeMillis()+ durata)).
                subject(utente.getId()+"").
                signWith(Keys.hmacShaKeyFor(chiaveSegreta.getBytes())).compact() ;






    }
    public void validateToken(String token){
        Jwts.parser().verifyWith(Keys.hmacShaKeyFor(chiaveSegreta.getBytes())).
                build().parse(token);
    }

    public Utente getUserFromToken(String token) throws NonTrovatoException {
        //Recuperare id utente dal token
        int id=  Integer.parseInt( Jwts.parser().verifyWith(Keys.hmacShaKeyFor(chiaveSegreta.getBytes())).build()
                .parseSignedClaims(token).getPayload().getSubject());

        return utenteService.getUtente(id);


    }


}
