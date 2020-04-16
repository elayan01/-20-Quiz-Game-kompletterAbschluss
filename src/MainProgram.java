import control.MainController;
import view.MainFrame;

import java.awt.*;

public class MainProgram {

    public static void main (String[] args){
        EventQueue.invokeLater(
                new Runnable() {
                    @Override
                    public void run() {
                        MainProgram.setup();
                    }
                });
    }

    private static void setup(){
        MainController mainController = new MainController();
        MainFrame mainFrame = new MainFrame(mainController, "Abschlussprojekt",50,50,1200,900);
    }

}
