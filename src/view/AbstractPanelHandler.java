package view;

import control.MainController;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    public void addToOutput(String text){
        if(output.getText().equals("")){
            output.setText(new SimpleDateFormat("HH:mm:ss").format(new Date()) + ": " + text);
        }else{
            output.setText(output.getText() + "\n" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + ": " + text);
        }
    }

    public JPanel getPanel(){
        return panel;
    }

    protected abstract void createButtons();


}
