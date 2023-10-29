package Objects;

import annotations.CustomField;
import annotations.Id;
import annotations.OneToMany;

import java.time.LocalDate;
import java.util.List;

public class Client extends Person{
    public Client(String nom, String prenom, LocalDate dateNaissance, String numeroTel, String adresse, String adresseEmail, Integer code) {
        super(nom, prenom, dateNaissance, numeroTel, adresse, adresseEmail);
        this.code = code;
    }

    @CustomField
    @Id
    private Integer code;

    @OneToMany(targetEntity = Compte.class, mappedBy = "client")
    private List<Compte> comptes;
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

    public List<Compte> getComptes() {
        return comptes;
    }

    public void setComptes(List<Compte> comptes) {
        this.comptes = comptes;
    }
}
