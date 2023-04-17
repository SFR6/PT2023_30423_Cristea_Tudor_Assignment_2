package Assignment2.models;

import java.util.List;

public class ShortestQueueStrategy implements Strategy
{
    synchronized public boolean addClient(List<Server> servers, Client client)
    {
        int minimumSize = Integer.MAX_VALUE;
        for (Server s: servers)
        {
            int size = s.getClients().size();
            if (size < minimumSize && s.getClients().remainingCapacity() > 0)
            {
                minimumSize = size;
            }
        }
        for (Server s: servers)
        {
            if (s.getClients().size() == minimumSize && s.getClients().remainingCapacity() > 0)
            {
                s.addClient(client);
                return true;
            }
        }

        return false;
    }
}