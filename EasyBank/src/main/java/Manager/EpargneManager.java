package Manager;

import Enums.Etat_enum;
import Implementations.ClientDAO;
import Implementations.EmployerDAO;
import Implementations.EpargneDAO;
import Objects.Client;
import Objects.Employer;
import Objects.Epargne;
import Objects.Person;

import java.util.Optional;
import java.util.Scanner;

public class EpargneManager {
    private EpargneDAO epargneDAO;
    public EpargneManager(){
        epargneDAO = new EpargneDAO();
    }
    public void create(){
        Scanner sc = new Scanner(System.in);
        System.out.println("create epargne");
        System.out.println("1: nouveu client");
        System.out.println("2: client deja existant");
        int choix = sc.nextInt();
        sc.nextLine();

        EmployerDAO employerDAO = new EmployerDAO();
        ClientDAO clientDAO = new ClientDAO();
        Optional<Person> employer = employerDAO.searchByMatricule(16);
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
        epargneDAO.create(epargneCompte);
        //NEXTVAL('account_number_seq')
        System.out.println("\n \n hello");
    }
}
