package up.mi.projet;

public class TestGraphe{
    public static void main(String[] args){
        try{
            Graphe g = new Graphe(10);
            for(int i = 0; i < 10; i++){
                g.ajouterSommet(new Sommet(i));
            }
            g.ajouterArrete(0, 6);
            g.ajouterArrete(0, 4);
            g.ajouterArrete(0, 5);

            g.ajouterArrete(1, 6);
            g.ajouterArrete(1, 7);
            g.ajouterArrete(1, 8);

            g.ajouterArrete(2, 5);
            g.ajouterArrete(2, 8);
            g.ajouterArrete(2, 9);

            g.ajouterArrete(3, 4);
            g.ajouterArrete(3, 7);
            g.ajouterArrete(3, 9);

            g.ajouterArrete(4, 8);

            g.ajouterArrete(5, 7);

            g.ajouterArrete(6, 9);


            Etiquetage correct = new Etiquetage(10);

            correct.setCouleur(0, Couleur.ROUGE);
            correct.setCouleur(1, Couleur.VERT);
            correct.setCouleur(2, Couleur.VERT);
            correct.setCouleur(3, Couleur.ROUGE);
            correct.setCouleur(4, Couleur.BLEU);
            correct.setCouleur(5, Couleur.VERT);
            correct.setCouleur(6, Couleur.ROUGE);
            correct.setCouleur(7, Couleur.BLEU);
            correct.setCouleur(8, Couleur.ROUGE);
            correct.setCouleur(9, Couleur.BLEU);

            //System.out.println("Coloration propre ? " + AlgorithmesUtilitaires.estCol(g, correct));
            Graphe g2 = new Graphe(6);
            g2.ajouterSommet(new Sommet(0));
            g2.ajouterSommet(new Sommet(1));
            g2.ajouterSommet(new Sommet(2));
            g2.ajouterSommet(new Sommet(3));
            g2.ajouterSommet(new Sommet(4));
            g2.ajouterSommet(new Sommet(5));
            
            g2.ajouterArrete(0, 3);
            g2.ajouterArrete(0, 4);
            g2.ajouterArrete(0, 5);
            g2.ajouterArrete(1,3);
            g2.ajouterArrete(1,4);
            g2.ajouterArrete(1,5);
            g2.ajouterArrete(2, 3);
            g2.ajouterArrete(2, 4);
            g2.ajouterArrete(2, 5);

            Etiquetage e = AlgorithmesUtilitaires.deuxCol(g2);
            e.afficher();
        }catch (NonBipartiException | TailleInsuffisanteException e){
            System.out.println(e.getMessage());
        }

    }
}