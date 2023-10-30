package Objects;

import annotations.CustomField;
import annotations.ManyToMany;
import annotations.ManyToOne;

import java.time.LocalDate;
import java.util.Date;

public class Affectation {
    public Affectation(LocalDate dateDebut, LocalDate dateFin, Employer employer, Mission mission) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.employer = employer;
        this.mission = mission;
    }

    @CustomField
    private LocalDate dateDebut;
    @CustomField
    private LocalDate dateFin;
    @ManyToOne(targetEntity = Employer.class)
    private Employer employer;
    @ManyToOne(targetEntity = Mission.class)
    private Mission mission;

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

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    public Mission getMission() {
        return mission;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }
}
