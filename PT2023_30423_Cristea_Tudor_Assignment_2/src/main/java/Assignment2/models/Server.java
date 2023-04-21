package Assignment2.models;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable
{
    private BlockingQueue<Client> clients;
    private AtomicInteger waitingPeriod;

    private volatile boolean go;

    public Server(int capacity)
    {
        this.clients = new ArrayBlockingQueue<>(capacity);
        this.waitingPeriod = new AtomicInteger(0);

        go = true;
    }

    public BlockingQueue<Client> getClients()
    {
        return clients;
    }

    public void setClients(BlockingQueue<Client> clients)
    {
        this.clients = clients;
    }

    public AtomicInteger getWaitingPeriod()
    {
        return waitingPeriod;
    }

    public void setWaitingPeriod(AtomicInteger waitingPeriod)
    {
        this.waitingPeriod = waitingPeriod;
    }

    public boolean getGo()
    {
        return go;
    }

    public void setGo(boolean go)
    {
        this.go = go;
    }

    public void addClient(Client newClient)
    {
        clients.add(newClient);
        waitingPeriod.set(waitingPeriod.get() + newClient.getServiceTime());
    }

    public void run()
    {
        while(go)
        {
            if (!clients.isEmpty())
            {
                Client currentClient = clients.peek();

                try
                {
                    Thread.sleep(950);
                }
                catch (InterruptedException e)
                {
                    throw new RuntimeException(e);
                }

                if (currentClient.getServiceTime() == 0)
                {
                    clients.remove(currentClient);
                    waitingPeriod.set(waitingPeriod.get() - currentClient.getServiceTime());
                }
            }
        }
    }
}