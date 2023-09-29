package Manager;

import Objects.Mission;

import java.util.Optional;
import java.util.Scanner;

public class MissionManager extends Manager{
    private Mission mission;
    private Scanner sc = new Scanner(System.in);
    public void create(){
        Mission mission = new Mission();
        System.out.println("*** new mission ***");
        System.out.println("enter name :");
        mission.setNom(sc.nextLine());
        System.out.println("description");
        mission.setDescription(sc.nextLine());
        Optional<Mission> createdMission = missionDAO.create(mission);
        if (createdMission.isPresent()) System.out.println("created succesefully");
        else System.out.println("error happened");
    }
}
