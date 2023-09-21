package Objects;

import Enums.Etat_enum;

import java.util.Date;
import java.util.List;

public class Compte {
    private Long numero;
    private Double solde;
    private Date dateCreation;
    private Etat_enum etat;
    private Client client;
    private Employer emplyer;
    private List<Operation> operations;
}
