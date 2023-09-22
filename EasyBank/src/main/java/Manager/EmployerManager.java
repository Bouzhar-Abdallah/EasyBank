package Manager;

import Implementations.EmployerDAO;

public class EmployerManager {
    private EmployerDAO employerDAO;
    public EmployerManager(){
        employerDAO = new EmployerDAO();
    }
}
