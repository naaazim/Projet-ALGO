package up.mi.projet;

public class AlgorithmeUtilitaires {
    public static boolean estCol(Graphe g, Etiquetage e){

        // Vérifier que l'étiquetage couvre tous les sommets
        if(e.getTaille() < g.getCompteur()){
            return false;
        }

        // Parcours de toutes les paires de sommets
        for(int i = 0; i < g.getCompteur(); i++){
            for(int j = 0; j < g.getCompteur(); j++){

                if(g.estAdjacent(i, j)){

                    // Conflit : même couleur -> coloration incorrecte
                    if(e.getCouleur(i) == e.getCouleur(j)){
                        return false;
                    }
                }
            }
        }
        return true;
    }

}
