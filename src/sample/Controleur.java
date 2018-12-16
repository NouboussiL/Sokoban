package sample;

import javafx.scene.input.KeyCode;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Controleur implements Sujet{

    private static Controleur singleton;
    FacadeModele facadeModele;
    ArrayList<Observateur> observateurs = new ArrayList<>();

    public static Controleur getControleur(){
        if(singleton==null)
            singleton = new Controleur(new FacadeModele());
        return singleton;
    }

    private Controleur(FacadeModele facadeModele){
        this.facadeModele=facadeModele;
    }

    @Override
    public void abonne(Observateur observateur) {
        observateurs.add(observateur);
    }

    @Override
    public void notifie() {
        for(Observateur observateur:observateurs)
            observateur.actualise();
    }

    public void move(KeyCode key){
        facadeModele.move(key);
        notifie();

    }

    public CommandePlateau commandePlateau(){
        return () -> facadeModele.getPlateau();
    }

    public CommandeListe commandeListe(){return ()-> facadeModele.getListe();}

    public void setPosition(int[] pos){facadeModele.setPosition(pos);}
    public int[] getPosition(){return facadeModele.getPosition();}

    public void setDonnees(int[] donnees){facadeModele.setDonnees(donnees);}
    public int[] getDonnees(){return facadeModele.getDonnees();}

    public void setPlateau(char[][] plateau){facadeModele.setPlateau(plateau);}
    public char[][] getPlateau(){return facadeModele.getPlateau();}


    public void undo(){
        facadeModele.undo();
        notifie();
    }




    public void getPlato(String path) throws IOException {
        String nomFich = traiterChemin(path);
        if(nomFich.endsWith(".xsb")) {
            Object[] o = lireFichier(nomFich);
            generatePlato((String) o[2], (int) o[0], (int) o[1]);
            notifie();
        } else {
            System.out.println("Mauvais format");
        }

    }


    /**
     *
     * @param chemin l'adresse complet du fichier
     * @return return la fin du chemin
     */
    public String traiterChemin(String chemin){
        String[] ts = chemin.split("/");
        return ts[ts.length-2] + "/" + ts[ts.length-1];
    }


    /**
     * affiche le plato
     * @param plato plato du jeu sous forme tableau
     */
    public void afficherPlato(char[][] plato){
        for (int i = 0; i < plato.length; i++) {
            for (int j = 0; j < plato[0].length; j++) {
                System.out.print(plato[i][j]);
            }
            System.out.println();
        }
    }


    /**
     *
     * @param plato le string contenant le plato du jeu
     * @param h la hauteur du plato
     * @param l la largeur du plato
     * @return le tableau contenant le plato du jeu
     */
    public void generatePlato(String plato, int h, int l){
        char[][] grille = new char[h][l];
        int n=0;
        Scanner sc = new Scanner (plato);
        sc.useDelimiter("\n");
        for (int i = 0; i < h; i++) {
            String ligne = sc.next();
            int size = ligne.length();
            for (int j = 0; j < size; j++) {
                switch(ligne.charAt(j)){
                    case '@': setPosition(new int[]{i,j});
                        break;

                    case '$': n++;
                        break;

                    case '+': setPosition(new int[]{i,j});
                        break;
                }
                grille[i][j] = ligne.charAt(j);
            }
            for (int k = size; k < l; k++) {
                grille[i][k] = ' ';
            }
        }
        setDonnees(new int[]{0,n});
        setPlateau(grille);
    }


    /**
     * @param nomFich le nom du fichier qu'on veut charger
     * @return tableau d'objet contenant des informations utiles concernant le niveau du jeu
     * @throws FileNotFoundException evite l'erreur du à l'existance du fichier
     * @throws IOException evite l'erreur du à la saisie
     */
    public Object[] lireFichier(String nomFich) throws FileNotFoundException, IOException {
        Object[] o = new Object[5]; // Hauteur Largeur Plato Titre Auteur -- int int Str Str Str.
        try {
            FileReader fr = new FileReader(nomFich);
            BufferedReader br = new BufferedReader(fr);
            Scanner sc = new Scanner (br);
            sc.useDelimiter("\n");
            String plato="";
            int hauteur = 0;
            int largeur = 0;
            while(sc.hasNext()){
                String ligne = sc.next();
                if ( (ligne.charAt(0) == '#') || (ligne.charAt(0) == ' ') ){
                    hauteur++;
                    plato += ligne;
                    plato += "\n";
                    if (ligne.length() > largeur) {
                        largeur = ligne.length();
                    }
                } else {
                    String info = "";
                    StringTokenizer st = new StringTokenizer(ligne);
                    String token = st.nextToken();
                    if (ligne.charAt(0) == 'A'){
                        while (st.hasMoreTokens()){
                            token = st.nextToken();
                            info += token + " ";
                        }
                        o[4] = info;
                    }
                    if ( ligne.charAt(0) == 'T' ) {
                        while (st.hasMoreTokens()) {
                            token = st.nextToken();
                            info += token + " ";
                        }
                        o[3] = info;
                    }
                }
            }
            o[0] = hauteur;
            o[1] = largeur;
            o[2] = plato;
            return o;
        } catch (FileNotFoundException e){
            return null;
        }
    }


    /**
     * @param dossier le chemin du dossier
     * @return return liste des fichiers contenus dans ce dossier
     * @throws IOException evite toute sorte d'erreur du à la saisie
     */
    public List<String> extraireFichier(String dossier) throws IOException {
        List<String> listPath = new ArrayList<>();
        List<String> listPathTrie = new ArrayList<>();
        DirectoryStream<Path> d;
        d = Files.newDirectoryStream(Paths.get(dossier), path -> path.toString().endsWith(".xsb"));
        for (Path p : d) {
            listPath.add(p.toString());
        }
        Collections.sort(listPath);
        int size = listPath.size();
        String[] ts;
        for (int i = 0; i < size ; i++){
            String ch = listPath.get(i);
            ts = ch.split("/");
            String s = ts[ts.length-2] + "/" + ts[ts.length-1];
            listPathTrie.add(s);
        }
        return listPathTrie;
    }

}

