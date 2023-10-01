package Manager;

import Enums.Etat_enum;
import Implementations.EpargneDAO;
import Objects.*;
import org.example.Main;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CompteManager extends Manager {

    private Scanner sc = new Scanner(System.in);

    public void create() {
        Scanner sc = new Scanner(System.in);
        ClientManager clientManager = new ClientManager();

        System.out.println("1: nouveu client");
        System.out.println("2: client deja existant");
        int choix = sc.nextInt();
        sc.nextLine();

        Optional<Person> client;

        if (choix == 1) {
            //nouveu client
            client = clientManager.create();
        } else {
            // client deja existant
            System.out.println("entrez le code du client");
            int code = sc.nextInt();
            sc.nextLine();
            client = clientDAO.searchByClientCode(code);
        }
        System.out.println("choisire type compte :");
        System.out.println("1 : epargne");
        System.out.println("2 : courant");
        int choix2 = sc.nextInt();
        sc.nextLine();

        if (choix2 == 1) {
            createEpargne(client);
        } else {
            createCourant(client);
        }

    }

    public void createEpargne(Optional<Person> client) {
        Employer executingEmployee = Main.getExecutingEmployee();

        //client = clientDAO.searchByClientCode(1);
        Epargne epargneCompte = new Epargne();
        client.ifPresent(person -> epargneCompte.setClient((Client) person));
        epargneCompte.setEmplyer(executingEmployee);

        epargneCompte.setEtat(Etat_enum.actif);
        epargneCompte.setTauxInteret(0.5);
        Optional<Compte> createdEpargne = epargneDAO.create(epargneCompte);
        if (createdEpargne.isPresent()) {

            System.out.println("compte epargne crée avec success. Numero : " + createdEpargne.get().getNumero());
            System.out.println(
                    "pour le client" + createdEpargne.get().getClient().getNom() + " " + createdEpargne.get().getClient().getPrenom()
            );

        }
        //NEXTVAL('account_number_seq')
        System.out.println("\n \n hello");
    }

    public void createCourant(Optional<Person> client) {
        Employer executingEmployee = Main.getExecutingEmployee();
        //client = clientDAO.searchByClientCode(1);

        Courant courantcompte = new Courant();
        client.ifPresent(person -> courantcompte.setClient((Client) person));
        courantcompte.setEmplyer(executingEmployee);
        //employer.ifPresent(person -> courantcompte.setEmplyer((Employer) person));
        courantcompte.setEtat(Etat_enum.actif);
        courantcompte.setDecouvert(5000.00);
        Optional<Compte> createdCourant = courantDAO.create(courantcompte);
        if (createdCourant.isPresent()) {

            System.out.println("compte courant crée avec success. Numero : " + createdCourant.get().getNumero());
            System.out.println(
                    "pour le client" + createdCourant.get().getClient().getNom() + " " + createdCourant.get().getClient().getPrenom()
            );
        }
        //NEXTVAL('account_number_seq')
        System.out.println("\n \n courant compte est cree");
    }

    /* doing */
    public void delete() {
        Scanner sc = new Scanner(System.in);

        System.out.println("enter le numero de compte a supprimer");
        long numero = sc.nextLong();
        sc.nextLine();
        Optional<Compte> compteEp = epargneDAO.searchByNumero(numero);
        Optional<Compte> compteCo = courantDAO.searchByNumero(numero);
        if (compteEp.isEmpty() && compteCo.isEmpty()) {
            System.out.println("aucun compte n'est trouvé");
            delete();
        } else {
            if (compteEp.isPresent()) {
                Epargne epargneToDElete = (Epargne) compteEp.get();

                System.out.printf("*****   compte epargne trouvé num[%d]   *****%n", epargneToDElete.getNumero());
                System.out.println("Press [y] to confirm delete \n");
                System.out.println("Press [any] to cancel delete \n");
                if (sc.nextLine().equals("y")) {

                    if (epargneDAO.delete(epargneToDElete.getNumero()) == 0) {
                        System.out.println("deletion failed");
                    } else {
                        System.out.println("account deleted succesefully");
                    }
                } else {
                    System.out.flush();
                    delete();
                }
            } else {
                Courant courantToDElete = (Courant) compteCo.get();

                System.out.printf("*****   compte courant trouvé num[%d]   *****%n", courantToDElete.getNumero());
                System.out.println("Press [y] to confirm delete \n");
                System.out.println("Press [any] to cancel delete \n");
                if (sc.nextLine().equals("y")) {

                    if (epargneDAO.delete(courantToDElete.getNumero()) == 0) {
                        System.out.println("deletion failed");
                    } else {
                        System.out.println("account deleted succesefully");
                    }
                } else {
                    System.out.flush();
                    delete();
                }
            }
        }

    }

    public void showAll() {
        List<Compte> comptes = new ArrayList<>();
        comptes.addAll(epargneDAO.getAll("epargne"));
        comptes.addAll(epargneDAO.getAll("courant"));
        Map<Compte, String> compteMap = new HashMap<>();

        System.out.println("__________________");
        for (Compte compte : comptes) {
            if (compte instanceof Epargne) {
                compteMap.put(compte, "epargne");
            } else if (compte instanceof Courant) {
                compteMap.put(compte, "courant");
            }
        }
        for (Map.Entry<Compte, String> entry : compteMap.entrySet()) {
            Compte compte = entry.getKey();
            String type = entry.getValue();

            System.out.println("Type: " + type);
            if ("epargne".equals(type)) {
                System.out.println("numero de compte: " + ((Epargne) compte).getNumero());
                System.out.println("solde: " + ((Epargne) compte).getSolde());
                System.out.println("client: " + ((Epargne) compte).getClient().getNom());
            } else if ("courant".equals(type)) {
                System.out.println("numero de compte: " + ((Courant) compte).getNumero());
                System.out.println("solde: " + ((Courant) compte).getSolde());
                System.out.println("client: " + ((Courant) compte).getClient().getNom());
            }
            System.out.println("\n__________________");
        }
    }

    public void updateAccountStatus() {
        System.out.println("update compte status\n");

        System.out.println("enter account number to update");
        long numeroCompte = sc.nextLong();

        sc.nextLine();
        Optional<Compte> compte = compteDAO.findByNumero(numeroCompte);
        if (compte.isPresent()) {

            System.out.println("pick new status");
            for (Etat_enum status : Etat_enum.values()
            ) {
                System.out.println(status.ordinal() + " :" + status.name());
            }
            int choix = sc.nextInt();
            switch (choix) {
                case 0 -> compte.get().setEtat(Etat_enum.valueOf("actif"));
                case 1 -> compte.get().setEtat(Etat_enum.valueOf("suspendu"));
                case 2 -> compte.get().setEtat(Etat_enum.valueOf("saisi"));
            }
            if (compteDAO.updateStatus(compte.get()) > 0) {
                System.out.println("updated succesefully");
            }
        } else {
            System.out.println("ce numero de compte n'existe pas");
        }
        //compte.ifPresent(value -> System.out.println(value.getEtat()));
    }

    public void updateAccount() {
        System.out.println("update compte\n");

        System.out.println("enter account number to update");
        long numeroCompte = sc.nextLong();

        sc.nextLine();
        Optional<Compte> compte = compteDAO.findByNumero(numeroCompte);
        if (compte.isPresent()) {
            System.out.println("compte trouvé");
            System.out.println("numero :" + compte.get().getNumero());
            System.out.println("choisir une operation");
            System.out.println("1: update numero");
            System.out.println("2: update solde");
            System.out.println("3: update client");
            System.out.println("4: update employer");
            if (compte.get() instanceof Epargne) System.out.println("5: update taux d'interet");
            else System.out.println("5: update decouvert");

            int choix = sc.nextInt();
            switch (choix) {
                case 1 -> updateNumero(compte.get());
                case 2 -> updateSolde(compte.get());
                case 3 -> updateClient(compte.get());
                case 4 -> updateEmployer(compte.get());
                case 5 -> {
                    if (compte.get() instanceof Epargne) updateTauxInteret(compte.get());
                    else updateDecouvert(compte.get());
                }
            }
            /*if (compteDAO.updateStatus(compte.get())>0){
                System.out.println("updated succesefully");
            }*/
        } else {
            System.out.println("ce numero de compte n'existe pas");
        }

    }

    public void updateSolde(Compte compte) {
        System.out.println("untrez le nouveu solde");
        compte.setSolde(sc.nextDouble());
        if (compteDAO.updateSolde(compte) > 0) {
            System.out.println("solde updated succesfully");
        } else {

            System.out.println("une erreur est servenu");
        }
    }

    public void updateNumero(Compte compte) {
        System.out.println("untrez le nouveu numero");
        compte.setNumero(sc.nextLong());
        if (compteDAO.updateNumero(compte) > 0) {
            System.out.println("solde updated succesfully");
        } else {
            System.out.println("une erreur est servenu");
        }
    }

    public void updateClient(Compte compte) {
        System.out.println("untrez le code du nouveu client");
        /*todo later*/

    }

    public void updateEmployer(Compte compte) {
        System.out.println("untrez le matricule du nouveu employer");
        /*todo later*/
    }

    public void updateDecouvert(Compte compte) {
        System.out.println("untrez le nouveu decouvert");
        Courant crt = (Courant) compte;
        System.out.println("decouvert : " + crt.getDecouvert());
        crt.setDecouvert(sc.nextDouble());
        if (compteDAO.updateDecouvert(crt) > 0) {
            System.out.println("decouvert updated succesfully");
        } else {
            System.out.println("une erreur est servenu");
        }
    }

    public void updateTauxInteret(Compte compte) {
        System.out.println("untrez le nouveu taux d'interet");
        Epargne epargne = (Epargne) compte;
        System.out.println("taux d'interet : " + epargne.getTauxInteret());
        epargne.setTauxInteret(sc.nextDouble());
        if (compteDAO.updateTauxInteret(epargne) > 0) {
            System.out.println("decouvert updated succesfully");
        } else {
            System.out.println("une erreur est servenu");
        }
    }

    public void searchByClient() {
        System.out.println("enter Client code serach with");
        Integer code = sc.nextInt();
        sc.nextLine();
        Optional<Person> optionalClient = clientDAO.searchByClientCode(code);
        List<Compte> comptes = compteDAO.findByClient((Client) optionalClient.get());
        /*for (Compte compte: comptes
             ) {
            System.out.println(compte.getNumero());
        }*/
    }

    public void searchAccountByStatus() {
        System.out.println("select status");
        for (Etat_enum etat : Etat_enum.values()
        ) {
            System.out.println(etat.ordinal() + " : " + etat.name());
        }
        int choix = sc.nextInt();
        String etat = "actif";
        switch (choix) {
            case 0 -> etat = "actif";
            case 1 -> etat = "suspendu";
            case 2 -> etat = "saisi";
        }
        List<Compte> comptes = compteDAO.findByStatus(etat);
        for (Compte compte : comptes
        ) {
            System.out.println(compte.getNumero());
        }
    }

    public void searchByCreationDate() {
        List<Compte> comptes = new ArrayList<>();
        System.out.println("creationDate");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        boolean validDateEntered = false;

        while (!validDateEntered) {
            System.out.print("entrez la date sous la forme (aaaa-mm-jj):");
            String tmp_date = sc.nextLine();

            try {
                LocalDate inputDate = LocalDate.parse(tmp_date, formatter);
                comptes = compteDAO.findByDateCreation(inputDate);
                for (Compte compte : comptes
                ) {
                    System.out.println(compte.getNumero());
                }
                validDateEntered = true;
            } catch (java.time.format.DateTimeParseException e) {
                System.out.println("Format de date invalide. Veuillez utiliser le format aaaa-mm-jj.");
            }
        }
    }

}