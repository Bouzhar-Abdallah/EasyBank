package Objects;

import java.util.List;

public class Agence {
    private int code;
    private String nom;
    private String numeroTel;
    private String adresse;
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
