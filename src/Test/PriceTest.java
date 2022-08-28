package Test;

import lombok.SneakyThrows;
import main.exceptions.PriceFormatException;
import main.model.Price;
import main.service.PriceUpdater;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;


public class PriceTest {

    @SneakyThrows
    @Test
    public void checkValidPrices() {
        Set<Price> oldPrices = new HashSet<>();
        Set<Price> newPrices = new HashSet<>();
        Set<Price> resultPrices = new HashSet<>();
        oldPrices.add(new Price("122856", 1, 1,
                LocalDateTime.of(2013, 1, 1, 0, 0, 0),
                LocalDateTime.of(2013, 1, 31, 23, 59, 59), 11000));

        oldPrices.add(new Price("122856", 2, 1,
                LocalDateTime.of(2013, 1, 10, 0, 0, 0, 0),
                LocalDateTime.of(2013, 1, 20, 23, 59, 59), 99000));

        oldPrices.add(new Price("6654", 1, 2,
                LocalDateTime.of(2013, 1, 1, 0, 0, 0),
                LocalDateTime.of(2013, 1, 31, 0, 0, 0), 5000));

        newPrices.add(new Price("122856", 1, 1,
                LocalDateTime.of(2013, 1, 20, 0, 0, 0, 0),
                LocalDateTime.of(2013, 2, 20, 23, 59, 59), 11000));

        newPrices.add(new Price("122856", 2, 1,
                LocalDateTime.of(2013, 1, 15, 0, 0, 0, 0),
                LocalDateTime.of(2013, 1, 25, 23, 59, 59), 92000));

        newPrices.add(new Price("6654", 1, 2,
                LocalDateTime.of(2013, 1, 12, 0, 0, 0, 0),
                LocalDateTime.of(2013, 1, 13, 0, 0, 0), 4000));

        resultPrices.add(new Price("122856", 1, 1,
                LocalDateTime.of(2013, 1, 1, 0, 0, 0),
                LocalDateTime.of(2013, 2, 20, 23, 59, 59), 11000));

        resultPrices.add(new Price("122856", 2, 1,
                LocalDateTime.of(2013, 1, 10, 0, 0, 0),
                LocalDateTime.of(2013, 1, 14, 23, 59, 59), 99000));

        resultPrices.add(new Price("122856", 2, 1,
                LocalDateTime.of(2013, 1, 15, 0, 0, 0),
                LocalDateTime.of(2013, 1, 25, 23, 59, 59), 92000));

        resultPrices.add(new Price("6654", 1, 2,
                LocalDateTime.of(2013, 1, 1, 0, 0, 0),
                LocalDateTime.of(2013, 1, 11, 23, 59, 59), 5000));

        resultPrices.add(new Price("6654", 1, 2,
                LocalDateTime.of(2013, 1, 12, 0, 0, 0),
                LocalDateTime.of(2013, 1, 13, 0, 0, 0), 4000));

        resultPrices.add(new Price("6654", 1, 2,
                LocalDateTime.of(2013, 1, 13, 0, 0, 1),
                LocalDateTime.of(2013, 1, 31, 0, 0, 0), 5000));

        Set<Price> resultFromMethod = PriceUpdater.updatePrices(newPrices, oldPrices);
        assertTrue(resultPrices.size() == resultFromMethod.size() && resultPrices.containsAll(resultFromMethod) && resultFromMethod.containsAll(resultPrices));

    }

    @SneakyThrows
    @Test
    public void checkEmptyPricesSet() {
        Set<Price> oldPrices = new HashSet<>();
        Set<Price> newPrices = new HashSet<>();
        Set<Price> resultPrices = new HashSet<>();

        oldPrices.add(new Price("122856", 1, 1,
                LocalDateTime.of(2013, 1, 1, 0, 0, 0),
                LocalDateTime.of(2013, 1, 31, 23, 59, 59), 11000));

        oldPrices.add(new Price("122856", 2, 1,
                LocalDateTime.of(2013, 1, 10, 0, 0, 0, 0),
                LocalDateTime.of(2013, 1, 20, 23, 59, 59), 99000));

        oldPrices.add(new Price("6654", 1, 2,
                LocalDateTime.of(2013, 1, 1, 0, 0, 0),
                LocalDateTime.of(2013, 1, 31, 0, 0, 0), 5000));

        resultPrices.add(new Price("122856", 1, 1,
                LocalDateTime.of(2013, 1, 1, 0, 0, 0),
                LocalDateTime.of(2013, 1, 31, 23, 59, 59), 11000));

        resultPrices.add(new Price("122856", 2, 1,
                LocalDateTime.of(2013, 1, 10, 0, 0, 0, 0),
                LocalDateTime.of(2013, 1, 20, 23, 59, 59), 99000));

        resultPrices.add(new Price("6654", 1, 2,
                LocalDateTime.of(2013, 1, 1, 0, 0, 0),
                LocalDateTime.of(2013, 1, 31, 0, 0, 0), 5000));

        Set<Price> resultFromMethod = PriceUpdater.updatePrices(newPrices, oldPrices);
        assertTrue(resultPrices.size() == resultFromMethod.size() && resultPrices.containsAll(resultFromMethod) && resultFromMethod.containsAll(resultPrices));

        newPrices.addAll(oldPrices);
        oldPrices = new HashSet<>();
        resultFromMethod = PriceUpdater.updatePrices(newPrices, oldPrices);
        assertTrue(resultPrices.size() == resultFromMethod.size() && resultPrices.containsAll(resultFromMethod) && resultFromMethod.containsAll(resultPrices));

    }

    @Test
    public void checkNullPrice() {
        Set<Price> oldPrices = new HashSet<>();
        Set<Price> newPrices = new HashSet<>();

        oldPrices.add(new Price("122856", 1, 1,
                LocalDateTime.of(2013, 1, 1, 0, 0, 0),
                LocalDateTime.of(2013, 1, 31, 23, 59, 59), 11000));

        newPrices.add(null);

        try {
            Set<Price> resultFromMethod = PriceUpdater.updatePrices(newPrices, oldPrices);
            fail("Expected exception was not thrown");
        } catch (PriceFormatException e) {
            assertEquals(e.getClass(), PriceFormatException.class);
        }

    }

    @Test
    public void checkInvalidPrice() {
        Set<Price> oldPrices = new HashSet<>();
        Set<Price> newPrices = new HashSet<>();

        oldPrices.add(new Price("122856", 1, 1,
                LocalDateTime.of(2013, 1, 1, 0, 0, 0),
                LocalDateTime.of(2013, 1, 31, 23, 59, 59), 11000));

        newPrices.add(new Price("122856", 1, 1,
                LocalDateTime.of(2013, 1, 20, 0, 0, 0, 0),
                LocalDateTime.of(2013, 2, 20, 23, 59, 59), -50));

        try {
            Set<Price> resultFromMethod = PriceUpdater.updatePrices(newPrices, oldPrices);
            fail("Expected exception was not thrown");
        } catch (PriceFormatException e) {
            assertEquals(e.getClass(), PriceFormatException.class);
        }
    }

    @Test
    public void checkInvalidDate() {
        Set<Price> oldPrices = new HashSet<>();
        Set<Price> newPrices = new HashSet<>();

        oldPrices.add(new Price("122856", 1, 1,
                LocalDateTime.of(2013, 1, 1, 0, 0, 0),
                LocalDateTime.of(2013, 1, 31, 23, 59, 59), 11000));

        newPrices.add(new Price("122856", 1, 1,
                LocalDateTime.of(2013, 5, 20, 0, 0, 0, 0),
                LocalDateTime.of(2013, 2, 20, 23, 59, 59), 50));

        try {
            Set<Price> resultFromMethod = PriceUpdater.updatePrices(newPrices, oldPrices);
            fail("Expected exception was not thrown");
        } catch (PriceFormatException e) {
            assertEquals(e.getClass(), PriceFormatException.class);
        }
    }


}

