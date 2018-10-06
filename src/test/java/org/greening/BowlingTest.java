package org.greening;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.greening.Framer;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BowlingTest {
    private static final Logger logger = LogManager.getLogger(BowlingTest.class);

    public class MutableInteger {
        public int value;

        public MutableInteger() {
            value = 0;
        }
    }
    MutableInteger score = new MutableInteger();

    @Test
    void myFirstTest() {
        assertEquals(2, 1 + 1);
    }

    void tester(Flux<Integer> input, int expectedScore) {
        score.value = 0;
        Framer framer = new Framer();
        framer.subscribe((Frame x) -> {logger.trace(x); score.value += x.score();});
        input.subscribe(framer);
        logger.trace("Total: " + score.value);
        assertEquals(score.value,expectedScore);
    }

    @Test
    void oneFrameTest() {
        tester(Flux.just(0, 0),0);
    }

    @Test
    void twoFrameTest() {
        tester(Flux.just(0, 0, 0, 0), 0);
    }

    @Test
    void spareTest() {
        tester(Flux.just(5, 5, 7, 0), 24);
    }

    @Test
    void strikeTest1() {
        tester(Flux.just(10, 0, 0), 10);
    }
    @Test
    void strikeTest2() {
        tester(Flux.just(10, 10, 0, 0), 30);
    }
    @Test
    void strikeTest3() {
        tester(Flux.just(10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 0, 0), 330);
    }
}
