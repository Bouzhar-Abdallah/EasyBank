package Manager;

import Implementations.*;

import java.util.Scanner;

public abstract class Manager {
    protected EmployerDAO employerDAO = new EmployerDAO();
    protected ClientDAO clientDAO = new ClientDAO();
    protected CompteDAO compteDAO = new CompteDAO();
    protected EpargneDAO epargneDAO = new EpargneDAO();
    protected CourantDAO courantDAO = new CourantDAO();
    protected OperationDAO operationDAO = new OperationDAO();
    protected MissionDAO missionDAO = new MissionDAO();

}
