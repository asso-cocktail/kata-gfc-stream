package org.cocktail.gfc.commandes;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CommandeServiceTest {
    
    @Test
    void testMontantTotalHT() {
        CommandeService service = new CommandeService();
        Map<Integer, BigDecimal> rapport = service.montantTotalHTSurTroisExercices(2022);

        assertEquals(3, rapport.size());
        assertEquals(new BigDecimal("11.0"), rapport.get(2022));
        assertEquals(new BigDecimal("5.0"), rapport.get(2021));
        assertEquals(new BigDecimal("48.30"), rapport.get(2020));
    }
}