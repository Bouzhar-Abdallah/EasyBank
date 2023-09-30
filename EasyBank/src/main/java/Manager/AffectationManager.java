package Manager;

import Objects.Affectation;
import Objects.Employer;
import Objects.Mission;
import Objects.Person;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class AffectationManager extends Manager {
    private Scanner sc = new Scanner(System.in);

    public Employer pickEmployer() {
        List<Person> employerList = employerDAO.getAll();
        System.out.println("Choisir un matricule (ou 0 pour annuler) : ");
        for (Person person : employerList) {
            if (person instanceof Employer) {
                Employer employer = (Employer) person;
                System.out.println("M: " + employer.getMatricule() + " - " + employer.getNom());
            }
        }
        long matricule = sc.nextLong();

        if (matricule == 0) {
            System.out.println("Sélection annulée.");
            return null;
        }

        for (Person person : employerList) {
            if (person instanceof Employer) {
                Employer employer = (Employer) person;
                if (employer.getMatricule() == matricule) {
                    System.out.println("Matricule trouvé: M: " + employer.getMatricule() + " - " + employer.getNom());
                    return employer;
                }
            }
        }

        System.out.println("Matricule non trouvé.");
        return null;
    }

    public void create() {
        System.out.println("Créer une affectation");
        Employer employer = null;
        Mission mission = null;
        while (employer == null) {
            employer = pickEmployer();
        }
        while (mission == null) {
            mission = pickMission();
        }
        Affectation affectation = new Affectation();
        affectation.setEmployer(employer);
        affectation.setMission(mission);
        affectation.setDateDebut(LocalDate.now());
        Optional<Affectation> createdAffec = affectationDAO.create(affectation);
        if (createdAffec.isPresent()){
            System.out.println("affected");
        }else{
            System.out.println("error happened");
        }
    }
    public Mission pickMission() {
        List<Mission> missionList = missionDAO.getAllMissions();

        System.out.println("Liste des missions : ");
        for (Mission mission : missionList) {
            System.out.println("Code mission: " + mission.getCode() + " - " + mission.getDescription());
        }

        System.out.println("Choisir un code mission (ou 0 pour annuler) : ");

        int codeMission = sc.nextInt();

        if (codeMission == 0) {
            System.out.println("Sélection annulée.");
            return null;
        }

        for (Mission mission : missionList) {
            if (mission.getCode() == codeMission) {
                System.out.println("Code mission trouvé: " + mission.getCode() + " - " + mission.getDescription());
                return mission;
            }
        }

        System.out.println("Code mission non trouvé.");
        return null;
    }



}
