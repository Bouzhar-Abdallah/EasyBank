package org.example;


import Manager.*;
import Objects.Operation;


// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        // Press Opt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.


        EmployerManager empManager = new EmployerManager();
        ClientManager cltManager = new ClientManager();
        CompteManager compteManager = new CompteManager();
        OperationManager operationManager = new OperationManager();
        MissionManager missionManager = new MissionManager();
        AffectationManager affectationManager = new AffectationManager();
        //empManager.create();
        //empManager.deleteEmployee();
        //empManager.showAll();
        //empManager.updateEmployee();
        //empManager.searchByAttribute();
        //cltManager.create();
        //cltManager.delete();
        //cltManager.showAll();
        //cltManager.update();
        //cltManager.searchByAttribute();
        //compteManager.create();
        //compteManager.showAll();
        //compteManager.delete();

        //compteManager.updateAccountStatus();
        //compteManager.updateAccount();
        //compteManager.searchByClient();
        //compteManager.searchAccountByStatus();
        //compteManager.searchByCreationDate();
        //operationManager.create();
        //operationManager.delete();
        //missionManager.create();
        //missionManager.showMissions();
        //missionManager.delete();
        //affectationManager.create();
        //affectationManager.showEmployerAffectations();
        affectationManager.deleteAffectation();
    }
}