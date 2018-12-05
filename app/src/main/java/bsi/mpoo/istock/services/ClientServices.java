package bsi.mpoo.istock.services;

import android.content.Context;

import bsi.mpoo.istock.data.AddressDAO;
import bsi.mpoo.istock.data.ClientDAO;
import bsi.mpoo.istock.domain.Address;
import bsi.mpoo.istock.domain.Client;
import bsi.mpoo.istock.services.Exceptions.ClientAlreadyRegistered;
public class ClientServices {

    private ClientDAO clientDAO;
    private AddressDAO addressDAO;

    public ClientServices(Context context){
        this.clientDAO = new ClientDAO(context);
        this.addressDAO = new AddressDAO(context);
    }

    private boolean isClientRegistered(String name, long idAdm){
        Client searchedClient = clientDAO.getClientName(name, idAdm);

        if (searchedClient == null){
            return false;
        }
        return true;
    }

    public void registerClient(Client client, long idAdm) throws Exception {

        if (isClientRegistered(client.getName().trim().toUpperCase(), idAdm)){
            throw new ClientAlreadyRegistered();
        }
        else {
            clientDAO.insertClient(client);
        }

    }
}
