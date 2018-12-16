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
        Scene scene = definirFenetre(primaryStage, vue.gridPane, controleurIHMFX,vue.text);

        scene.setOnKeyPressed(new ActionMove());

        primaryStage.setTitle("Sokoban Walter White");
        primaryStage.show();
    }

    @Override
    public void actualise(){
        Platform.runLater(() -> {
            vue.dessine();
        });




    }

    void lance(){launch(new String[]{});}

    private Scene definirFenetre(Stage window, Region node, ControleurIHMFX controleurIHMFX,Region node1){
        MonteurScene monteurScene = new MonteurScene();

        Scene scene = monteurScene.
                setCentre(node).
                ajoutBas(controleurIHMFX.select).
                ajoutBas(controleurIHMFX.undo).
                setLargeur(1000).
                setHauteur(1000).
                setHaut(node1).
                retourneScene();

        window.setScene(scene);
        return scene;
    }



    class ActionMove implements EventHandler<KeyEvent>{
        public void handle(KeyEvent e){
            if(Controleur.getControleur().commandePlateau().exec()!=null && Controleur.getControleur().getDonnees()[1]!=0){
                    Controleur.getControleur().move(e.getCode());

            }
        }
    }


}
