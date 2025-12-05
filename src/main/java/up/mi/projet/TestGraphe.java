package up.mi.projet;

public class TestGraphe{
    public static void main(String[] args){
        try{
            // =======================
            // 1) GRAPHE DE PETERSEN
            // =======================
            Graphe g = new Graphe(10);
            for(int i = 0; i < 10; i++){
                g.ajouterSommet(new Sommet(i));
            }

            // Arêtes du graphe (3-régulier)
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

            System.out.println("=== Graphe de Petersen construit ===\n");


            // =======================
            // 2) TEST COLORATION A 2 COULEURS (GRAPHE g2)
            // =======================
            System.out.println("=== Test coloration 2-colorable (g2) ===");
            Graphe g2 = new Graphe(6);
            for (int i = 0; i < 6; i++){
                g2.ajouterSommet(new Sommet(i));
            }

            g2.ajouterArrete(0, 3);
            g2.ajouterArrete(0, 4);
            g2.ajouterArrete(0, 5);
            g2.ajouterArrete(1, 3);
            g2.ajouterArrete(1, 4);
            g2.ajouterArrete(1, 5);
            g2.ajouterArrete(2, 3);
            g2.ajouterArrete(2, 4);
            g2.ajouterArrete(2, 5);

            Etiquetage e2 = AlgorithmesUtilitaires.deuxCol(g2);
            System.out.println("Coloration 2-col :");
            e2.afficher();


            // =======================
            // 3) TEST GLOUTON AVEC ORDRE num1
            // =======================
            System.out.println("\n=== Test glouton sur le graphe de Petersen ===");
            int[] num1 = {1,3,4,0,2,6,5,9,8,7};
            Etiquetage colGlouton = AlgorithmesUtilitaires.glouton(g, num1);
            colGlouton.afficher();


            // =======================
            // 4) TEST Welsh–Powell
            // =======================
            System.out.println("\n=== Test Welsh–Powell ===");
            Etiquetage colWP = AlgorithmesUtilitaires.welshPowell(g);
            colWP.afficher();


            // =======================
            // 5) TEST triDegre
            // =======================
            System.out.println("\n=== Tri par degrés (Petersen = 3-régulier) ===");
            int []tabDeg = AlgorithmesUtilitaires.triDegre(g);
            for(int i = 0; i < tabDeg.length; i++){
                System.out.println("Sommet " + tabDeg[i] + " : degré = " + g.getDegre(tabDeg[i]));
            }


            // =======================
            // 6) TEST sous_graphe : voisins du sommet 0
            // =======================
            System.out.println("\n=== Test sous-graphe induit par les voisins du sommet 0 ===");

            int[] voisins0 = {2, 5, 7};  // voisins de 0 dans TON graphe
            Graphe sous0 = AlgorithmesUtilitaires.sousGraphe(g, voisins0);

            System.out.println("Sous-graphe induit par {4, 5, 6} :");
            sous0.afficher();
            // =======================
            // 7) TEST voisins_non_colories & degre_non_colories
            // =======================
            System.out.println("\n=== Test voisins_non_colories et degre_non_colories ===");

            // On part d'un étiquetage vide (tous AUCUNE)
            Etiquetage etiqTest = new Etiquetage(10);

            // Colorions quelques sommets manuellement pour tester
            etiqTest.setCouleur(4, Couleur.ROUGE);
            etiqTest.setCouleur(6, Couleur.VERT);

            System.out.println("Voisins non coloriés du sommet 0 :");
            int[] vn = AlgorithmesUtilitaires.voisinsNonColories(g, etiqTest, 0);
            for (int t : vn) {
                System.out.println("    - Sommet " + t);
            }

            int degNC = AlgorithmesUtilitaires.degreNonColories(g, etiqTest, 0);
            System.out.println("Degré non colorié du sommet 0 = " + degNC);



        } catch (Exception e){
            System.out.println("Erreur : " + e.getMessage());
        }

    }
}
