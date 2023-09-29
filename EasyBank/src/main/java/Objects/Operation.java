package Objects;

import Enums.Type_operation_enum;

import java.time.LocalDate;
import java.util.Date;

public class Operation {
    private Long numero;
    private LocalDate dateOperation;
    private Float montant;
    private Type_operation_enum type;
    private Employer employer;
    private Compte compte;

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public LocalDate getDateOperation() {
        return dateOperation;
    }

    public void setDateOperation(LocalDate dateOperation) {
        this.dateOperation = dateOperation;
    }

    public Float getMontant() {
        return montant;
    }

    public void setMontant(Float montant) {
        this.montant = montant;
    }

    public Type_operation_enum getType() {
        return type;
    }

    public void setType(Type_operation_enum type) {
        this.type = type;
    }

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    public Compte getCompte() {
        return compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }
}
