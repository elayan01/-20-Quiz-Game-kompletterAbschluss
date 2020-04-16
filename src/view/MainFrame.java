package view;

import control.MainController;
import view.adminPanels.DatabaseAdministrationPanelHandler;
import view.navigationPanels.SingleOrMultiPanelHandler;
import view.navigationPanels.StartingPanelHandler;
import view.singleplayerPanels.SingleplayerPanelHandler;

import javax.swing.*;

/**
 * Created by Jean-Pierre on 15.11.2016.
 */
public class MainFrame extends JFrame {

    // Attribute

    // Referenzen
    private AbstractPanelHandler startingPanelHandler, adminPanelHandler, singleOrMultiPanelHandler, singleplayerGameHandler;
    private JPanel startingPanel, adminPanel, singleOrMultiPanel, singleplayerGamePanel;

    /**
     * Konstruktor
     * @param name Der Titel des Fensters
     * @param x Die obere linke x-Koordinate des Fensters bzgl. des Bildschirms
     * @param y Die obere linke y-Koordinaite des Fensters bzgl. des Bildschirms
     * @param width Die Breite des Fensters
     * @param height Die Höhe des Fensters
     */
    public MainFrame(MainController mainController, String name, int x, int y, int width, int height) {
        this.createPanels(mainController);

        this.setLocation(x,y);
        this.setSize(width,height);
        this.setTitle(name);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        this.setVisible(true);
    }

    private void createPanels(MainController mainController){
        startingPanelHandler        = new StartingPanelHandler(this, mainController);
        startingPanel               = startingPanelHandler.getPanel();

        adminPanelHandler           = new DatabaseAdministrationPanelHandler(this,mainController);
        adminPanel                  = adminPanelHandler.getPanel();

        singleOrMultiPanelHandler   = new SingleOrMultiPanelHandler(this,mainController);
        singleOrMultiPanel          = singleOrMultiPanelHandler.getPanel();

        singleplayerGameHandler     = new SingleplayerPanelHandler(this,mainController);
        singleplayerGamePanel       = singleplayerGameHandler.getPanel();

        switchToPanel(startingPanel);
    }

    private void switchToPanel(JPanel panel){
        this.getContentPane().removeAll();
        this.getContentPane().add(panel);
        this.validate();
        this.repaint();
    }

    public void switchToAdminDB(){
        switchToPanel(adminPanel);
    }

    public void switchToStart(){
        switchToPanel(startingPanel);
    }

    public void switchSingleOrMulti(){
        switchToPanel(singleOrMultiPanel);
        ((SingleOrMultiPanelHandler)singleOrMultiPanelHandler).welcomeText();
    }

    public void switchToSingleplayerGame(){
        ((SingleplayerPanelHandler)singleplayerGameHandler).prepare();
        switchToPanel(singleplayerGamePanel);
    }
}
