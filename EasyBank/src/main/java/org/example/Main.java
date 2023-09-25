package org.example;


import Manager.ClientManager;
import Manager.EmployerManager;
import Manager.EpargneManager;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        // Press Opt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.


        EmployerManager empManager = new EmployerManager();
        ClientManager cltManager = new ClientManager();
        EpargneManager epargneManager = new EpargneManager();
        //empManager.addEmployee();
        //empManager.deleteEmployee();
        //empManager.showAll();
        //empManager.updateEmployee();
        //empManager.searchByAttribute();
        //cltManager.create();
        //cltManager.delete();
        //cltManager.showAll();
        //cltManager.update();
        //cltManager.searchByAttribute();
        epargneManager.create();
    }
}