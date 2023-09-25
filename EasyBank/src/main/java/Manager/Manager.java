package Manager;

import Implementations.ClientDAO;
import Implementations.CompteDAO;
import Implementations.EmployerDAO;
import Implementations.EpargneDAO;

public abstract class Manager {
    protected EmployerDAO employerDAO = new EmployerDAO();
    protected ClientDAO clientDAO = new ClientDAO();
    protected CompteDAO compteDAO = new CompteDAO();
    protected EpargneDAO epargneDAO = new EpargneDAO();
}
