package Objects;

import annotations.CustomField;
import annotations.Id;
import annotations.OneToMany;

import java.util.List;

public class Agence {
    public Agence(int code, String nom, String numeroTel, String adresse) {
        this.code = code;
        this.nom = nom;
        this.numeroTel = numeroTel;
        this.adresse = adresse;
    }

    @CustomField
    @Id
    private Integer code;
    @CustomField
    private String nom;
    @CustomField
    private String numeroTel;
    @CustomField
    private String adresse;
    @OneToMany(targetEntity = Compte.class, mappedBy = "agence")
    private List<Compte> comptes;
    private List<AffectationAgence> affectationAgences;

    public List<AffectationAgence> getAffectationAgences() {
        return affectationAgences;
    }

    public void setAffectationAgences(List<AffectationAgence> affectationAgences) {
        this.affectationAgences = affectationAgences;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNumeroTel() {
        return numeroTel;
    }

    public void setNumeroTel(String numeroTel) {
        this.numeroTel = numeroTel;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public List<Compte> getComptes() {
        return comptes;
    }

    public void setComptes(List<Compte> comptes) {
        this.comptes = comptes;
    }

}
