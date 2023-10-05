package Objects;

import Enums.Etat_enum;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public abstract class Compte {
    private Long numero;
    private Double solde;
    private LocalDate dateCreation;
    private Etat_enum etat;
    private Client client;
    private Employer emplyer;
    private Agence egence;
    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public Double getSolde() {
        return solde;
    }

    public void setSolde(Double solde) {
        this.solde = solde;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Etat_enum getEtat() {
        return etat;
    }

    public void setEtat(Etat_enum etat) {
        this.etat = etat;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Employer getEmplyer() {
        return emplyer;
    }

    public void setEmplyer(Employer emplyer) {
        this.emplyer = emplyer;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }

    private List<Operation> operations;
}
