package newService;

import Objects.Client;
import newImplementation.ClientImp;

import java.util.Optional;

public class ClientService {
    ClientImp clientImp;
    public ClientService(ClientImp clientImp) {
        this.clientImp = clientImp;
    }
    public Optional<Client> create(Client client){
        return clientImp.create(client);
    }
}
