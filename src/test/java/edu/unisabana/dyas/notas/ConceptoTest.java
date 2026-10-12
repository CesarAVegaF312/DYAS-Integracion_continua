package edu.unisabana.dyas.notas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ConceptoTest {

    private final CalculadoraNotas calculadora = new CalculadoraNotas();

    @ParameterizedTest
    @CsvSource({"5.0, Excelente", "4.5, Excelente", "4.0, Sobresaliente", "3.0, Aprobado", "2.9, Reprobado"})
    void clasificaLaDefinitiva(double nota, String esperado) {
        assertEquals(esperado, calculadora.concepto(nota));
    }

    @Test
    void rechazaDefinitivaFueraDeRango() {
        assertThrows(IllegalArgumentException.class, () -> calculadora.concepto(5.5));
    }
}