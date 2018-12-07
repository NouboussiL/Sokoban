package sample;

import javafx.scene.input.KeyCode;

public class ModeleConcret implements Modele{

    private char[][] plateau;
    private int[] position;
    private int[] donnees;

    @Override
    public void move(KeyCode keyCode) {
        switch(keyCode){
            case RIGHT:
                move(0,1);
                break;

            case LEFT:
                move(0,-1);
                break;

            case UP:
                move(-1,0);
                break;

            case DOWN:
                move(1,0);
                break;
        }
        System.out.println(donnees[0]+","+donnees[1]);
        System.out.println(position[0]+","+position[1]);
        Controleur.getControleur().afficherPlato(plateau);
    }


    private void move(int vertical, int horizontal) {
        int x = position[0];
        int y = position[1];

        boolean b = (y+2*horizontal<0)||(x+2*vertical<0)||(plateau.length<x+2*vertical)||(plateau[0].length<y+2*horizontal);
        boolean a = plateau[x+vertical][y+horizontal]=='#'?false:plateau[x + 2 * vertical][y + 2 * horizontal] != '#' && plateau[x + 2 * vertical][y + 2 * horizontal] !='$' &&
                plateau[x + 2 * vertical][y + 2 * horizontal]!='*';
        switch(plateau[x+vertical][y+horizontal]){
            case '#':
                break;

            case ' ':
                if(plateau[x][y]=='@')
                    plateau[x][y]=' ';
                else
                    plateau[x][y]='.';
                plateau[x+vertical][y+horizontal]='@';
                setPosWhileMove(vertical,horizontal);
                donnees[0]++;
                break;

            case '.':
                if(plateau[x][y]=='@')
                    plateau[x][y]=' ';
                else
                    plateau[x][y]='.';
                plateau[x+vertical][y+horizontal]='+';
                setPosWhileMove(vertical,horizontal);
                donnees[0]++;
                break;

            case '$':
                if(b)
                    break;
                else{
                    if(a) {
                        switch (plateau[x + 2 * vertical][y + 2 * horizontal]) {
                            case ' ':
                                plateau[x + 2 * vertical][y + 2 * horizontal] = '$';
                                break;

                            case '.':
                                plateau[x + 2 * vertical][y + 2 * horizontal] = '*';
                                donnees[1]--;
                                break;
                        }

                        if (plateau[x][y] == '@')
                            plateau[x][y] = ' ';
                        else
                            plateau[x][y] = '.';
                        plateau[x + vertical][y + horizontal] = '@';
                        setPosWhileMove(vertical, horizontal);
                        donnees[0]++;
                    }

                }
                break;

            case '*':
                if(b)
                    break;
                else {
                    if (a) {
                        if (plateau[x][y] == '@')
                            plateau[x][y] = ' ';
                        else
                            plateau[x][y] = '.';
                        plateau[x + vertical][y + horizontal] = '+';
                        setPosWhileMove(vertical, horizontal);
                        donnees[0]++;
                        switch (plateau[x + 2 * vertical][y + 2 * horizontal]) {
                            case ' ':
                                plateau[x + 2 * vertical][y + 2 * horizontal] = '$';
                                donnees[1]++;
                                break;

                            case '.':
                                plateau[x + 2 * vertical][y + 2 * horizontal] = '*';
                                break;
                        }
                    }
                }
                break;

        }
    }

    public char[][] getPlateau(){
        return plateau;
    }

    public void setPlateau(char[][] plateau){
        this.plateau = plateau;
    }

    public int[] getPosition(){
        return position;
    }

    public void setPosition(int[] position){
        this.position=position;
    }


    public int[] getDonnees(){
        return donnees;
    }

    public void setDonnees(int[] donnees) {
        this.donnees = donnees;
    }

    private void setPosWhileMove(int ver, int ho){
        if (ver != 0)
            position[0]+=ver;
        else
            position[1]+=ho;
    }
}
