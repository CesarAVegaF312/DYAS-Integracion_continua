package edu.unisabana.dyas.notas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Casos que complementan NotaNecesariaTest: redondeo hacia arriba, límite de 5.0,
 * validación de entradas y coherencia con calcularDefinitiva.
 */
class NotaNecesariaBordesTest {

    private final CalculadoraNotas calculadora = new CalculadoraNotas();

    @Test
    void redondeaHaciaArribaYNoAlMasCercano() {
        // 3.8 y 3.5 acumulan 2.19; se necesitan 0.81 / 0.4 = 2.025.
        // Redondeando al más cercano daría 2.0; hacia arriba debe dar 2.1.
        assertEquals(2.1, calculadora.notaNecesariaTercerCorte(3.8, 3.5));
    }

    @Test
    void permiteNecesitarExactamenteCinco() {
        // 2.0 y 1.4 acumulan 1.02; se necesitan 1.98 / 0.4 = 4.95 -> 5.0: todavía es posible.
        assertEquals(5.0, calculadora.notaNecesariaTercerCorte(2.0, 1.4));
    }

    @Test
    void lanzaExcepcionApenasSeNecesitaMasDeCinco() {
        // 2.0 y 1.3 acumulan 0.99; se necesitan 2.01 / 0.4 = 5.025 -> 5.1: ya no es posible.
        IllegalStateException error = assertThrows(IllegalStateException.class,
                () -> calculadora.notaNecesariaTercerCorte(2.0, 1.3));
        assertTrue(error.getMessage().contains("5.1"), error.getMessage());
    }

    @ParameterizedTest(name = "corte1={0}, corte2={1} es inválido")
    @CsvSource({"-0.1, 3.0", "5.1, 3.0", "3.0, -0.1", "3.0, 5.1"})
    void validaLasNotasDeEntradaIgualQueLaDefinitiva(double c1, double c2) {
        assertThrows(IllegalArgumentException.class, () -> calculadora.notaNecesariaTercerCorte(c1, c2));
    }

    @ParameterizedTest(name = "con {0} y {1}, sacar la nota necesaria alcanza para aprobar")
    @CsvSource({"3.0, 3.0", "2.0, 2.0", "3.5, 2.8", "3.8, 3.5", "2.0, 1.4", "4.1, 0.0"})
    void laNotaNecesariaAlcanzaParaAprobar(double c1, double c2) {
        double necesaria = calculadora.notaNecesariaTercerCorte(c1, c2);

        assertTrue(calculadora.aprueba(calculadora.calcularDefinitiva(c1, c2, necesaria)));
    }
}
