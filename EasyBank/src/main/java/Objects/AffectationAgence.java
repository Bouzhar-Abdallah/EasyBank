package Objects;

import annotations.CustomField;
import annotations.ManyToOne;

import java.time.LocalDate;

public class AffectationAgence {
    public AffectationAgence(LocalDate dateDebut, LocalDate dateFin, Agence agence, Employer employer) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.agence = agence;
        this.employer = employer;
    }

    @CustomField
    private LocalDate dateDebut;
    @CustomField
    private LocalDate dateFin;
    @ManyToOne(targetEntity = Agence.class)
    private Agence agence;

    @ManyToOne(targetEntity = Employer.class)
    private Employer employer;

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public Agence getAgence() {
        return agence;
    }

    public void setAgence(Agence agence) {
        this.agence = agence;
    }

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }
}
