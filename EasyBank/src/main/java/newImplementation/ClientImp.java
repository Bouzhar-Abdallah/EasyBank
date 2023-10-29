package newImplementation;

import Objects.Client;
import utils.DBConnection;

import java.util.List;
import java.util.Optional;

public class ClientImp extends DataImplimentation<Client,Integer>{
    public ClientImp() {
        connection = DBConnection.getDBConnection();
    }

    public Optional<Client> update(Client client) {
        return super.update(client);
    }
    public Optional<Client> findByID(Integer id, Class<Client> clientClass) {
        return super.findByID(id, clientClass);
    }
    public List<Client> getAll(Class<Client> clientClass) {
        return super.getAll(clientClass);
    }
    public boolean delete(Integer id, Class<Client> clientClass) {
        return super.delete(id, clientClass);
    }
}
