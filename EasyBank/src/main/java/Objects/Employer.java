package Objects;

import java.util.Date;
import java.util.List;

public class Employer extends Person{
    private String matricule;

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public Date getDateRecrutement() {
        return dateRecrutement;
    }

    public void setDateRecrutement(Date dateRecrutement) {
        this.dateRecrutement = dateRecrutement;
    }

    public List<Affectation> getAffectations() {
        return affectations;
    }

    public void setAffectations(List<Affectation> affectations) {
        this.affectations = affectations;
    }

    private Date dateRecrutement;
    private List<Affectation> affectations;
}
