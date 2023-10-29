package Objects;

import annotations.CustomField;

import javax.management.ObjectInstance;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public abstract class Person {
    public Person(String nom, String prenom, LocalDate dateNaissance, String numeroTel, String adresse, String adressemail) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.numeroTel = numeroTel;
        this.adresse = adresse;
        this.adressemail = adressemail;
    }

    @CustomField
    protected String nom;
    @CustomField
    protected String prenom;
    @CustomField
    protected LocalDate dateNaissance;
    @CustomField
    protected String numeroTel;
    @CustomField
    protected String adresse;
    @CustomField
    protected String adressemail;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
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

    public String getAdresseEmail() {
        return adressemail;
    }

    public void setAdresseEmail(String adresseEmail) {
        this.adressemail = adresseEmail;
    }


}
