package Assignment2.controllers;

import Assignment2.models.*;
import Assignment2.views.ResultsView;
import Assignment2.views.SimulationView;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class SimulationManager implements Runnable
{
    public SelectionPolicy selectionPolicy;

    private int numberOfClients;
    private int numberOfQueues;
    private int queueSize;
    private int simulationTime;
    private int minimumArrivalTime;
    private int maximumArrivalTime;
    private int minimumServiceTime;
    private int maximumServiceTime;

    private Scheduler scheduler;
    private SimulationView simulationView;
    private WindowListener windowListener;
    private List<Client> generatedClientsList;

    private volatile boolean interrupted;

    public SimulationManager(SimulationView simulationView, int numberOfClients, int numberOfQueues, int queueSize, int simulationTime, int minimumArrivalTime, int maximumArrivalTime, int minimumServiceTime, int maximumServiceTime, SelectionPolicy selectionPolicy)
    {
        this.simulationView = simulationView;

        this.numberOfClients = numberOfClients;
        this.numberOfQueues = numberOfQueues;
        this.queueSize = queueSize;
        this.simulationTime = simulationTime;
        this.minimumArrivalTime = minimumArrivalTime;
        this.maximumArrivalTime = maximumArrivalTime;
        this.minimumServiceTime = minimumServiceTime;
        this.maximumServiceTime = maximumServiceTime;
        this.selectionPolicy = selectionPolicy;

        windowListener = new WindowListener()
        {
            @Override
            public void windowOpened(WindowEvent e) {}

            @Override
            public void windowClosing(WindowEvent e)
            {
                simulationView.setEnabled(true);
            }

            @Override
            public void windowClosed(WindowEvent e) {}

            @Override
            public void windowIconified(WindowEvent e) {}

            @Override
            public void windowDeiconified(WindowEvent e) {}

            @Override
            public void windowActivated(WindowEvent e) {}

            @Override
            public void windowDeactivated(WindowEvent e) {}
        };

        this.generatedClientsList = new ArrayList<>();

        Strategy strategy;
        if (selectionPolicy == SelectionPolicy.SHORTEST_QUEUE)
        {
            strategy = new ShortestQueueStrategy();
        }
        else
        {
            strategy = new ShortestTimeStrategy();
        }
        this.scheduler = new Scheduler(this.numberOfQueues, queueSize, strategy);
        generateRandomClients();
    }

    private void generateRandomClients()
    {
        int i;
        for (i = 1; i <= numberOfClients; ++i)
        {
            int arrivalTime = (int)(Math.random() * (maximumArrivalTime - minimumArrivalTime + 1) + minimumArrivalTime);
            int serviceTime = (int)(Math.random() * (maximumServiceTime - minimumServiceTime + 1) + minimumServiceTime);

            Client client = new Client(i, arrivalTime, serviceTime);
            generatedClientsList.add(client);
        }
        generatedClientsList.sort(new Comparator<Client>()
        {
            @Override
            public int compare(Client o1, Client o2)
            {
                return o1.getArrivalTime() - o2.getArrivalTime();
            }
        });
    }

    public boolean getInterrupted()
    {
        return interrupted;
    }

    public void setInterrupted(boolean interrupted)
    {
        this.interrupted = interrupted;
    }

    @Override
    public void run()
    {
        FileWriter fileWriter = null;
        try
        {
            fileWriter = new FileWriter("log.txt");
        }
        catch (IOException e)
        {
            simulationView.showErrorMessage("Unexpected error occurred when interacting with the log text file");
        }

        Set<Client> existingClients = new HashSet<>();
        int averageWaitingTime = 0;
        int averageWaitingTime2 = 0;
        int averageServiceTime = 0;
        int peakHour = 0;
        int peakCapacity = Integer.MIN_VALUE;
        int currentTime = 0;
        int currentTime2 = 0;
        boolean prematureFinish;
        do
        {
            prematureFinish = true;

            int currentCapacity = 0;
            List<Client> removedClients = new ArrayList<>();
            for (Client client: generatedClientsList)
            {
                prematureFinish = false;
                if (client.getArrivalTime() <= currentTime)
                {
                    existingClients.add(client);
                    if (scheduler.dispatchClient(client))
                    {
                        averageWaitingTime += (currentTime - client.getArrivalTime());
                        removedClients.add(client);
                    }
                    else
                    {
                        ++currentCapacity;
                    }
                }
            }
            if (!removedClients.isEmpty())
            {
                generatedClientsList.removeAll(removedClients);
            }

            try
            {
                if (currentTime != 0)
                {
                    fileWriter.write("\n\n");
                }
                fileWriter.write("Time " + currentTime + '\n');
                simulationView.getTimeLabel2().setText(String.valueOf(currentTime));
                fileWriter.write("Waiting clients: ");
                if (!generatedClientsList.isEmpty())
                {
                    String labelText = "";
                    for (Client client: generatedClientsList)
                    {
                        fileWriter.write("(" + client.getId() + "," + client.getArrivalTime() + "," + client.getServiceTime() + "); ");
                        labelText += "(" + client.getId() + "," + client.getArrivalTime() + "," + client.getServiceTime() + "); ";
                    }
                    simulationView.getClientsLabel2().setText(labelText);
                }
                else
                {
                    fileWriter.write("empty");
                    simulationView.getClientsLabel2().setText("empty");
                    simulationView.getClientsLabel().setForeground(new Color(178, 75, 75));
                }

                int i = 0;
                for (Server server: scheduler.getServerList())
                {
                    currentCapacity += server.getClients().size();
                    ++i;
                    fileWriter.write("\nQueue " + i + ": ");
                    if (server.getClients().isEmpty())
                    {
                        fileWriter.write("empty");
                        simulationView.getQueueLabelList2().get(i - 1).setText("empty");
                    }
                    else
                    {
                        averageWaitingTime2 += server.getWaitingPeriod().get();
                        prematureFinish = false;
                        String labelText = "";
                        Client currentClient = server.getClients().peek();
                        for (Client client: server.getClients())
                        {
                            fileWriter.write("(" + client.getId() + "," + client.getArrivalTime() + "," + client.getServiceTime() + "); ");
                            labelText += "(" + client.getId() + "," + client.getArrivalTime() + "," + client.getServiceTime() + "); ";
                            if (client == currentClient)
                            {
                                client.setServiceTime(client.getServiceTime() - 1);
                                averageServiceTime += 1;
                            }
                        }
                        simulationView.getQueueLabelList2().get(i - 1).setText(labelText);
                    }

                    if (server.getClients().remainingCapacity() == 0)
                    {
                        simulationView.getQueueLabelList().get(i - 1).setForeground(new Color(178, 75, 75));
                    }
                    else
                    {
                        simulationView.getQueueLabelList().get(i - 1).setForeground(new Color(88, 164, 110));
                    }
                }
                averageWaitingTime2 /= scheduler.getServerList().size();

                if (currentCapacity > peakCapacity)
                {
                    peakCapacity = currentCapacity;
                    peakHour = currentTime;
                }
            }
            catch (IOException | NullPointerException e)
            {
                simulationView.showErrorMessage("Unexpected error occurred when writing in the log text file");
            }

            ++currentTime;
            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {
                simulationView.showErrorMessage("Unexpected error occurred when pausing the MAIN thread");
            }

            if (prematureFinish || interrupted)
            {
                currentTime2 = currentTime;
                currentTime = simulationTime + 1;
            }

        }while (currentTime <= simulationTime);

        scheduler.getServerList().get(0).setGo(false); // stopping all servers

        if (!interrupted)
        {
            if (currentTime2 == 0)
            {
                currentTime2 = simulationTime;
            }
            for (Client client: generatedClientsList)
            {
                averageWaitingTime += (currentTime2 - client.getArrivalTime());
            }

            averageWaitingTime /= existingClients.size();
            averageWaitingTime2 /= currentTime2;
            averageServiceTime /= existingClients.size();

            ResultsView resultsView = new ResultsView(averageWaitingTime + averageWaitingTime2, averageServiceTime, peakHour, windowListener);

            try
            {
                fileWriter.write("\n\nResults\n");
                fileWriter.write("Average Waiting Time: " + (averageWaitingTime + averageWaitingTime2) + '\n');
                fileWriter.write("Average Service Time: " + averageServiceTime + '\n');
                fileWriter.write("Peak Hour: " + peakHour);
            }
            catch (IOException e)
            {
                simulationView.showErrorMessage("Unexpected error occurred when writing in the log text file");
            }
        }

        try
        {
            fileWriter.close();
        }
        catch (IOException | NullPointerException e)
        {
            simulationView.showErrorMessage("Unexpected error occurred when closing the log text file!");
        }
    }
}