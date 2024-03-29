package Manager;

import Implementations.ClientDAO;
import Objects.Client;
import Objects.Employer;
import Objects.Person;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ClientManager extends Manager{

    public ClientManager(){
        clientDAO = new ClientDAO();
    }
    public Optional<Person> create() {
        try {
            Scanner sc = new Scanner(System.in);
            Client client = new Client();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            System.out.print("nom:");
            client.setPrenom(sc.nextLine());
            System.out.print("prenom:");
            client.setNom(sc.nextLine());

            while (true) {
                System.out.print("Date de naissance(aaaa-mm-jj):");
                String tmp_date = sc.nextLine();

                try {
                    LocalDate inputDate = LocalDate.parse(tmp_date, formatter);
                    if (Period.between(inputDate, LocalDate.now()).getYears() >= 18) {
                        client.setDateNaissance(inputDate);
                        break; // Exit the loop if date is valid
                    } else {
                        System.out.println("*****   INVALIDE DATE DE NAISSANCE POUR L'EMPLOYEE   *****");
                    }
                } catch (java.time.format.DateTimeParseException e) {
                    System.out.println("Format de date invalide. Veuillez utiliser le format aaaa-mm-jj.");
                }
            }

            System.out.print("telephone:");
            client.setNumeroTel(sc.nextLine());
            System.out.print("adresse:");
            client.setAdresse(sc.nextLine());
            System.out.print("adresseEmail:");
            client.setAdresseEmail(sc.nextLine());
            Optional<Person> optionalClient = clientDAO.create(client);
            if (optionalClient.isPresent()) {
                Client createdPerson = (Client) optionalClient.get();
                System.out.println(String.format("*****   AJOUT D'UN Client AVEC Code[%d] nom :[%s]  *****", createdPerson.getCode(), createdPerson.getNom()));
            } else {
                System.out.println("*****   Opération d'ajout d'emploi a échoué   *****");
            }

                return optionalClient ;
        } catch (Exception e) {
            System.out.println(e.getClass() + "::" + e.getMessage());
        }
        return Optional.empty();
    }

    public void delete() {
        Scanner sc = new Scanner(System.in);

        System.out.println("enter client code to delete");
        Integer codeTodelete = sc.nextInt();
        sc.nextLine();
        Optional<Person> client = clientDAO.searchByClientCode(codeTodelete);
        if (client.isPresent()) {
            Client clientToDelete = (Client) client.get();
            //Employer createdPerson = (Employer) employerToDelete.getId();
            System.out.println(String.format("*****   found client code :[%d] nom :[%s]  *****", clientToDelete.getCode(), clientToDelete.getNom()));
            System.out.println("Press [y] to confirm delete \n");
            System.out.println("Press [any] to cancel delete \n");
            if (sc.nextLine().equals("y")) {

                if (clientDAO.delete(clientToDelete.getId()) == 0) {
                    System.out.println("deletion failed");
                } else {
                    System.out.println("client deleted succesefully");
                }
            } else {
                System.out.flush();
                delete();
            }
        } else {
            System.out.println("*****   employe not found   *****");
            delete();
        }
    }

    public void showAll() {
        System.out.println("_______________");
        System.out.println("All clients:");
        System.out.println("_______________");

        List<Person> employees = clientDAO.getAll();

        for (Person person : employees) {
            if (person instanceof Employer) {
                Employer employee = (Employer) person;


                System.out.println("Nom: " + employee.getNom());
                System.out.println("Prenom: " + employee.getPrenom());
                System.out.println("Date de Naissance: " + employee.getDateNaissance());
                System.out.println("Numero de Telephone: " + employee.getNumeroTel());
                System.out.println("Adresse: " + employee.getAdresse());
                System.out.println("Adresse Email: " + employee.getAdresseEmail());

                System.out.println("------------------------------");
            }
        }
    }

    public void update() {
        System.out.println("update client\n");
        Scanner sc = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        System.out.println("enter Client code to update");
        Integer code = sc.nextInt();
        sc.nextLine();
        Optional<Person> optionalEmployer = clientDAO.searchByClientCode(code);
        if (optionalEmployer.isPresent()) {
            Client clientToUpdate = (Client) optionalEmployer.get();
            System.out.println(String.format("*****   found employee | Matricule :[%d] | nom :[%s] |  *****", clientToUpdate.getCode(), clientToUpdate.getNom()));
            /*start from here*/
            /* nom */
            System.out.println("nom :"+clientToUpdate.getNom());
            System.out.println("update it ?");
            System.out.println("Press [y] to confirm \n");
            System.out.println("Press [any] to go next \n");
            if (sc.nextLine().equals("y")) {
                System.out.println("entrez le neuvou nom :");
                clientToUpdate.setNom(sc.nextLine());
            }
            /* prenom */
            System.out.println("prenom :"+clientToUpdate.getPrenom());
            System.out.println("update it ?");
            System.out.println("Press [y] to confirm \n");
            System.out.println("Press [any] to go next \n");
            if (sc.nextLine().equals("y")) {
                System.out.println("entrez le neuvou prenom :");
                clientToUpdate.setPrenom(sc.nextLine());
            }
            /* numeroTel*/
            System.out.println("numero de tel :"+clientToUpdate.getNumeroTel());
            System.out.println("update it ?");
            System.out.println("Press [y] to confirm \n");
            System.out.println("Press [any] to go next \n");
            if (sc.nextLine().equals("y")) {
                System.out.println("entrez le neuvou numeroTel :");
                clientToUpdate.setNumeroTel(sc.nextLine());
            }
            /* adresse*/
            System.out.println("adresse :"+clientToUpdate.getAdresse());
            System.out.println("update it ?");
            System.out.println("Press [y] to confirm \n");
            System.out.println("Press [any] to go next \n");
            if (sc.nextLine().equals("y")) {
                System.out.println("entrez le neuvou adresse :");
                clientToUpdate.setAdresse(sc.nextLine());
            }
            /* adressmail*/
            System.out.println("adresse Email :"+clientToUpdate.getAdresseEmail());
            System.out.println("update it ?");
            System.out.println("Press [y] to confirm \n");
            System.out.println("Press [any] to go next \n");
            if (sc.nextLine().equals("y")) {
                System.out.println("entrez le neuvou adresse Email :");
                clientToUpdate.setAdresseEmail(sc.nextLine());
            }
            /* datenaissance */

            System.out.println("Date de naissance :"+clientToUpdate.getDateNaissance());
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
                            clientToUpdate.setDateNaissance(inputDate);
                            break; // Exit the loop if date is valid
                        } else {
                            System.out.println("*****   INVALIDE DATE DE NAISSANCE POUR L'EMPLOYEE   *****");
                        }
                    } catch (java.time.format.DateTimeParseException e) {
                        System.out.println("Format de date invalide. Veuillez utiliser le format aaaa-mm-jj.");
                    }
                }
            }
            Optional<Person> updatedEmp = clientDAO.update(clientToUpdate);
            if (updatedEmp.isPresent()){
                System.out.println("operation est effectué avec succes");
            }else{
                System.out.println("l'operation a echoué");
            }
            return;

        } else {
            System.out.println("*****   employe not found   *****");
            update();
        }
    }

    public void searchByAttribute() {

        Scanner sc = new Scanner(System.in);
        System.out.println("search by :");
        System.out.println("1 : code");
        System.out.println("2 : nom");
        System.out.println("3 : date de naissance");
        System.out.println("4 : numero de tel");
        int choix = sc.nextInt();
        Optional<Person> optionalPerson = Optional.empty();
        sc.nextLine();
        switch (choix) {
            case 1 -> {
                System.out.println("entrez le code");
                Integer code = sc.nextInt();
                sc.nextLine();
                optionalPerson = clientDAO.searchByClientCode(code);
            }
            case 2 -> {
                System.out.println("entrez le nom");
                String nom = sc.nextLine();
                optionalPerson = clientDAO.searchByNom(nom);
            }
            case 3 -> {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                System.out.println("entrez la date de naissance");
                String tmp_date = sc.nextLine();
                LocalDate inputDate = LocalDate.parse(tmp_date, formatter);
                optionalPerson = clientDAO.searchByDateNaissance(inputDate);
            }
            case 4 -> {
                System.out.println("entrez le numero de tel");
                String numeroTel = sc.nextLine();
                optionalPerson = clientDAO.searchByNumeroTel(numeroTel);
            }
            default -> {
            }
        }
        if (optionalPerson.isPresent()){
            Client client = (Client) optionalPerson.get();

            System.out.println("Code client: " + client.getCode());
            System.out.println("Nom: " + client.getNom());
            System.out.println("Prenom: " + client.getPrenom());
            System.out.println("Date de Naissance: " + client.getDateNaissance());
            System.out.println("Numero de Telephone: " + client.getNumeroTel());
            System.out.println("Adresse: " + client.getAdresse());
            System.out.println("Adresse Email: " + client.getAdresseEmail());


            System.out.println("------------------------------");

        }else{
            System.out.println("employer not found");
            System.out.flush();
            searchByAttribute();
        }
    }
}
