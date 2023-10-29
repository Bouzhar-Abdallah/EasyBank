package Objects;

import Enums.Etat_enum;
import annotations.CustomField;
import annotations.Id;
import annotations.ManyToOne;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public abstract class Compte {
    public Compte(Long numero, Double solde, LocalDate dateCreation, Etat_enum etat) {
        this.numero = numero;
        this.solde = solde;
        this.dateCreation = dateCreation;
        this.etat = etat;
    }

    @CustomField
    @Id
    private Long numero;
    @CustomField
    private Double solde;
    @CustomField
    private LocalDate dateCreation;
    @CustomField
    private Etat_enum etat;
    @ManyToOne(targetEntity = Client.class)
    private Client client;
    @ManyToOne(targetEntity = Employer.class)
    private Employer emplyer;
    @ManyToOne(targetEntity = Agence.class)
    private Agence egence;
    public Long getNumero() {
        return numero;
    }

    public Agence getEgence() {
        return egence;
    }

    public void setEgence(Agence egence) {
        this.egence = egence;
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
