for(i = 0; i < nombreDeTaches; i++){
        if(taches[i].id == idTache){
            int sort;
            //zid hadi
            int collabsCount = 0;
            do{
                printf("Veuillez saisir l'id du collaborateur : ");
                scanf("%d",&idCollaborateur);
                for(j=0; j <nombreDeCollaborateurs; j++){
                    if (collaborateurs[j].id == idCollaborateur)
                    {
                    
                        collaborations[nombreDeCollaborations].id=nombreDeCollaborations+defCollaboration;
                        //collabsCount li zdna hiya lighandiro hna
                        collaborations[nombreDeCollaborations].idCollaborateur[collabsCount]=idCollaborateur;
                        collaborations[nombreDeCollaborations].idTache=idTache;
                        nombreDeCollaborations++;
                        //finma t'ajouta chi collab tzad
                        collabsCount++;
                        break;
                    }else{
                        printf("collaborateur n'existe pas");
                    }
                }
                printf("voulez-vous ajouter un autre collaborateur\n 0----------->oui 1----------->non\n");
                scanf("%d",&sort);
            }while(sort==0);
            
        }else{
            printf("tache n'existe pas");
            break;
        }