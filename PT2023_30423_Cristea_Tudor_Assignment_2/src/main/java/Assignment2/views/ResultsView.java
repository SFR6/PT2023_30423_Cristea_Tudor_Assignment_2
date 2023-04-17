package Assignment2.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowListener;

public class ResultsView extends JFrame
{
    public ResultsView(int averageWaitingTime, int averageServiceTime, int peakHour, WindowListener windowListener)
    {
        this.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Tudor Cristea\\Pictures\\Screenshots\\queue.png"));
        this.setTitle("Queue Simulator");
        this.getContentPane().setBackground(new Color(216, 191, 216));
        this.setBounds(100, 100, 880, 500);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.getContentPane().setLayout(new GridLayout(4, 2, 10, 10));

        this.getContentPane().setLayout(new GridLayout(4, 2, 10, 10));

        JLabel resultsLabel = new JLabel("Results");
        resultsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        resultsLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
        this.getContentPane().add(resultsLabel);

        JLabel emptyLabel = new JLabel("");
        this.getContentPane().add(emptyLabel);

        JLabel averageWaitingTimeLabel1 = new JLabel("Average Waiting Time:");
        averageWaitingTimeLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        averageWaitingTimeLabel1.setFont(new Font("Tahoma", Font.BOLD, 25));
        this.getContentPane().add(averageWaitingTimeLabel1);

        JLabel averageWaitingTimeLabel2 = new JLabel(String.valueOf(averageWaitingTime));
        averageWaitingTimeLabel2.setHorizontalAlignment(SwingConstants.CENTER);
        averageWaitingTimeLabel2.setFont(new Font("Tahoma", Font.PLAIN, 25));
        this.getContentPane().add(averageWaitingTimeLabel2);

        JLabel averageServiceTimeLabel1 = new JLabel("Average Service Time:");
        averageServiceTimeLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        averageServiceTimeLabel1.setFont(new Font("Tahoma", Font.BOLD, 25));
        this.getContentPane().add(averageServiceTimeLabel1);

        JLabel averageServiceTimeLabel2 = new JLabel(String.valueOf(averageServiceTime));
        averageServiceTimeLabel2.setHorizontalAlignment(SwingConstants.CENTER);
        averageServiceTimeLabel2.setFont(new Font("Tahoma", Font.PLAIN, 25));
        this.getContentPane().add(averageServiceTimeLabel2);

        JLabel peakHourLabel1 = new JLabel("Peak Hour:");
        peakHourLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        peakHourLabel1.setFont(new Font("Tahoma", Font.BOLD, 25));
        this.getContentPane().add(peakHourLabel1);

        JLabel peakHourLabel2 = new JLabel(String.valueOf(peakHour));
        peakHourLabel2.setHorizontalAlignment(SwingConstants.CENTER);
        peakHourLabel2.setFont(new Font("Tahoma", Font.PLAIN, 25));
        this.getContentPane().add(peakHourLabel2);

        this.addWindowListener(windowListener);
        this.setVisible(true);
    }
}