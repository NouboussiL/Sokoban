package sample;

import javafx.scene.input.KeyCode;

public interface Modele {

    void move(KeyCode keyCode);
    char[][] getPlateau();
    void setPlateau(char[][] plateau);
    int[] getPosition();
    void setPosition(int[] position);
    int[] getDonnees();
    void setDonnees(int[] donnees);

    void initialiser(char[][] plateau,int[] position, int[] donnees);


}
