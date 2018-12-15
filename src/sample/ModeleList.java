package sample;

import javafx.scene.input.KeyCode;

import java.util.ArrayList;
public class ModeleList implements Modele{

    Modele modele;

    ArrayList<Modele> liste;
    ArrayList<Modele> listeUndo;

    public ModeleList(Modele modele){
        this.modele = modele;
        this.liste=new ArrayList<>();
        liste.add(modele);
        this.listeUndo = new ArrayList<>();
    }


    @Override
    public void move(KeyCode keyCode) {
        modele.move(keyCode);
        liste.add(modele);
    }

    @Override
    public char[][] getPlateau() {
        return modele.getPlateau();
    }

    @Override
    public void setPlateau(char[][] plateau) {
        modele.setPlateau(plateau);
    }

    @Override
    public int[] getPosition() {
        return modele.getPosition();
    }

    @Override
    public void setPosition(int[] position) {
        modele.setPosition(position);
    }

    @Override
    public int[] getDonnees() {
        return modele.getDonnees();
    }

    @Override
    public void setDonnees(int[] donnees) {
        modele.setDonnees(donnees);
    }
}
