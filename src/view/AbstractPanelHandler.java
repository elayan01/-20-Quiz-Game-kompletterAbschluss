package view;

import control.MainController;

import javax.swing.*;

public abstract class AbstractPanelHandler {

    protected MainFrame mainFrame;
    protected MainController mainController;
    protected JPanel panel;
    protected JTextArea output;

    public AbstractPanelHandler(MainFrame mainFrame, MainController mainController){
        this.mainFrame = mainFrame;
        this.mainController = mainController;
    }

    public void setPointer(JPanel panel, JTextArea output){
        this.panel = panel;
        this.output = output;
    }

    protected void addToOutput(String text){
        if(output.getText().equals("")){
            output.setText(text);
        }else{
            output.setText(output.getText() + "\n" + text);
        }
    }

    public JPanel getPanel(){
        return panel;
    }

    protected abstract void createButtons();


}
