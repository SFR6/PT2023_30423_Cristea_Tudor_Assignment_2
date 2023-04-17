package Assignment2.models;

import java.util.List;

public interface Strategy
{
    boolean addClient(List<Server> servers, Client client);
}