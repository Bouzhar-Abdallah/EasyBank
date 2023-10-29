package Objects;

import java.time.LocalDate;
import java.util.List;

public class Client extends Person{
    public Client(String nom, String prenom, LocalDate dateNaissance, String numeroTel, String adresse, String adresseEmail, Integer code) {
        super(nom, prenom, dateNaissance, numeroTel, adresse, adresseEmail);
        this.code = code;
    }

    private Integer code;

    private List<Compte> compte;
    private List<Demande> demandes;

    public List<Demande> getDemandes() {
        return demandes;
    }

    public void setDemandes(List<Demande> demandes) {
        this.demandes = demandes;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public List<Compte> getCompte() {
        return compte;
    }

    public void setCompte(List<Compte> compte) {
        this.compte = compte;
    }
}
