package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class IHMFX extends Application implements Observateur {
    VueIHMFX vue;

    @Override
    public void start(Stage primaryStage)throws Exception{
        Controleur controleur = Controleur.getControleur();
        controleur.abonne(this);

        vue = new VueIHMFX(controleur);
        ControleurIHMFX controleurIHMFX = new ControleurIHMFX(Controleur.getControleur(),vue);
        vue.gridPane.setAlignment(Pos.CENTER);

        /*montage de la scene*/
        Scene scene = definirFenetre(primaryStage, vue.gridPane,controleur, controleurIHMFX);

        scene.setOnKeyPressed(new ActionMove());

        primaryStage.setTitle("Sokoban");
        primaryStage.show();
    }

    @Override
    public void actualise(){
        Platform.runLater(() -> {
            vue.dessine();
        });




    }

    void lance(){launch(new String[]{});}

    private Scene definirFenetre(Stage window, Region node, Controleur controleur, ControleurIHMFX controleurIHMFX){
        MonteurScene monteurScene = new MonteurScene();

        Scene scene = monteurScene.
                setCentre(node).
                ajoutBas(controleurIHMFX.select).
                setLargeur(1000).
                setHauteur(1000).
                retourneScene();

        window.setScene(scene);
        return scene;
    }

    private void handle(KeyEvent e, Controleur controleur){
        controleur.move(e.getCode());
        actualise();

    }

    class ActionMove implements EventHandler<KeyEvent>{
        public void handle(KeyEvent e){
            if(Controleur.getControleur().commandePlateau().exec()!=null){
                Controleur.getControleur().move(e.getCode());
                actualise();
            }
        }
    }


}
