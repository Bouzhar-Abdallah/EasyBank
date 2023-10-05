package Objects;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class Employer extends Person{
    private Integer matricule;

    private LocalDate dateRecrutement;
    private List<Affectation> affectations;
    private List<AffectationAgence> affectationAgences;

    public List<AffectationAgence> getAffectationAgences() {
        return affectationAgences;
    }

    public void setAffectationAgences(List<AffectationAgence> affectationAgences) {
        this.affectationAgences = affectationAgences;
    }

    public Integer getMatricule() {
        return matricule;
    }

    public void setMatricule(Integer matricule) {
        this.matricule = matricule;
    }

    public LocalDate getDateRecrutement() {
        return dateRecrutement;
    }

    public void setDateRecrutement(LocalDate dateRecrutement) {
        this.dateRecrutement = dateRecrutement;
    }

    public List<Affectation> getAffectations() {
        return affectations;
    }

    public void setAffectations(List<Affectation> affectations) {
        this.affectations = affectations;
    }
}
