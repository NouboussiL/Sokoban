package sample;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class VueIHMFX {

    CommandePlateau commandeGetPlateau;
    GridPane gridPane = new GridPane();
    Label text;
    Rectangle[][] plateauImages;


    Image[] images = new Image[]{
        new Image(new FileInputStream("images/walter_white.jpeg"),50,50,false,false),
        new Image(new FileInputStream("images/varil_close.jpeg"),50,50,false,false),
        new Image(new FileInputStream("images/varil_open.jpg"),50,50,false,false),
        new Image(new FileInputStream("images/wall.jpg"),50,50,false,false),
        new Image(new FileInputStream("images/location.jpg"),50,50,false,false)
    };


    public VueIHMFX(Controleur controleur)throws FileNotFoundException{
        commandeGetPlateau = controleur.commandePlateau();
        text = new Label();
        dessine();

    }



    public void dessine(){
        if(commandeGetPlateau.exec()!=null) {
                plateauImages = new Rectangle[commandeGetPlateau.exec().length][commandeGetPlateau.exec()[0].length];
                if (Controleur.getControleur().getDonnees()[1]!=0)
                    text.setText("Nombre de Coups : "+Controleur.getControleur().getDonnees()[0]);
                else
                    text.setText("Vous avez gagné en "+Controleur.getControleur().getDonnees()[0]+" coups.");

                remplirPlateauImages();
        }
    }

    private void remplirPlateauImages(){
        for (int i = 0; i < commandeGetPlateau.exec().length; i++)
            for (int j = 0; j < commandeGetPlateau.exec()[0].length; j++) {
                plateauImages[i][j] = new Rectangle(50, 50);
                switch (commandeGetPlateau.exec()[i][j]) {
                    case ' ':
                        plateauImages[i][j].setFill(Color.WHITE);
                        break;
                    case '#':
                        plateauImages[i][j].setFill(new ImagePattern(images[3]));
                        break;
                    case '*':
                        plateauImages[i][j].setFill(new ImagePattern(images[1]));
                        break;
                    case '$':
                        plateauImages[i][j].setFill(new ImagePattern(images[2]));
                        break;
                    case '@':
                        plateauImages[i][j].setFill(new ImagePattern(images[0]));
                        break;
                    case '+':
                        plateauImages[i][j].setFill(new ImagePattern(images[0]));
                        break;
                    case '.':
                        plateauImages[i][j].setFill(new ImagePattern(images[4]));
                        break;
                }
                    GridPane.setRowIndex(plateauImages[i][j], i);
                    GridPane.setColumnIndex(plateauImages[i][j], j);
                    gridPane.getChildren().add(plateauImages[i][j]);

            }
    }


    public void reinitialiserVue(){
        if(plateauImages!=null) {

            gridPane.getChildren().remove(0,gridPane.getChildren().size());
            plateauImages = null;
        }
    }

}
