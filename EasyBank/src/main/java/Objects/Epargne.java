package Objects;


import Enums.Etat_enum;

import java.time.LocalDate;

public class Epargne extends Compte{
    public Epargne(Long numero, Double solde, LocalDate dateCreation, Etat_enum etat, Double tauxInteret) {
        super(numero, solde, dateCreation, etat);
        this.tauxInteret = tauxInteret;
    }

    private Double tauxInteret;

    public Double getTauxInteret() {
        return tauxInteret;
    }

    public void setTauxInteret(Double tauxInteret) {
        this.tauxInteret = tauxInteret;
    }
}
