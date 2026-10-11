package edu.unisabana.dyas.notas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class ConceptoTest {

    private final CalculadoraNotas calculadora = new CalculadoraNotas();

    // Cada frontera se prueba por los dos lados: el valor exacto y una décima por debajo.
    // Así, si alguien cambia un >= por > (o mueve un límite), alguna de estas pruebas falla.
    @ParameterizedTest(name = "{0} -> {1}")
    @CsvSource({
            "5.0, Excelente",
            "4.5, Excelente",
            "4.4, Sobresaliente",
            "4.0, Sobresaliente",
            "3.9, Aprobado",
            "3.0, Aprobado",
            "2.9, Reprobado",
            "0.0, Reprobado"
    })
    void asignaElConceptoSegunLaDefinitiva(double definitiva, String esperado) {
        assertEquals(esperado, calculadora.concepto(definitiva));
    }

    @ParameterizedTest
    @ValueSource(doubles = {-0.1, 5.1})
    void rechazaDefinitivasFueraDeRango(double definitiva) {
        IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
                () -> calculadora.concepto(definitiva));
        assertEquals("La nota del definitiva debe estar entre 0.0 y 5.0, pero fue " + definitiva,
                error.getMessage());
    }
}
