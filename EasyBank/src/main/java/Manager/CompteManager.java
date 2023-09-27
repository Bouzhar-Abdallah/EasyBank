package Manager;

import Enums.Etat_enum;
import Implementations.EpargneDAO;
import Objects.*;

import java.time.format.DateTimeFormatter;
import java.util.*;

public class CompteManager extends Manager {


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
        Optional<Person> employer = employerDAO.searchByMatricule(17);
        //client = clientDAO.searchByClientCode(1);
        Epargne epargneCompte = new Epargne();
        client.ifPresent(person -> epargneCompte.setClient((Client) person));
        employer.ifPresent(person -> epargneCompte.setEmplyer((Employer) person));
        epargneCompte.setEtat(Etat_enum.actif);
        epargneCompte.setTauxInteret(0.5);
        Optional<Compte> createdEpargne = epargneDAO.create(epargneCompte);
        if (createdEpargne.isPresent()) {

            System.out.println(
                    createdEpargne.get().getClient().getNom()
            );
            System.out.println(
                    createdEpargne.get().getEmplyer().getNom()
            );
            System.out.println(createdEpargne.get().getNumero());
        }
        //NEXTVAL('account_number_seq')
        System.out.println("\n \n hello");
    }

    public void createCourant(Optional<Person> client) {
        Optional<Person> employer = employerDAO.searchByMatricule(17);
        //client = clientDAO.searchByClientCode(1);

        Courant courantcompte = new Courant();
        client.ifPresent(person -> courantcompte.setClient((Client) person));
        employer.ifPresent(person -> courantcompte.setEmplyer((Employer) person));
        courantcompte.setEtat(Etat_enum.actif);
        courantcompte.setDecouvert(5000.00);
        Optional<Compte> createdCourant = courantDAO.create(courantcompte);
        if (createdCourant.isPresent()) {

            System.out.println(
                    createdCourant.get().getClient().getNom()
            );
            System.out.println(
                    createdCourant.get().getEmplyer().getNom()
            );
            System.out.println(createdCourant.get().getNumero());
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
                System.out.println("Attribute Value: " + ((Epargne) compte).getTauxInteret());
            } else if ("courant".equals(type)) {
                System.out.println("Attribute Value: " + ((Courant) compte).getDecouvert());
            }
        }


       /* for (Compte compte: comptes
             ) {
            System.out.println(compte.getDateCreation());
        }*/
        System.out.println(comptes.size());
    }

    public void updateAccountStatus() {
        System.out.println("update compte\n");
        Scanner sc = new Scanner(System.in);

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
            if (compteDAO.updateStatus(compte.get())>0){
                System.out.println("updated succesefully");
            }
        }else{
            System.out.println("ce numero de compte n'existe pas");
        }
        //compte.ifPresent(value -> System.out.println(value.getEtat()));
    }
}
