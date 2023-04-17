package Assignment2.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainView extends JFrame
{
    class MyButton extends JButton
    {

        private Color hoverBackgroundColor;
        private Color pressedBackgroundColor;

        public MyButton()
        {
            this(null);
        }

        public MyButton(String text)
        {
            super(text);
            super.setContentAreaFilled(false);
        }

        @Override
        protected void paintComponent(Graphics g)
        {
            if (getModel().isPressed())
            {
                g.setColor(pressedBackgroundColor);
            }
            else if (getModel().isRollover())
            {
                g.setColor(hoverBackgroundColor);
            }
            else
            {
                g.setColor(getBackground());
            }
            g.fillRect(0, 0, getWidth(), getHeight());
            super.paintComponent(g);
        }

        public Color getHoverBackgroundColor()
        {
            return hoverBackgroundColor;
        }

        public void setHoverBackgroundColor(Color hoverBackgroundColor)
        {
            this.hoverBackgroundColor = hoverBackgroundColor;
        }

        public Color getPressedBackgroundColor()
        {
            return pressedBackgroundColor;
        }

        public void setPressedBackgroundColor(Color pressedBackgroundColor)
        {
            this.pressedBackgroundColor = pressedBackgroundColor;
        }
    }

    private JTextField numberOfClientsTextField;
    private JTextField numberOfQueuesTextField;
    private JTextField simulationTimeTextField;
    private JTextField minimumArrivalTimeTextField;
    private JTextField maximumArrivalTimeTextField;
    private JTextField minimumServiceTimeTextField;
    private JTextField maximumServiceTimeTextField;

    private MyButton clearEverythingButton;
    private MyButton startSimulationButton;

    JComboBox<String> selectionStrategyComboBox;

    public MainView()
    {
        this.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Tudor Cristea\\Pictures\\Screenshots\\queue.png"));
        this.setTitle("Queue Simulator");
        this.getContentPane().setBackground(new Color(216, 191, 216));
        this.setBounds(100, 100, 880, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(new GridLayout(10, 2, 10, 10));

        JLabel titleLabel = new JLabel("Queue Simulator");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
        this.getContentPane().add(titleLabel);

        JLabel emptyLabel = new JLabel("");
        this.getContentPane().add(emptyLabel);

        JLabel numberOfClientsLabel = new JLabel("Number of Clients:");
        numberOfClientsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        numberOfClientsLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        this.getContentPane().add(numberOfClientsLabel);

        numberOfClientsTextField = new JTextField();
        numberOfClientsTextField.setFont(new Font("Tahoma", Font.PLAIN, 20));
        this.getContentPane().add(numberOfClientsTextField);
        numberOfClientsTextField.setColumns(10);

        JLabel numberOfQueuesLabel = new JLabel("Number of Queues:");
        numberOfQueuesLabel.setHorizontalAlignment(SwingConstants.CENTER);
        numberOfQueuesLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        this.getContentPane().add(numberOfQueuesLabel);

        numberOfQueuesTextField = new JTextField();
        numberOfQueuesTextField.setFont(new Font("Tahoma", Font.PLAIN, 20));
        numberOfQueuesTextField.setColumns(10);
        this.getContentPane().add(numberOfQueuesTextField);

        JLabel simulationTimeLabel = new JLabel("Simulation Time:");
        simulationTimeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        simulationTimeLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        this.getContentPane().add(simulationTimeLabel);

        simulationTimeTextField = new JTextField();
        simulationTimeTextField.setFont(new Font("Tahoma", Font.PLAIN, 20));
        simulationTimeTextField.setColumns(10);
        this.getContentPane().add(simulationTimeTextField);

        JLabel minimumArrivalTimeLabel = new JLabel("Minimum Arrival Time:");
        minimumArrivalTimeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        minimumArrivalTimeLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        this.getContentPane().add(minimumArrivalTimeLabel);

        minimumArrivalTimeTextField = new JTextField();
        minimumArrivalTimeTextField.setFont(new Font("Tahoma", Font.PLAIN, 20));
        minimumArrivalTimeTextField.setColumns(10);
        this.getContentPane().add(minimumArrivalTimeTextField);

        JLabel maximumArrivalTimeLabel = new JLabel("Maximum Arrival Time:");
        maximumArrivalTimeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        maximumArrivalTimeLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        this.getContentPane().add(maximumArrivalTimeLabel);

        maximumArrivalTimeTextField = new JTextField();
        maximumArrivalTimeTextField.setFont(new Font("Tahoma", Font.PLAIN, 20));
        maximumArrivalTimeTextField.setColumns(10);
        this.getContentPane().add(maximumArrivalTimeTextField);

        JLabel minimumServiceTimeLabel = new JLabel("Minimum Service Time:");
        minimumServiceTimeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        minimumServiceTimeLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        this.getContentPane().add(minimumServiceTimeLabel);

        minimumServiceTimeTextField = new JTextField();
        minimumServiceTimeTextField.setFont(new Font("Tahoma", Font.PLAIN, 20));
        minimumServiceTimeTextField.setColumns(10);
        this.getContentPane().add(minimumServiceTimeTextField);

        JLabel maximumServiceTimeLabel = new JLabel("Maximum Service Time:");
        maximumServiceTimeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        maximumServiceTimeLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        this.getContentPane().add(maximumServiceTimeLabel);

        maximumServiceTimeTextField = new JTextField();
        maximumServiceTimeTextField.setFont(new Font("Tahoma", Font.PLAIN, 20));
        maximumServiceTimeTextField.setColumns(10);
        this.getContentPane().add(maximumServiceTimeTextField);

        JLabel selectionStrategyLabel = new JLabel("Selection Strategy:");
        selectionStrategyLabel.setHorizontalAlignment(SwingConstants.CENTER);
        selectionStrategyLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        this.getContentPane().add(selectionStrategyLabel);

        selectionStrategyComboBox = new JComboBox<>();
        selectionStrategyComboBox.setModel(new DefaultComboBoxModel<>(new String[] {"Shortest Queue", "Shortest Time"}));
        selectionStrategyComboBox.setSelectedIndex(0);
        selectionStrategyComboBox.setMaximumRowCount(2);
        selectionStrategyComboBox.setFont(new Font("Tahoma", Font.PLAIN, 20));
        this.getContentPane().add(selectionStrategyComboBox);

        clearEverythingButton = new MyButton("Clear Everything");
        clearEverythingButton.setBackground(new Color(250, 129, 129));
        clearEverythingButton.setHoverBackgroundColor(new Color (252, 89, 89));
        clearEverythingButton.setPressedBackgroundColor(new Color(255, 63, 63));
        clearEverythingButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
        this.getContentPane().add(clearEverythingButton);

        startSimulationButton = new MyButton("Start Simulation");
        startSimulationButton.setBackground(new Color(255, 200, 100));
        startSimulationButton.setHoverBackgroundColor(new Color (255, 200, 50));
        startSimulationButton.setPressedBackgroundColor(new Color(255, 200, 0));
        startSimulationButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
        this.getContentPane().add(startSimulationButton);

        this.setVisible(true);
    }

    public JTextField getNumberOfClientsTextField()
    {
        return numberOfClientsTextField;
    }

    public void setNumberOfClientsTextField(JTextField numberOfClientsTextField)
    {
        this.numberOfClientsTextField = numberOfClientsTextField;
    }

    public JTextField getNumberOfQueuesTextField()
    {
        return numberOfQueuesTextField;
    }

    public void setNumberOfQueuesTextField(JTextField numberOfQueuesTextField)
    {
        this.numberOfQueuesTextField = numberOfQueuesTextField;
    }

    public JTextField getSimulationTimeTextField()
    {
        return simulationTimeTextField;
    }

    public void setSimulationTimeTextField(JTextField simulationTimeTextField)
    {
        this.simulationTimeTextField = simulationTimeTextField;
    }

    public JTextField getMinimumArrivalTimeTextField()
    {
        return minimumArrivalTimeTextField;
    }

    public void setMinimumArrivalTimeTextField(JTextField minimumArrivalTimeTextField)
    {
        this.minimumArrivalTimeTextField = minimumArrivalTimeTextField;
    }

    public JTextField getMaximumArrivalTimeTextField()
    {
        return maximumArrivalTimeTextField;
    }

    public void setMaximumArrivalTimeTextField(JTextField maximumArrivalTimeTextField)
    {
        this.maximumArrivalTimeTextField = maximumArrivalTimeTextField;
    }

    public JTextField getMinimumServiceTimeTextField()
    {
        return minimumServiceTimeTextField;
    }

    public void setMinimumServiceTimeTextField(JTextField minimumServiceTimeTextField)
    {
        this.minimumServiceTimeTextField = minimumServiceTimeTextField;
    }

    public JTextField getMaximumServiceTimeTextField()
    {
        return maximumServiceTimeTextField;
    }

    public void setMaximumServiceTimeTextField(JTextField maximumServiceTimeTextField)
    {
        this.maximumServiceTimeTextField = maximumServiceTimeTextField;
    }

    public JComboBox<String> getSelectionStrategyComboBox()
    {
        return selectionStrategyComboBox;
    }

    public void setSelectionStrategyComboBox(JComboBox<String> selectionStrategyComboBox)
    {
        this.selectionStrategyComboBox = selectionStrategyComboBox;
    }

    public void addClearEverythingButtonListener(ActionListener actionListener)
    {
        clearEverythingButton.addActionListener(actionListener);
    }

    public void addStartSimulationButtonListener(ActionListener actionListener)
    {
        startSimulationButton.addActionListener(actionListener);
    }

    public void showErrorMessage(String message)
    {
        JOptionPane.showMessageDialog(this, message, "ERROR", JOptionPane.ERROR_MESSAGE);
    }
}