package Manager;

import Enums.Etat_enum;
import Objects.*;

import java.util.Optional;
import java.util.Scanner;

public class EpargneManager extends Manager {


    public void create() {
        Scanner sc = new Scanner(System.in);
        System.out.println("create epargne");
        System.out.println("1: nouveu client");
        System.out.println("2: client deja existant");
        int choix = sc.nextInt();
        sc.nextLine();

        Optional<Person> employer = employerDAO.searchByMatricule(17);
        Optional<Person> client;
        ClientManager clientManager = new ClientManager();

        /*if (choix == 1){
            //nouveu client
            client = clientManager.create();
        }else{
            // client deja existant
            System.out.println("entrez le code du client");
            int code = sc.nextInt();
            sc.nextLine();
            client = clientDAO.searchByClientCode(code);
        }*/
        client = clientDAO.searchByClientCode(1);
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
    /*public void test(){
        Long num = 1000000000000019L;

        Optional<Compte> createdEpargne = epargneDAO.searchByNumero(num);
        if (createdEpargne.isPresent()) {

            System.out.println(
                    createdEpargne.get().getClient().getNom()
            );
            System.out.println(
                    createdEpargne.get().getEmplyer().getNom()
            );
            System.out.println(createdEpargne.get().getNumero());
        }
    }*/
}
