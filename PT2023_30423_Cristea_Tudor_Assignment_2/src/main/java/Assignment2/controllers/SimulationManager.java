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
    private int simulationTime;
    private int minimumArrivalTime;
    private int maximumArrivalTime;
    private int minimumServiceTime;
    private int maximumServiceTime;

    private Scheduler scheduler;
    private SimulationView simulationView;
    private WindowListener windowListener;
    private List<Client> generatedClientsList;
    //private List<Client> processingClientsList;

    public SimulationManager(SimulationView simulationView, int numberOfClients, int numberOfQueues, int simulationTime, int minimumArrivalTime, int maximumArrivalTime, int minimumServiceTime, int maximumServiceTime, SelectionPolicy selectionPolicy)
    {
        this.simulationView = simulationView;

        this.numberOfClients = numberOfClients;
        this.numberOfQueues = numberOfQueues;
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
        //this.processingClientsList = new ArrayList<>();

        Strategy strategy;
        if (selectionPolicy == SelectionPolicy.SHORTEST_QUEUE)
        {
            strategy = new ShortestQueueStrategy();
        }
        else
        {
            strategy = new ShortestTimeStrategy();
        }
        this.scheduler = new Scheduler(this.numberOfQueues, 1, strategy);
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

        int averageWaitingTime = 0;
        int averageServiceTime = 0;
        int peakHour = 0;
        int peakCapacity = Integer.MIN_VALUE;
        int currentTime = 0;
        boolean prematureFinish;
        do
        {
            prematureFinish = true;

            List<Client> removedClients = new ArrayList<>();
            for (Client client: generatedClientsList)
            {
                prematureFinish = false;
                if (client.getArrivalTime() <= currentTime)
                {
                    if (scheduler.dispatchClient(client))
                    {
                        averageWaitingTime += (currentTime - client.getArrivalTime());
                        removedClients.add(client);
                    }
                }
            }
            if (!removedClients.isEmpty())
            {
                generatedClientsList.removeAll(removedClients);
                //processingClientsList.addAll(removedClients);
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

                int currentCapacity = 0;
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
                        prematureFinish = false;
                        String labelText = "";
                        for (Client client: server.getClients())
                        {
/*                            Client correctClient = null;
                            for (Client client2: processingClientsList)
                            {
                                if (client.getId() == client2.getId())
                                {
                                    correctClient = client2;
                                }
                            }
                            if (correctClient != null)
                            {*/
                                fileWriter.write("(" + client.getId() + "," + client.getArrivalTime() + "," + client.getServiceTime() + "); ");
                                labelText += "(" + client.getId() + "," + client.getArrivalTime() + "," + client.getServiceTime() + "); ";
                                //correctClient.setServiceTime(correctClient.getServiceTime() - 1);
                                client.setServiceTime(client.getServiceTime() - 1);
                                averageServiceTime += 1;

/*                                if (correctClient.getServiceTime() == 0)
                                {
                                    processingClientsList.remove(correctClient);
                                }
                            }*/
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

            if (prematureFinish)
            {
                currentTime = simulationTime + 1;
            }

        }while (currentTime <= simulationTime);

        scheduler.getServerList().get(0).setGo(false); // stopping all servers

        averageWaitingTime /= (numberOfClients - generatedClientsList.size());
        averageServiceTime /= (numberOfClients - generatedClientsList.size());
        ResultsView resultsView = new ResultsView(averageWaitingTime, averageServiceTime, peakHour, windowListener);

        try
        {
            fileWriter.write("\n\nResults\n");
            fileWriter.write("Average Waiting Time: " + averageWaitingTime + '\n');
            fileWriter.write("Average Service Time: " + averageServiceTime + '\n');
            fileWriter.write("Peak Hour: " + peakHour);
        }
        catch (IOException e)
        {
            simulationView.showErrorMessage("Unexpected error occurred when writing in the log text file");
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