package Manager;

import Implementations.EmployerDAO;
import Objects.Employer;
import Objects.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class EmployerManager {
    private EmployerDAO employerDAO;

    public EmployerManager() {
        employerDAO = new EmployerDAO();
    }

    public void addEmployee() {
        try {
            Scanner sc = new Scanner(System.in);
            Employer emp = new Employer();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            System.out.print("nom:");
            emp.setPrenom(sc.nextLine());
            System.out.print("prenom:");
            emp.setNom(sc.nextLine());

            while (true) {
                System.out.print("Date de naissance(aaaa-mm-jj):");
                String tmp_date = sc.nextLine();

                try {
                    LocalDate inputDate = LocalDate.parse(tmp_date, formatter);
                    if (Period.between(inputDate, LocalDate.now()).getYears() >= 18) {
                        emp.setDateNaissance(inputDate);
                        break; // Exit the loop if date is valid
                    } else {
                        System.out.println("*****   INVALIDE DATE DE NAISSANCE POUR L'EMPLOYEE   *****");
                    }
                } catch (java.time.format.DateTimeParseException e) {
                    System.out.println("Format de date invalide. Veuillez utiliser le format aaaa-mm-jj.");
                }
            }

            System.out.print("telephone:");
            emp.setNumeroTel(sc.nextLine());
            System.out.print("adresse:");
            emp.setAdresse(sc.nextLine());
            System.out.print("adresseEmail:");
            emp.setAdresseEmail(sc.nextLine());

            while (true) {
                System.out.print("date de recrutement(aaaa-mm-jj):");
                String tmp_date = sc.nextLine();

                try {
                    LocalDate inputDate = LocalDate.parse(tmp_date, formatter);
                    if (Period.between(inputDate, LocalDate.now()).getYears() > 0)
                        emp.setDateRecrutement(inputDate);
                    else
                        throw new Exception("*****   INVALIDE DATE DE RECRUTEMENT POUR L'EMPLOYEE   *****");
                    break;
                } catch (java.time.format.DateTimeParseException e) {
                    System.out.println("Format de date invalide. Veuillez utiliser le format aaaa-mm-jj.");
                }
            }
            //employerDAO.create(emp);
            /*Employer test = new Employer();
            employerDAO.create(test);*/
            Optional<Person> optionalEmp = employerDAO.create(emp);
            if (optionalEmp.isPresent()) {
                Employer createdPerson = (Employer) optionalEmp.get();
                System.out.println(String.format("*****   AJOUT D'UN EMPLOI AVEC ID[%s] nom :[%s]  *****", createdPerson.getMatricule(), createdPerson.getNom()));
            } else {
                System.out.println("*****   Opération d'ajout d'emploi a échoué   *****");
            }
            sc.close();
        } catch (Exception e) {
            System.out.println(e.getClass() + "::" + e.getMessage());
        }
    }

    public void deleteEmployee() {
        Scanner sc = new Scanner(System.in);

        System.out.println("enter employe matricule to delete");
        Integer matriculeTodelete = sc.nextInt();
        sc.nextLine();
        Optional<Person> employer = employerDAO.search(matriculeTodelete);
        if (employer.isPresent()) {
            Employer employerToDelete = (Employer) employer.get();
            //Employer createdPerson = (Employer) employerToDelete.getId();
            System.out.println(String.format("*****   found employee ID[%d] nom :[%s]  *****", employerToDelete.getMatricule(), employerToDelete.getNom()));
            System.out.println("Press [y] to confirm delete \n");
            System.out.println("Press [any] to cancel delete \n");
            if (sc.nextLine().equals("y")) {

                if (employerDAO.delete(employerToDelete.getId()) == 0) {
                    System.out.println("deletion failed");
                } else {
                    System.out.println("employee deleted succesefully");
                }
            } else {
                System.out.flush();
                deleteEmployee();
            }
        } else {
            System.out.println("*****   employe not found   *****");
            deleteEmployee();
        }
    }

    public void showAllEmployees() {
        System.out.println("All Employees:");

        List<Person> employees = employerDAO.getAll();

        for (Person person : employees) {
            if (person instanceof Employer) {
                Employer employee = (Employer) person;

                System.out.println("Employee Matricule: " + employee.getMatricule());
                System.out.println("Nom: " + employee.getNom());
                System.out.println("Prenom: " + employee.getPrenom());
                System.out.println("Date de Naissance: " + employee.getDateNaissance());
                System.out.println("Numero de Telephone: " + employee.getNumeroTel());
                System.out.println("Adresse: " + employee.getAdresse());
                System.out.println("Adresse Email: " + employee.getAdresseEmail());
                System.out.println("Date de Recrutement: " + employee.getDateRecrutement());

                System.out.println("------------------------------");
            }
        }
    }

    public void updateEmployee() {
        System.out.println("update employee\n");
        Scanner sc = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        System.out.println("enter employe matricule to update");
        Integer matricule = sc.nextInt();
        sc.nextLine();
        Optional<Person> optionalEmployer = employerDAO.search(matricule);
        if (optionalEmployer.isPresent()) {
            Employer employerToUpdate = (Employer) optionalEmployer.get();
            System.out.println(String.format("*****   found employee | Matricule :[%d] | nom :[%s] |  *****", employerToUpdate.getMatricule(), employerToUpdate.getNom()));
            /*start from here*/
            /* nom */
            System.out.println("nom :"+employerToUpdate.getNom());
            System.out.println("update it ?");
            System.out.println("Press [y] to confirm \n");
            System.out.println("Press [any] to go next \n");
            if (sc.nextLine().equals("y")) {
                System.out.println("entrez le neuvou nom :");
                employerToUpdate.setNom(sc.nextLine());
            }
            /* prenom */
            System.out.println("prenom :"+employerToUpdate.getPrenom());
            System.out.println("update it ?");
            System.out.println("Press [y] to confirm \n");
            System.out.println("Press [any] to go next \n");
            if (sc.nextLine().equals("y")) {
                System.out.println("entrez le neuvou prenom :");
                employerToUpdate.setPrenom(sc.nextLine());
            }
            /* numeroTel*/
            System.out.println("numero de tel :"+employerToUpdate.getNumeroTel());
            System.out.println("update it ?");
            System.out.println("Press [y] to confirm \n");
            System.out.println("Press [any] to go next \n");
            if (sc.nextLine().equals("y")) {
                System.out.println("entrez le neuvou numeroTel :");
                employerToUpdate.setNumeroTel(sc.nextLine());
            }
            /* adresse*/
            System.out.println("adresse :"+employerToUpdate.getAdresse());
            System.out.println("update it ?");
            System.out.println("Press [y] to confirm \n");
            System.out.println("Press [any] to go next \n");
            if (sc.nextLine().equals("y")) {
                System.out.println("entrez le neuvou adresse :");
                employerToUpdate.setAdresse(sc.nextLine());
            }
            /* adressmail*/
            System.out.println("adresse Email :"+employerToUpdate.getAdresseEmail());
            System.out.println("update it ?");
            System.out.println("Press [y] to confirm \n");
            System.out.println("Press [any] to go next \n");
            if (sc.nextLine().equals("y")) {
                System.out.println("entrez le neuvou adresse Email :");
                employerToUpdate.setAdresseEmail(sc.nextLine());
            }
            /* datenaissance */

            System.out.println("Date de naissance :"+employerToUpdate.getDateNaissance());
            System.out.println("update it ?");
            System.out.println("Press [y] to confirm \n");
            System.out.println("Press [any] to go next \n");
            if (sc.nextLine().equals("y")) {
                System.out.println("entrez la nouvelle date de naissance :");
                while (true) {
                    System.out.print("date de naissance(aaaa-mm-jj):");
                    String tmp_date = sc.nextLine();

                    try {
                        LocalDate inputDate = LocalDate.parse(tmp_date, formatter);
                        if (Period.between(inputDate, LocalDate.now()).getYears() >= 18) {
                            employerToUpdate.setDateNaissance(inputDate);
                            break; // Exit the loop if date is valid
                        } else {
                            System.out.println("*****   INVALIDE DATE DE NAISSANCE POUR L'EMPLOYEE   *****");
                        }
                    } catch (java.time.format.DateTimeParseException e) {
                        System.out.println("Format de date invalide. Veuillez utiliser le format aaaa-mm-jj.");
                    }
                }
            }
            /* daterecrutement*/
            System.out.println("Date de recrutement :"+employerToUpdate.getDateRecrutement());
            System.out.println("update it ?");
            System.out.println("Press [y] to confirm \n");
            System.out.println("Press [any] to go next \n");
            if (sc.nextLine().equals("y")) {
                System.out.println("entrez la nouvelle date de naissance :");
                while (true) {
                    System.out.print("date de recrutement(aaaa-mm-jj):");
                    String tmp_date = sc.nextLine();

                    try {
                        LocalDate inputDate = LocalDate.parse(tmp_date, formatter);
                        if (Period.between(inputDate, LocalDate.now()).getYears() > 0)
                            employerToUpdate.setDateRecrutement(inputDate);
                        else
                            System.out.println("date de recrutement incorrecte");
                        break;
                    } catch (java.time.format.DateTimeParseException e) {
                        System.out.println("Format de date invalide. Veuillez utiliser le format aaaa-mm-jj.");
                    }
                }
            }

            Optional<Person> updatedEmp = employerDAO.update(employerToUpdate);
            if (updatedEmp.isPresent()){
                System.out.println("l'operation est effectué avec succes");
                return;
            }else{
                System.out.println("l'operation a echoué");
                return;
            }

        } else {
            System.out.println("*****   employe not found   *****");
            updateEmployee();
        }
    }

    public void test() {
        Optional<Person> emp = employerDAO.search(16);
        if (emp.isPresent()) {
            Person person = emp.get();

        }

    }

}
