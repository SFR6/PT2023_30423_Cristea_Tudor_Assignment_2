package Assignment2.models;

import java.util.List;

public class ShortestTimeStrategy implements Strategy
{
    synchronized public boolean addClient(List<Server> servers, Client client)
    {
        int minimumTime = Integer.MAX_VALUE;
        for (Server s: servers)
        {
            int time = s.getWaitingPeriod().get();
            if (time < minimumTime && s.getClients().remainingCapacity() > 0)
            {
                minimumTime = time;
            }
        }
        for (Server s: servers)
        {
            if (s.getWaitingPeriod().get() == minimumTime && s.getClients().remainingCapacity() > 0)
            {
                s.addClient(client);
                return true;
            }
        }

        return false;
    }
}