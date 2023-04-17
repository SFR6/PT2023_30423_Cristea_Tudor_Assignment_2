package Assignment2.models;

import java.util.ArrayList;
import java.util.List;

public class Scheduler
{
    private List<Server> serverList;
    private int maximumNumberOfServers;
    private int maximumNumberOfClientsPerServer;
    private Strategy strategy;

    public Scheduler(int maximumNumberOfServers, int maximumNumberOfClientsPerServer, Strategy strategy)
    {
        this.serverList = new ArrayList<>();
        this.maximumNumberOfServers = maximumNumberOfServers;
        this.maximumNumberOfClientsPerServer = maximumNumberOfClientsPerServer;
        this.strategy = strategy;

        for (int i = 1; i <= maximumNumberOfServers; ++i)
        {
            Server server = new Server(maximumNumberOfClientsPerServer);
            serverList.add(server);
            Thread thread = new Thread(server);
            thread.start();
        }
    }

    public List<Server> getServerList()
    {
        return serverList;
    }

    public void setServerList(List<Server> serverList)
    {
        this.serverList = serverList;
    }

    public int getMaximumNumberOfServers()
    {
        return maximumNumberOfServers;
    }

    public void setMaximumNumberOfServers(int maximumNumberOfServers)
    {
        this.maximumNumberOfServers = maximumNumberOfServers;
    }

    public int getMaximumNumberOfClientsPerServer()
    {
        return maximumNumberOfClientsPerServer;
    }

    public void setMaximumNumberOfClientsPerServer(int maximumNumberOfClientsPerServer)
    {
        this.maximumNumberOfClientsPerServer = maximumNumberOfClientsPerServer;
    }

    public Strategy getStrategy()
    {
        return strategy;
    }

    public void changeStrategy(SelectionPolicy selectionPolicy)
    {
        if (selectionPolicy == SelectionPolicy.SHORTEST_QUEUE)
        {
            strategy = new ShortestQueueStrategy();
        }
        else
        {
            strategy = new ShortestTimeStrategy();
        }
    }

    synchronized public boolean dispatchClient(Client client)
    {
        return strategy.addClient(serverList, client);
    }
}