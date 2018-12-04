package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ControleurIHMFX {

    Controleur controleur;
    VueIHMFX vue;
    Button select;

    ControleurIHMFX(Controleur controleur, VueIHMFX vue){
        this.controleur=controleur;
        this.vue=vue;

        select = new Button("Selection");
        select.setOnAction(new ActionSelection());
    }

    class ActionSelection implements EventHandler<ActionEvent>{
        public void handle(ActionEvent event){
            String path = getPath();
            try{
                controleur.setPlateau(controleur.getPlato(path));
                controleur.setPosition(controleur.getCoordinate(controleur.getPlato(path)));
                controleur.setDonnees(new int[]{0,0});
                vue = new VueIHMFX(controleur);
                controleur.notifie();
            }catch(IOException e){
                System.out.println(e.getStackTrace());
            }
        }
    }

    public String getPath(){
        String path="";
        List<File> files = (new FileChooser()).showOpenMultipleDialog(new Stage());
        if(! (files == null)) {
            path= files.get(0).getAbsolutePath();
        }
        return path;
    }
}
