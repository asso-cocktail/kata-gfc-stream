package org.cocktail.gfc.commandes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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
    class Exercice {
        private Integer value;
        private StatutExercice statut;

        public Exercice(Integer value, StatutExercice statut) {
            this.value = value;
            this.statut = statut;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }

        public StatutExercice getStatut() {
            return statut;
        }

        public void setStatut(StatutExercice statut) {
            this.statut = statut;
        }

        public boolean estOuvert() {
            return StatutExercice.OUVERT.equals(statut);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Exercice exercice = (Exercice) o;
            return value.equals(exercice.value) && statut == exercice.statut;
        }

        @Override
        public int hashCode() {
            return Objects.hash(value, statut);
        }
    }
    class LigneCommande {
        private String idLigne;
        private String referenceArticle;
        private Double quantite;
        private BigDecimal prixUnitaire;

        public LigneCommande(String idLigne, String referenceArticle, Double quantite, BigDecimal prixUnitaire) {
            this.idLigne = idLigne;
            this.referenceArticle = referenceArticle;
            this.quantite = quantite;
            this.prixUnitaire = prixUnitaire;
        }

        public String getIdLigne() {
            return idLigne;
        }

        public void setIdLigne(String idLigne) {
            this.idLigne = idLigne;
        }

        public String getReferenceArticle() {
            return referenceArticle;
        }

        public void setReferenceArticle(String referenceArticle) {
            this.referenceArticle = referenceArticle;
        }

        public Double getQuantite() {
            return quantite;
        }

        public void setQuantite(Double quantite) {
            this.quantite = quantite;
        }

        public BigDecimal getPrixUnitaire() {
            return prixUnitaire;
        }

        public void setPrixUnitaire(BigDecimal prixUnitaire) {
            this.prixUnitaire = prixUnitaire;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            LigneCommande that = (LigneCommande) o;
            return idLigne.equals(that.idLigne) && referenceArticle.equals(that.referenceArticle) && quantite.equals(that.quantite) && prixUnitaire.equals(that.prixUnitaire);
        }

        @Override
        public int hashCode() {
            return Objects.hash(idLigne, referenceArticle, quantite, prixUnitaire);
        }
    }
    class Commande {
        private String numeroCommande;
        private String referenceFournisseur;
        private List<LigneCommande> lignes;
        private LocalDateTime dateCreation;

        public Commande(String numeroCommande, String referenceFournisseur, List<LigneCommande> lignes, LocalDateTime dateCreation) {
            this.numeroCommande = numeroCommande;
            this.referenceFournisseur = referenceFournisseur;
            this.lignes = lignes;
            this.dateCreation = dateCreation;
        }

        public String getNumeroCommande() {
            return numeroCommande;
        }

        public void setNumeroCommande(String numeroCommande) {
            this.numeroCommande = numeroCommande;
        }

        public String getReferenceFournisseur() {
            return referenceFournisseur;
        }

        public void setReferenceFournisseur(String referenceFournisseur) {
            this.referenceFournisseur = referenceFournisseur;
        }

        public List<LigneCommande> getLignes() {
            return lignes;
        }

        public void setLignes(List<LigneCommande> lignes) {
            this.lignes = lignes;
        }

        public LocalDateTime getDateCreation() {
            return dateCreation;
        }

        public void setDateCreation(LocalDateTime dateCreation) {
            this.dateCreation = dateCreation;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Commande commande = (Commande) o;
            return numeroCommande.equals(commande.numeroCommande) && referenceFournisseur.equals(commande.referenceFournisseur) && lignes.equals(commande.lignes) && dateCreation.equals(commande.dateCreation);
        }

        @Override
        public int hashCode() {
            return Objects.hash(numeroCommande, referenceFournisseur, lignes, dateCreation);
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

    // ----------------------------
    // --------- LEVEL 2 ----------
    // ----------------------------

    // ----------------------------
    // Classes internes
    // ----------------------------
    class InMemoryExerciceRepository implements ExerciceRepository {

        @Override
        public List<Exercice> findAll() {
            List<Exercice> exercices = new ArrayList<>();
            exercices.add(new Exercice(2023, StatutExercice.EN_PREPARATION));
            exercices.add(new Exercice(2022, StatutExercice.OUVERT));
            exercices.add(new Exercice(2021, StatutExercice.RESTREINT));
            exercices.add(new Exercice(2020, StatutExercice.CLOS));
            exercices.add(new Exercice(2019, StatutExercice.CLOS));
            return exercices;
        }
    }
    class InMemoryCommandeRepository implements CommandeRepository {
        @Override
        public List<Commande> findAll() {
            return fullSample();
        }

        private List<Commande> emptySample() {
            return new ArrayList<>();
        }

        private List<Commande> fullSample() {
            LocalDateTime mockDate2022 = LocalDateTime.of(2022, 5, 24, 10, 45);
            LocalDateTime mockDate2021 = mockDate2022.minusYears(1L);
            LocalDateTime mockDate2020 = mockDate2022.minusYears(2L);
            LocalDateTime mockDate2019 = mockDate2022.minusYears(3L);
            LocalDateTime mockDate2018 = mockDate2022.minusYears(4L);

            LigneCommande cde1l1 = new LigneCommande("ligne11", "art11", 4d, BigDecimal.valueOf(2));
            LigneCommande cde1l2 = new LigneCommande("ligne12", "art12", 1d, BigDecimal.valueOf(3));
            Commande cde1 = new Commande("cde1", "fou1", Arrays.asList(cde1l1, cde1l2), mockDate2022);

            LigneCommande cde2l1 = new LigneCommande("ligne21", "art21", 1d, BigDecimal.valueOf(5));
            Commande cde2 = new Commande("cde2", "fou1", Arrays.asList(cde2l1), mockDate2021);

            LigneCommande cde3l1 = new LigneCommande("ligne31", "art31", 4d, BigDecimal.valueOf(12));
            LigneCommande cde3l2 = new LigneCommande("ligne32", "art32", 3d, BigDecimal.valueOf(0.1));
            Commande cde3 = new Commande("cde2", "fou2", Arrays.asList(cde3l1, cde3l2), mockDate2020);

            return Arrays.asList(cde1, cde2, cde3);
        }
    }
}
