package sample;

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
    Rectangle[][] plateauImages;


    Image[] images = new Image[]{
        new Image(new FileInputStream("D:\\L3_Info\\COO\\Projet\\Sokoban\\images\\walter_white.jpeg"),50,50,false,false),
        new Image(new FileInputStream("D:\\L3_Info\\COO\\Projet\\Sokoban\\images\\varil_close.jpeg"),50,50,false,false),
        new Image(new FileInputStream("D:\\L3_Info\\COO\\Projet\\Sokoban\\images\\varil_open.jpg"),50,50,false,false),
        new Image(new FileInputStream("D:\\L3_Info\\COO\\Projet\\Sokoban\\images\\wall.jpg"),50,50,false,false),
        new Image(new FileInputStream("D:\\L3_Info\\COO\\Projet\\Sokoban\\images\\location.jpg"),50,50,false,false)
    };


    public VueIHMFX(Controleur controleur)throws FileNotFoundException{
        commandeGetPlateau = controleur.commandePlateau();
        plateauImages = new Rectangle[commandeGetPlateau.exec().length][commandeGetPlateau.exec()[0].length];
        for(int i = 0 ; i< commandeGetPlateau.exec().length;i++){
            for(int j = 0; j < commandeGetPlateau.exec()[0].length;j++){
                plateauImages[i][j]= new Rectangle(50,50);
                switch (commandeGetPlateau.exec()[i][j]){
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
                gridPane.add(plateauImages[i][j],i,j);
            }
        }
        dessine();
    }



    public void dessine(){
        for(int i = 0 ; i< commandeGetPlateau.exec().length;i++) {
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
            }
        }
    }


}
