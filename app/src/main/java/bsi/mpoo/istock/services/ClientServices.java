package bsi.mpoo.istock.services;

import android.content.Context;

import java.util.ArrayList;

import bsi.mpoo.istock.data.client.ClientDAO;
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

    private boolean isClientRegistered(Client client){

        Client searchedClient = clientDAO.getClientByName(client);

        if (searchedClient == null){
            return false;
        }
        return true;
    }

    public void registerClient(Client client) throws Exception {

        if (isClientRegistered(client)){
            throw new ClientAlreadyRegistered();
        }
        else {
            clientDAO.insertClient(client);
        }

    }

    public void updateClient(Client client) throws Exception{
            try {
                clientDAO.updateClient(client);
            }catch (Exception error){
                throw new ClientNotRegistered();
            }




    }

    public void disableClient(Client client) throws Exception{

        if (isClientRegistered(client)){
            clientDAO.disableClient(client);
        }
        else {
            throw  new ClientNotRegistered();
        }


    }

    public ArrayList<Client> getAcitiveClientsAsc(User user){
        return (ArrayList<Client>) clientDAO.getActiveClientsByAdmId(user,Contract.ASC);

    }

    public ArrayList<Client> getAcitiveClientsDesc(User user){
        return (ArrayList<Client>) clientDAO.getActiveClientsByAdmId(user,Contract.DESC);

    }
}
