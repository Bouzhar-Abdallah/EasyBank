package Objects;

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

    protected String nom;
    protected String prenom;
    protected LocalDate dateNaissance;
    protected String numeroTel;
    protected String adresse;
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
