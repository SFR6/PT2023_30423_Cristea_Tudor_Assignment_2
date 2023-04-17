package Assignment2.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

public class SimulationView extends JFrame
{
    private JLabel timeLabel2;
    private JLabel clientsLabel;
    private JLabel clientsLabel2;
    private List<JLabel> queueLabelList;
    private List<JLabel> queueLabelList2;

    public SimulationView(int numberOfQueues, WindowListener windowListener)
    {
        this.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Tudor Cristea\\Pictures\\Screenshots\\queue.png"));
        this.setTitle("Queue Simulator");
        this.getContentPane().setBackground(new Color(216, 191, 216));
        this.setBounds(100, 100, 880, 500);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.getContentPane().setLayout(new GridLayout(2 + numberOfQueues, 2, 10, 10));

        JLabel timeLabel = new JLabel("Time");
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timeLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
        this.getContentPane().add(timeLabel);

        timeLabel2 = new JLabel("");
        timeLabel2.setHorizontalAlignment(SwingConstants.CENTER);
        timeLabel2.setFont(new Font("Tahoma", Font.PLAIN, 25));
        this.getContentPane().add(timeLabel2);

        clientsLabel = new JLabel("Waiting clients:");
        clientsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        clientsLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
        this.getContentPane().add(clientsLabel);

        clientsLabel2 = new JLabel("");
        clientsLabel2.setHorizontalAlignment(SwingConstants.CENTER);
        clientsLabel2.setFont(new Font("Tahoma", Font.PLAIN, 25));
        this.getContentPane().add(clientsLabel2);

        queueLabelList = new ArrayList<>();
        queueLabelList2 = new ArrayList<>();
        for (int i = 1; i <= numberOfQueues; ++i)
        {
            JLabel queueLabel = new JLabel("Queue " + i + ":");
            queueLabel.setHorizontalAlignment(SwingConstants.CENTER);
            queueLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
            this.getContentPane().add(queueLabel);

            queueLabelList.add(queueLabel);

            JLabel queueLabel2 = new JLabel("");
            queueLabel2.setHorizontalAlignment(SwingConstants.CENTER);
            queueLabel2.setFont(new Font("Tahoma", Font.PLAIN, 25));
            this.getContentPane().add(queueLabel2);

            queueLabelList2.add(queueLabel2);
        }

        this.addWindowListener(windowListener);
        this.setVisible(true);
    }

    public JLabel getTimeLabel2()
    {
        return timeLabel2;
    }

    public void setTimeLabel2(JLabel timeLabel2)
    {
        this.timeLabel2 = timeLabel2;
    }

    public JLabel getClientsLabel()
    {
        return clientsLabel;
    }

    public void setClientsLabel(JLabel clientsLabel)
    {
        this.clientsLabel = clientsLabel;
    }

    public JLabel getClientsLabel2()
    {
        return clientsLabel2;
    }

    public void setClientsLabel2(JLabel clientsLabel2)
    {
        this.clientsLabel2 = clientsLabel2;
    }

    public List<JLabel> getQueueLabelList()
    {
        return queueLabelList;
    }

    public void setQueueLabelList(List<JLabel> queueLabelList)
    {
        this.queueLabelList = queueLabelList;
    }

    public List<JLabel> getQueueLabelList2()
    {
        return queueLabelList2;
    }

    public void setQueueLabelList2(List<JLabel> queueLabelList2)
    {
        this.queueLabelList2 = queueLabelList2;
    }

    public void showErrorMessage(String message)
    {
        JOptionPane.showMessageDialog(this, message, "ERROR", JOptionPane.ERROR_MESSAGE);
    }
}