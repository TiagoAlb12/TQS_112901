package tqs.euromillions;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import tqs.euromillions.CuponEuromillions;
import tqs.euromillions.Dip;
import tqs.euromillions.EuromillionsDraw;

public class EuromillionsDrawTest {

    private CuponEuromillions sampleCoupon;

    @BeforeEach
    public void setUp()  {
        sampleCoupon = new CuponEuromillions();
        sampleCoupon.appendDip(Dip.generateRandomDip());
        sampleCoupon.appendDip(Dip.generateRandomDip());
        sampleCoupon.appendDip(new Dip(new int[]{1, 2, 3, 48, 49}, new int[]{1, 9}));
    }


    @DisplayName("reports correct matches in a coupon")
    @Test
    public void testCompareBetWithDrawToGetResults() {
        Dip winningDip, matchesFound;

        // test for full match, using the 3rd dip in the coupon as the Draw results
        winningDip = sampleCoupon.getDipByIndex(2);
        EuromillionsDraw testDraw = new EuromillionsDraw(winningDip);
        matchesFound = testDraw.findMatchesFor(sampleCoupon).get(2);

        assertEquals(winningDip, matchesFound, "expected the bet and the matches found to be equal");

        // test for no matches at all
        testDraw = new EuromillionsDraw(new Dip(new int[]{9, 10, 11, 12, 13}, new int[]{2, 3}));
        matchesFound = testDraw.findMatchesFor(sampleCoupon).get(2);
        // compare empty with the matches found
        assertEquals( new Dip(), matchesFound);
    }

    @DisplayName("Generate a random Euromillions draw")
    @Test
    public void testGenerateRandomDraw() {
        EuromillionsDraw draw = EuromillionsDraw.generateRandomDraw();
        assertNotNull(draw, "Random draw should not be null.");
        assertEquals(5, draw.getDrawResults().getNumbersColl().size(), "Draw should have exactly 5 numbers.");
        assertEquals(2, draw.getDrawResults().getStarsColl().size(), "Draw should have exactly 2 stars.");
    }

    @DisplayName("Find matches in an empty coupon")
    @Test
    public void testFindMatchesEmptyCoupon() {
        CuponEuromillions emptyCoupon = new CuponEuromillions();
        EuromillionsDraw draw = new EuromillionsDraw(Dip.generateRandomDip());

        assertTrue(draw.findMatchesFor(emptyCoupon).isEmpty(), "Finding matches in an empty coupon should return an empty list.");
    }

    @DisplayName("Find partial matches in a draw")
    @Test
    public void testFindPartialMatches() {
        CuponEuromillions coupon = new CuponEuromillions();
        coupon.appendDip(new Dip(new int[]{5, 10, 15, 20, 25}, new int[]{3, 4})); // Aposta
        EuromillionsDraw draw = new EuromillionsDraw(new Dip(new int[]{5, 10, 30, 40, 50}, new int[]{3, 7})); // Sorteio

        Dip matches = draw.findMatchesFor(coupon).get(0);

        assertEquals(2, matches.getNumbersColl().size(), "Should match exactly 2 numbers.");
        assertEquals(1, matches.getStarsColl().size(), "Should match exactly 1 star.");
    }

    @DisplayName("Test draw with a null coupon")
    @Test
    public void testFindMatchesWithNullCoupon() {
        EuromillionsDraw draw = new EuromillionsDraw(Dip.generateRandomDip());
        assertThrows(NullPointerException.class, () -> draw.findMatchesFor(null), "Should throw exception when passing a null coupon.");
    }

}
