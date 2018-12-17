package sample;

import javafx.scene.input.KeyCode;

import java.util.ArrayList;
public class ModeleList implements Modele{

    private Modele modele;

    ArrayList<Modele> liste;
    ArrayList<Modele> listeUndo;

    public ModeleList(Modele modele){
        this.modele = modele;
        this.liste=new ArrayList<>();
        Modele mod = new ModeleConcret();
        mod.setPlateau(modele.getPlateau());
        mod.setDonnees(modele.getDonnees());
        mod.setPosition(modele.getPosition());
        liste.add(mod);
        this.listeUndo = new ArrayList<>();
    }

    public void initialiser(char[][] plateau, int[] position, int[] donnees){
        modele.initialiser(plateau,position,donnees);
        Modele mod = liste.get(liste.size()-1);
        copier(mod,modele);
    }


    private void copier(Modele mod1, Modele mod2){
        mod1.setPlateau(mod2.getPlateau());
        mod1.setPosition(mod2.getPosition());
        mod1.setDonnees(mod2.getDonnees());
    }


    @Override
    public void move(KeyCode keyCode) {
        modele.move(keyCode);
        Modele mod = new ModeleConcret();
        mod.setPlateau(modele.getPlateau());
        mod.setDonnees(modele.getDonnees());
        mod.setPosition(modele.getPosition());
        liste.add(mod);
        listeUndo.clear();
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


    public ArrayList<Modele> getListe(){
        return liste;
    }

    public void undo(){
        if(liste.size()>1){
            listeUndo.add(liste.get(liste.size()-1));
            liste.remove(liste.size()-1);
            Modele mod = liste.get(liste.size()-1);
            copier(modele,mod);
        }
    }

    public void redo(){
        if(listeUndo.size()!=0){
            Modele mod = listeUndo.get(listeUndo.size()-1);
            liste.add(mod);
            listeUndo.remove(listeUndo.size()-1);
            copier(modele,mod);
        }
    }

    public void reset(){
        if(liste.size()!=0){
            Modele mod = liste.get(0);
            copier(modele,mod);
            liste.clear();
            listeUndo.clear();
            liste.add(mod);
        }
    }
}
