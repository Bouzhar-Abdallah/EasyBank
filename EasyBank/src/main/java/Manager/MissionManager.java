package Manager;

import Objects.Mission;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class MissionManager extends Manager {
    private Mission mission;
    private Scanner sc = new Scanner(System.in);

    public void create() {
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

    public void showMissions() {
        System.out.println("missions :");
        List<Mission> missions = missionDAO.getAllMissions();

        for (Mission mission : missions
        ) {
            System.out.println(mission.getCode() + " : " + mission.getNom());
            System.out.println("   description : " + mission.getDescription());
        }
    }

    public void delete() {
        showMissions();
        System.out.println("enter code to delete :");
        while (true) {
            int code = sc.nextInt();
            sc.nextLine();
            if (missionDAO.delete(code) > 0){
                System.out.println("deleted succesefully");
                break;
            }else{
                System.out.flush();
                System.out.println("code incorrecte");
                delete();
            }

        }
    }
}
