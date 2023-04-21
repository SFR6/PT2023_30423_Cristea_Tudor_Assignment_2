package Assignment2.controllers;

import Assignment2.models.SelectionPolicy;
import Assignment2.views.MainView;
import Assignment2.views.SimulationView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class MainController
{
    private MainView mainView;

    private WindowListener windowListener;

    private SimulationManager simulationManager;

    public MainController(MainView mainView)
    {
        this.mainView = mainView;

        windowListener = new WindowListener()
        {
            @Override
            public void windowOpened(WindowEvent e)
            {
                simulationManager.setInterrupted(false);
            }

            @Override
            public void windowClosing(WindowEvent e)
            {
                simulationManager.setInterrupted(true);
                try
                {
                    Thread.sleep(1050);
                }
                catch (InterruptedException exception)
                {
                    mainView.setEnabled(true);
                    mainView.showErrorMessage("Unexpected error occurred when closing the simulation!");
                }
                mainView.setEnabled(true);
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

        this.mainView.addClearEverythingButtonListener(new ClearEverythingButtonListener());
        this.mainView.addStartSimulationButtonListener(new StartSimulationButtonListener());
    }

    class ClearEverythingButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            mainView.getNumberOfClientsTextField().setText("");
            mainView.getNumberOfQueuesTextField().setText("");
            mainView.getQueueSizeTextField().setText("");
            mainView.getSimulationTimeTextField().setText("");
            mainView.getMinimumArrivalTimeTextField().setText("");
            mainView.getMaximumArrivalTimeTextField().setText("");
            mainView.getMinimumServiceTimeTextField().setText("");
            mainView.getMaximumServiceTimeTextField().setText("");
        }
    }

    private void validateInput(String string) throws Exception
    {
        String regex = "^[1-9]|([1-9]\\d+)$";
        if (!string.matches(regex))
        {
            if (string.equals(""))
            {
                throw new Exception("Invalid input: <empty string>");
            }
            else
            {
                throw new Exception("Invalid input: " + string);
            }
        }
    }

    private void validateInput2(int minimumTime, int maximumTime) throws Exception
    {
        if (minimumTime > maximumTime)
        {
            throw new Exception("Minimum Time cannot be larger than Maximum Time");
        }
    }

    class StartSimulationButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            String[] inputString = new String[8];
            inputString[0] = mainView.getNumberOfClientsTextField().getText();
            inputString[1] = mainView.getNumberOfQueuesTextField().getText();
            inputString[2] = mainView.getQueueSizeTextField().getText();
            inputString[3] = mainView.getSimulationTimeTextField().getText();
            inputString[4] = mainView.getMinimumArrivalTimeTextField().getText();
            inputString[5] = mainView.getMaximumArrivalTimeTextField().getText();
            inputString[6] = mainView.getMinimumServiceTimeTextField().getText();
            inputString[7] = mainView.getMaximumServiceTimeTextField().getText();

            boolean error = false;
            for (String s: inputString)
            {
                try
                {
                    validateInput(s);
                }
                catch (Exception exception)
                {
                    error = true;
                    mainView.showErrorMessage(exception.getMessage());
                }
            }

            if (!error)
            {
                int numberOfClients = Integer.parseInt(inputString[0]);
                int numberOfQueues = Integer.parseInt(inputString[1]);
                int queueSize = Integer.parseInt(inputString[2]);
                int simulationTime = Integer.parseInt(inputString[3]);
                int minimumArrivalTime = Integer.parseInt(inputString[4]);
                int maximumArrivalTime = Integer.parseInt(inputString[5]);
                int minimumServiceTime = Integer.parseInt(inputString[6]);
                int maximumServiceTime = Integer.parseInt(inputString[7]);

                try
                {
                    validateInput2(minimumArrivalTime, maximumArrivalTime);
                }
                catch (Exception exception)
                {
                    error = true;
                    mainView.showErrorMessage("For Arrival Times: " + exception.getMessage());
                }

                try
                {
                    validateInput2(minimumServiceTime, maximumServiceTime);
                }
                catch (Exception exception)
                {
                    error = true;
                    mainView.showErrorMessage("For Service Times: " + exception.getMessage());
                }

                if (!error)
                {
                    SelectionPolicy selectionPolicy;
                    if (mainView.getSelectionStrategyComboBox().getSelectedIndex() == 0)
                    {
                        selectionPolicy = SelectionPolicy.SHORTEST_QUEUE;
                    }
                    else
                    {
                        selectionPolicy = SelectionPolicy.SHORTEST_TIME;
                    }

                    SimulationView simulationView = new SimulationView(numberOfQueues, windowListener);
                    mainView.setEnabled(false);
                    simulationManager = new SimulationManager(simulationView, numberOfClients, numberOfQueues, queueSize, simulationTime, minimumArrivalTime, maximumArrivalTime, minimumServiceTime, maximumServiceTime, selectionPolicy);
                    Thread thread = new Thread(simulationManager);
                    thread.start();
                }
            }
        }
    }
}