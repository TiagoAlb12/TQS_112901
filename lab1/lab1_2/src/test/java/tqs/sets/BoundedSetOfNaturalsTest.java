package tqs.sets;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

/**
 * Test cases for BoundedSetOfNaturals
 * @author ico0
 */
class BoundedSetOfNaturalsTest {
    private BoundedSetOfNaturals setA;
    private BoundedSetOfNaturals setB;
    private BoundedSetOfNaturals setC;

    @BeforeEach
    public void setUp() {
        setA = new BoundedSetOfNaturals(1);
        setB = BoundedSetOfNaturals.fromArray(new int[]{10, 20, 30, 40, 50, 60});
        setC = BoundedSetOfNaturals.fromArray(new int[]{50, 60});
    }

    @AfterEach
    public void tearDown() {
        setA = setB = setC = null;
    }

    @DisplayName("Test adding elements to the set")
    @Test
    public void testAddElement() {
        setA = new BoundedSetOfNaturals(2);

        setA.add(99);
        assertTrue(setA.contains(99), "add: added element not found in set.");
        assertEquals(1, setA.size());

        setA.add(88);
        assertTrue(setA.contains(88), "add: second added element not found in set.");
        assertEquals(2, setA.size());
    }

    @DisplayName("Test adding from an invalid array")
    @Test
    public void testAddFromBadArray() {
        BoundedSetOfNaturals set = new BoundedSetOfNaturals(5);

        int[] elems = new int[]{10, -20, -30};

        assertThrows(IllegalArgumentException.class, () -> set.add(elems));
    }

    @DisplayName("Test adding elements beyond the limit")
    @Test
    public void testBoundedSetLimit() {
        BoundedSetOfNaturals set = new BoundedSetOfNaturals(3);

        set.add(1);
        set.add(2);
        set.add(3);

        assertThrows(IllegalStateException.class, () -> set.add(4), "Set should not allow more elements than its limit.");
    }

    @DisplayName("Test that duplicate numbers are not added")
    @Test
    public void testNoDuplicateNumbers() {
        BoundedSetOfNaturals set = new BoundedSetOfNaturals(5);

        set.add(10);
        assertThrows(IllegalArgumentException.class, () -> set.add(10), "Should not allow duplicate values.");

        assertEquals(1, set.size(), "Set size should remain 1 after duplicate attempt.");
    }

    @DisplayName("Test intersection between two sets")
    @Test
    public void testIntersects() {
        BoundedSetOfNaturals set1 = BoundedSetOfNaturals.fromArray(new int[]{1, 2, 3, 4, 5});
        BoundedSetOfNaturals set2 = BoundedSetOfNaturals.fromArray(new int[]{3, 4, 5, 6, 7});
        BoundedSetOfNaturals set3 = BoundedSetOfNaturals.fromArray(new int[]{8, 9, 10});

        assertTrue(set1.intersects(set2), "intersects: sets should have common elements {3, 4, 5}");
        assertFalse(set1.intersects(set3), "intersects: sets should have no common elements");
    }

    @DisplayName("Test intersection when both sets are empty")
    @Test
    public void testIntersectsEmptySets() {
        BoundedSetOfNaturals emptySet1 = new BoundedSetOfNaturals(5);
        BoundedSetOfNaturals emptySet2 = new BoundedSetOfNaturals(5);

        assertFalse(emptySet1.intersects(emptySet2), "Empty sets should not intersect.");
    }
}
