package sample;

import javafx.scene.input.KeyCode;

import java.util.ArrayList;

public class FacadeModele {

    ModeleList modele = new ModeleList(new ModeleConcret());

    public void move(KeyCode key){modele.move(key);}

    public void setPosition(int[] pos){modele.setPosition(pos);}
    public int[] getPosition(){return modele.getPosition();}

    public void setDonnees(int[] donnees){modele.setDonnees(donnees);}
    public int[] getDonnees(){return modele.getDonnees();}

    public void setPlateau(char[][] plateau){modele.setPlateau(plateau);}
    public char[][] getPlateau(){return modele.getPlateau();}

    public ArrayList<Modele> getListe(){
        return modele.getListe();
    }

    public void undo(){
        modele.undo();
    }
}
