package lt.andriaus.hangman.kata;

import lt.andriaus.hangman.kata.Arge;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ArgeTest {
    @Test
    void shouldCalculate() {
        List<Integer> expected = Arrays.asList(3, 15, 10, 94);
        List<Integer> received = Arrays.asList(
                Arge.nbYear(1000, 2, 50, 1200),
                Arge.nbYear(1500, 5, 100, 5000),
                Arge.nbYear(1500000, 2.5, 10000, 2000000),
                Arge.nbYear(1500000, 0.25, 1000, 2000000)
        );

        assertThat(received.get(0)).isEqualTo(expected.get(0));
        assertThat(received.get(1)).isEqualTo(expected.get(1));
        assertThat(received.get(2)).isEqualTo(expected.get(2));
        assertThat(received.get(3)).isEqualTo(expected.get(3));
    }
}