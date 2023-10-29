package Objects;


import annotations.CustomField;
import annotations.Id;
import annotations.OneToMany;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class Employer extends Person{
    public Employer(String nom,
                    String prenom,
                    LocalDate dateNaissance,
                    String numeroTel,
                    String adresse,
                    String adressemail,
                    Integer matricule,
                    LocalDate dateRecrutement) {
        super(nom, prenom, dateNaissance, numeroTel, adresse, adressemail);
        this.matricule = matricule;
        this.dateRecrutement = dateRecrutement;
    }

    @CustomField
    @Id
    private Integer matricule;

    @CustomField
    private LocalDate dateRecrutement;
    private List<Affectation> affectations;
    private List<AffectationAgence> affectationAgences;

    public List<AffectationAgence> getAffectationAgences() {
        return affectationAgences;
    }
    @OneToMany(targetEntity = Compte.class, mappedBy = "employer")
    protected List<Compte> comptes;

    public List<Compte> getComptes() {
        return comptes;
    }

    public void setComptes(List<Compte> comptes) {
        this.comptes = comptes;
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
