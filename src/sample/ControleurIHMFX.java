package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Timer;

public class ControleurIHMFX {

    Controleur controleur;
    VueIHMFX vue;
    Button select;
    Button reanimer;
    Button undo;
    Button redo;
    Button reset;
    Timeline timer;

    ControleurIHMFX(Controleur controleur, VueIHMFX vue){
        this.controleur=controleur;
        this.vue=vue;

        select = new Button("Selection");
        select.setOnAction(new ActionSelection());

        reanimer = new Button("Réanimer");
        reanimer.setOnAction(new ActionAnimer());

        undo = new Button("Undo");
        undo.setOnAction(new ActionUndo());

        redo = new Button("Redo");
        redo.setOnAction(new ActionRedo());

        reset = new Button("Reset");
        reset.setOnAction(new ActionReset());

        reanimer = new Button("Animer");
        reanimer.setOnAction(new ActionAnimer());

        timer = new Timeline(new KeyFrame(Duration.seconds(0.25),
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        controleur.animer();
                    }
                }));
    }

    class ActionSelection implements EventHandler<ActionEvent>{
        public void handle(ActionEvent event){
            String path = getPath();
            try{
                timer.stop();
                vue.reinitialiserVue();
                controleur.getPlato(path);
            }catch(IOException e){
                System.out.println(e.getStackTrace());
            }
        }
    }

    class ActionAnimer implements EventHandler<ActionEvent>{
        public void handle(ActionEvent event){
            if(controleur.commandeListe().exec().size()!=0){
                timer.setCycleCount(controleur.commandeListe().exec().size());
                timer.play();
                controleur.reinitialiserAnimation();
            }
        }
    }


    class ActionUndo implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event){
            timer.stop();
            controleur.reinitialiserAnimation();
            controleur.undo();
        }
    }

    class ActionRedo implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event){
            timer.stop();
            controleur.reinitialiserAnimation();
            controleur.redo();
        }
    }

    class ActionReset implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event){
            timer.stop();
            controleur.reinitialiserAnimation();
            controleur.reset();
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
