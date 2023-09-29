package Manager;

import Enums.Etat_enum;
import Enums.Type_operation_enum;
import Objects.Compte;
import Objects.Employer;
import Objects.Operation;

import java.util.Optional;
import java.util.Scanner;

public class OperationManager extends Manager {
    /*temp*/
    public OperationManager() {
        employer = (Employer) employerDAO.searchByMatricule(16).get();
    }

    private String operationType;
    private Compte compte;
    private Employer employer;
    private Scanner sc = new Scanner(System.in);

    public void create() {
        while (operationType == null) setOperationType();
        while (compte == null) setCompte();
        switch (operationType) {
            case "versement" -> versement();
            case "retrait" -> retrait();
            case "virement" -> virement();
        }
    }

    public void setOperationType() {
        System.out.println("choisissez un type d'operation");
        for (Type_operation_enum type : Type_operation_enum.values()
        ) {
            System.out.println(type.ordinal() + ": " + type.name());
        }
        int choix = sc.nextInt();
        switch (choix) {
            case 0 -> operationType = "versement";
            case 1 -> operationType = "retrait";
            case 2 -> operationType = "virement";
        }
    }

    public void setCompte() {

        System.out.println("enter account number for operation");
        long numeroCompte = sc.nextLong();

        sc.nextLine();
        Optional<Compte> compteTmp = compteDAO.findByNumero(numeroCompte);
        if (compteTmp.isPresent()) {
            compte = compteTmp.get();
        } else {
            System.out.println("ce numero de compte n'existe pas");
        }
    }

    public void versement() {
        Operation operation = new Operation();
        operation.setCompte(compte);
        operation.setEmployer(employer);
        operation.setType(Type_operation_enum.valueOf(operationType));
            System.out.println("entrez le montant x");
            float montant = sc.nextFloat();
            operation.setMontant(montant);
                operationDAO.create(operation);
    }

    public void retrait() {
        Operation operation = new Operation();
        operation.setCompte(compte);
        operation.setEmployer(employer);
        operation.setType(Type_operation_enum.valueOf(operationType));
        while (true) {
            System.out.println("entrez le montant");
            float montant = sc.nextFloat();
            operation.setMontant(montant);
            if (montant >compte.getSolde()){
                System.out.println("solde insufisant");
            }else{
                operationDAO.create(operation);
                break;
            }
        }
    }

    public void virement() {
    }
}
