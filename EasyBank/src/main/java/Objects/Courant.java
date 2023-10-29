package Objects;

import Enums.Etat_enum;

import java.time.LocalDate;

public class Courant extends Compte{
    public Courant(Long numero, Double solde, LocalDate dateCreation, Etat_enum etat, Double decouvert) {
        super(numero, solde, dateCreation, etat);
        this.decouvert = decouvert;
    }

    private Double decouvert;

    public Double getDecouvert() {
        return decouvert;
    }

    public void setDecouvert(Double decouvert) {
        this.decouvert = decouvert;
    }
}
