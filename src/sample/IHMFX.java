package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class IHMFX extends Application implements Observateur {
    VueIHMFX vue;
    Stage window;
    Controleur controleur;
    ControleurIHMFX controleurIHMFX;

    @Override
    public void start(Stage primaryStage){
        window = primaryStage;
        controleur = Controleur.getControleur();
        controleur.abonne(this);
        controleurIHMFX = new ControleurIHMFX(controleur,null);

        /*montage de la scene*/
        definirFenetre(window, new GridPane());



        primaryStage.setTitle("Sokoban");
        primaryStage.show();
    }

    @Override
    public void actualise(){
        if(vue!=null){
            Platform.runLater(()->{
                vue.dessine();
            });
        }else{
            try {
                vue = new VueIHMFX(Controleur.getControleur());
                Scene scene = definirFenetre(window,vue.gridPane);
                scene.setOnKeyPressed(e ->{handle(e);});
                actualise();
            }catch(Exception e){
                System.out.println(e.getStackTrace());
            }
        }

    }

    public void lance(){launch(new String[]{});}

    public Scene definirFenetre(Stage window, Region node){
        MonteurScene monteurScene = new MonteurScene();

        Scene scene = monteurScene.
                setCentre(node).
                ajoutBas(controleurIHMFX.select).
                setLargeur(1200).
                setHauteur(1200).
                retourneScene();

        window.setScene(scene);
        return scene;
    }

    private void handle(KeyEvent e){
        controleur.move(e.getCode());
        actualise();
    }


}
