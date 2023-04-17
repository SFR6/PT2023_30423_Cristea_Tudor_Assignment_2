package Assignment2;

import Assignment2.controllers.MainController;
import Assignment2.views.MainView;

public class App
{
    public static void main(String[] args)
    {
        MainView mainView = new MainView();

        MainController mainController = new MainController(mainView);
    }
}