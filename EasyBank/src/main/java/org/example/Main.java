package org.example;



import Objects.*;
import newImplementation.AgenceImp;
import newImplementation.ClientImp;
import newImplementation.EmployerImp;
import newService.AgenceService;
import newService.ClientService;
import newService.EmployerService;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Scanner;


// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
/*    private static void handleEmployeeMenu() {
        EmployerManager empManager = new EmployerManager();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            // Affichez le menu des employés
            System.out.println("Menu Employés:");
            System.out.println("1. Ajouter un employé");
            System.out.println("2. Supprimer un employé");
            System.out.println("3. Rechercher un employé");
            System.out.println("4. Mettre à jour un employé");
            System.out.println("0. Retour au menu principal");
            System.out.print("Votre choix : ");
            choice = scanner.nextInt();
            switch (choice) {
                case 1 -> empManager.create();
                case 2 -> empManager.delete();
                case 3 -> empManager.searchByAttribute();
                case 4 -> empManager.update();
                case 0 -> System.out.println("Retour au menu principal.");
                default -> System.out.println("Choix invalide. Réessayez.");
            }
        } while (choice != 0);
    }

    private static void handleClientMenu() {
        ClientManager cltManager = new ClientManager();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            // Affichez le menu des clients
            System.out.println("Menu Clients:");
            System.out.println("1. Ajouter un client");
            System.out.println("2. Supprimer un client");
            System.out.println("3. Rechercher des clients par attributs");
            System.out.println("4. List des client");
            System.out.println("5. Mettre à jour un client");
            *//*System.out.println("6. Obtenir les comptes d'un client");*//*
            System.out.println("0. Retour au menu principal");
            System.out.print("Votre choix : ");


            choice = scanner.nextInt();

            switch (choice) {
                case 1 -> cltManager.create();
                case 2 -> cltManager.delete();
                case 3 -> cltManager.searchByAttribute();
                case 4 -> cltManager.showAll();
                case 5 -> cltManager.update();

                *//*case 6:
                    cltManager.getClientAccount();
                    break;*//*
                case 0 -> System.out.println("Retour au menu principal.");
                default -> System.out.println("Choix invalide. Réessayez.");
            }
        } while (choice != 0);
    }

    private static void handleAccountMenu() {
        CompteManager compteManager = new CompteManager();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("Menu Comptes:");
            System.out.println("1. Ajouter un compte");
            System.out.println("2. Supprimer un compte");
            System.out.println("3. Mettre à jour le statuts d'un compte");
            System.out.println("4. Obtenir la liste des comptes");
            System.out.println("5. Mettre à jour un compte");
            System.out.println("6. recherche des comptes par statut");
            System.out.println("7. recherche des comptes par client");
            System.out.println("8. recherche des comptes par date de création");
            System.out.println("0. Retour au menu principal");
            System.out.print("Votre choix : ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> compteManager.create();
                case 2 -> compteManager.delete();
                case 3 -> compteManager.updateAccountStatus();
                case 4 -> compteManager.showAll();
                case 5 -> compteManager.updateAccount();
                case 6 -> compteManager.searchAccountByStatus();
                case 7 -> compteManager.searchByClient();
                case 8 -> compteManager.searchByCreationDate();
                case 0 -> System.out.println("Retour au menu principal.");
                default -> System.out.println("Choix invalide. Réessayez.");
            }
        } while (choice != 0);
    }

    private static void handleOperationMenu() {
        OperationManager operationManager = new OperationManager();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("Menu Operation:");
            System.out.println("1. Ajouter une operation");
            System.out.println("2. Supprimer une operation");
            //System.out.println("3. Rechercher une operation par nombre");
            System.out.println("0. Retour au menu principal");
            System.out.print("Votre choix : ");

            choice = scanner.nextInt();
            switch (choice) {
                case 1 -> operationManager.create();
                case 2 -> operationManager.delete();
                case 0 -> System.out.println("Retour au menu principal.");
                default -> System.out.println("Choix invalide. Réessayez.");
            }
        } while (choice != 0);

    }

    private static void handleMissionMenu() {
        MissionManager missionManager = new MissionManager();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("Menu Operation:");
            System.out.println("1. Ajouter une mission");
            System.out.println("2. Supprimer une mission");
            System.out.println("3. Afficher tous les missions");
            System.out.println("0. Retour au menu principal");
            System.out.print("Votre choix : ");
            choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> missionManager.create();
                case 2 -> missionManager.delete();
                case 3 -> missionManager.showMissions();
                case 0 -> System.out.println("Retour au menu principal.");
                default -> System.out.println("Choix invalide. Réessayez.");
            }
        } while (choice != 0);
    }

    private static void handleAffectationMenu() {
        AffectationManager affectationManager = new AffectationManager();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            // Affichez le menu des clients
            System.out.println("Menu Operation:");
            System.out.println("1. Ajouter une affectation");
            System.out.println("2. Supprimer une affectation");
            System.out.println("3. Afficher tous les missions");
            System.out.println("4. Statistiques");
            System.out.println("0. Retour au menu principal");
            System.out.print("Votre choix : ");

            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> affectationManager.create();
                case 2 -> affectationManager.deleteAffectation();
                case 3 -> affectationManager.showEmployerAffectations();
                case 4 -> affectationManager.showAffectationsStats();
                case 0 -> System.out.println("Retour au menu principal.");
                default -> System.out.println("Choix invalide. Réessayez.");
            }
        } while (choice != 0);
    }
    public static Employer executingEmployee;
    public static Employer getExecutingEmployee() {
        return executingEmployee;
    }

    private static void logIn(){
        //Employer executingEmployee = Main.getExecutingEmployee();
        Scanner sc = new Scanner(System.in);
        System.out.println("entrez votre matricule");
        int matricule = sc.nextInt();
        sc.nextLine();
        Optional<Person> emp = new EmployerDAO().searchByMatricule(matricule);
        emp.ifPresent(person -> executingEmployee = (Employer) person);
        if (emp.isEmpty()){
            System.out.println("matricule non trouvé");
        }
    }*/
    public static void createEmp(){
        EmployerService employerService = new EmployerService(new EmployerImp());
        Employer employer = new Employer(
                "orm nom",
                "orm prenom",
                LocalDate.now(),
                "orm numero tell",
                "orm adresse",
                "orm email",
                2331,
                LocalDate.now()
        );
        employerService.create(employer);
    }
    public static void createClient(){
        ClientService clientService = new ClientService(new ClientImp());
        Client client = new Client(
                "orm nom",
                "orm prenom",
                LocalDate.now(),
                "orm numero tell",
                "orm adresse",
                "orm email",
                334598
        );
        clientService.create(client);
    }
    public static void createAgence(){
        AgenceService agenceService = new AgenceService(new AgenceImp());
        Agence agence = new Agence(
                7683,
                "nom",
                "ejhbhjebjh",
                "adresse"
        );
        agenceService.create(agence);
    }
    public static void main(String[] args) {
        createClient();
        /*
        Scanner sc = new Scanner(System.in);
        do {
            logIn();
        }while(executingEmployee == null);
        int choice;
        do {
            // Affichez le menu
            System.out.println("Menu:");
            System.out.println("1. Gérer les employés");
            System.out.println("2. Gérer les clients");
            System.out.println("3. Gérer les comptes");
            System.out.println("4. Gérer les opérations");
            System.out.println("5. Gérer les missions");
            System.out.println("6. Gérer les affectations");
            System.out.println("0. Quitter");
            System.out.print("Votre choix : ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> handleEmployeeMenu();
                case 2 -> handleClientMenu();
                case 3 -> handleAccountMenu();
                case 4 -> handleOperationMenu();
                case 5 -> handleMissionMenu();
                case 6 -> handleAffectationMenu();
                case 0 -> System.out.println("Au revoir !");
                default -> System.out.println("Choix invalide. Réessayez.");
            }
        } while (choice != 0);

        sc.close();
*/


    }
}