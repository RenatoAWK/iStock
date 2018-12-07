package bsi.mpoo.istock.services;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import bsi.mpoo.istock.data.ClientDAO;
import bsi.mpoo.istock.data.Contract;
import bsi.mpoo.istock.domain.Client;
import bsi.mpoo.istock.domain.User;
import bsi.mpoo.istock.services.Exceptions.ClientAlreadyRegistered;
import bsi.mpoo.istock.services.Exceptions.ClientNotRegistered;
public class ClientServices {

    private ClientDAO clientDAO;

    public ClientServices(Context context){
        this.clientDAO = new ClientDAO(context);
    }

    private boolean isClientRegistered(String name, long idAdm){
        Client searchedClient = clientDAO.getClientByName(name, idAdm);

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

    public void updateClient(Client client, long idAdm) throws Exception{

        if (isClientRegistered(client.getName().trim().toUpperCase(), idAdm)){
            clientDAO.updateClient(client);
        }
        else {
            throw  new ClientNotRegistered();
        }


    }

    public void disableClient(Client client, long idAdm) throws Exception{

        if (isClientRegistered(client.getName().trim().toUpperCase(), idAdm)){
            clientDAO.disableClient(client);
        }
        else {
            throw  new ClientNotRegistered();
        }


    }

    public ArrayList<Client> getAcitiveClientsAsc(User user){
        return (ArrayList<Client>) clientDAO.getActivieClientsByAdmId(user.getId(),Contract.ASC);

    }

    public ArrayList<Client> getAcitiveClientsDesc(User user){
        return (ArrayList<Client>) clientDAO.getActivieClientsByAdmId(user.getId(),Contract.DESC);

    }
}
