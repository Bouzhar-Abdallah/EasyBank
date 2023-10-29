package Objects;

import annotations.CustomField;
import annotations.Id;

import java.util.List;

public class Mission {
    public Mission(Integer code, String nom, String description) {
        this.code = code;
        this.nom = nom;
        this.description = description;
    }

    @Id
    @CustomField
    private Integer code;
    @CustomField
    private String nom;
    @CustomField
    private String description;
    private List<Affectation> affectationList;

    public void setCode(Integer code) {
        this.code = code;
    }

    public List<Affectation> getAffectationList() {
        return affectationList;
    }

    public void setAffectationList(List<Affectation> affectationList) {
        this.affectationList = affectationList;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
