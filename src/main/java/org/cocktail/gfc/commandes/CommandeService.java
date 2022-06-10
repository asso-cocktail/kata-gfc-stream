package org.cocktail.gfc.commandes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CommandeService {

    /*
     * Description du modèle métier :
     * - Un Exercice represente la période sur laquelle les opérations financières ont lieues.
     * - Une Commande represente un achat effectué par un service et contient un ensemble de Lignes de commande.
     */
    enum StatutExercice {
        OUVERT,
        RESTREINT,
        EN_PREPARATION,
        CLOS
    }
    record Exercice(Integer value, StatutExercice statut) {
        public boolean estOuvert() {
            return StatutExercice.OUVERT.equals(statut);
        }
    }
    record LigneCommande(String idLigne, String referenceArticle, Double quantite, BigDecimal prixUnitaire) {}
    record Commande(String numeroCommande, String referenceFournisseur, List<LigneCommande> lignes, LocalDateTime dateCreation) {
        BigDecimal getTotalHT() {
            return lignes.stream()
                    .map(l -> l.prixUnitaire.multiply(BigDecimal.valueOf(l.quantite)))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }
    }

    interface ExerciceRepository {
        List<Exercice> findAll();
    }

    interface CommandeRepository {
        List<Commande> findAll();
    }

    /*
     * La classe de service contient les différents cas d'utilisation
     */
    private final ExerciceRepository exerciceRepository;
    private final CommandeRepository commandeRepository;

    public CommandeService() {
        this.exerciceRepository = new InMemoryExerciceRepository();
        this.commandeRepository = new InMemoryCommandeRepository();
    }

    // ----------------------------
    // --------- LEVEL 1 ----------
    // ----------------------------
    /**
     * Retourne l'exercice dont le statut est "ouvert".
     * @return l'exercice sous forme d'entier (année) si  il en existe un au statut ouvert
     * sinon lance une exception IllegalArgumentException.
     */
    public Integer recupererExerciceOuvert() {
        return -1;
    }

    // ----------------------------
    // --------- LEVEL 2 ----------
    // ----------------------------
    /**
     * Retourne le montant total HT des Commandes pour les 3 derniers exercuces à partir de l'exercice de référence (inclus).
     * Pour l'exercice de référence 2022, nous attendons :
     * 2022 | 11.0
     * 2021 | 48.30
     * 2020 | 5
     * @param exerciceReference exercice de référence sous forme d'un entier
     * @return une Map dont la clé est l'exercice et la valeur le montant total HT des commandes associées à l'exercice.
     */
    public Map<Integer, BigDecimal> montantTotalHTSurTroisExercices(Integer exerciceReference) {
        return Map.of();
    }

    // ----------------------------
    // Classes internes
    // ----------------------------
    class InMemoryExerciceRepository implements ExerciceRepository {

        @Override
        public List<Exercice> findAll() {
            return List.of(
                    new Exercice(2023, StatutExercice.EN_PREPARATION),
                    new Exercice(2022, StatutExercice.OUVERT),
                    new Exercice(2021, StatutExercice.RESTREINT),
                    new Exercice(2020, StatutExercice.CLOS),
                    new Exercice(2019, StatutExercice.CLOS)
            );
        }
    }
    class InMemoryCommandeRepository implements CommandeRepository {
        @Override
        public List<Commande> findAll() {
            return fullSample();
        }

        private List<Commande> emptySample() {
            return List.of();
        }

        private List<Commande> fullSample() {
            LocalDateTime mockDate2022 = LocalDateTime.of(2022, 5, 24, 10, 45);
            LocalDateTime mockDate2021 = mockDate2022.minusYears(1L);
            LocalDateTime mockDate2020 = mockDate2022.minusYears(2L);
            LocalDateTime mockDate2019 = mockDate2022.minusYears(3L);
            LocalDateTime mockDate2018 = mockDate2022.minusYears(4L);

            LigneCommande cde1l1 = new LigneCommande("ligne11", "art11", 4d, BigDecimal.valueOf(2));
            LigneCommande cde1l2 = new LigneCommande("ligne12", "art12", 1d, BigDecimal.valueOf(3));
            Commande cde1 = new Commande("cde1", "fou1", List.of(cde1l1, cde1l2), mockDate2022);

            LigneCommande cde2l1 = new LigneCommande("ligne21", "art21", 1d, BigDecimal.valueOf(5));
            Commande cde2 = new Commande("cde2", "fou1", List.of(cde2l1), mockDate2021);

            LigneCommande cde3l1 = new LigneCommande("ligne31", "art31", 4d, BigDecimal.valueOf(12));
            LigneCommande cde3l2 = new LigneCommande("ligne32", "art32", 3d, BigDecimal.valueOf(0.1));
            Commande cde3 = new Commande("cde2", "fou2", List.of(cde3l1, cde3l2), mockDate2020);

            return List.of(cde1, cde2, cde3);
        }
    }
}
