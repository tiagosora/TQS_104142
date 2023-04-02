/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tqs.sets;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

/**
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

    // @Disabled("TODO revise test logic")
    @Test
    public void testAddElement() {
        setA.add(99);
        assertTrue(setA.contains(99), "add: added element not found in set.");
        assertEquals(1, setA.size());
        assertThrows(IllegalArgumentException.class, () -> setB.add(11));
    }

    // @Disabled("TODO revise to test the construction from invalid arrays")
    @Test
    public void testAddFromBadArray() {
        int[] elems = new int[]{10, -20, -30};
        assertThrows(IllegalArgumentException.class, () -> BoundedSetOfNaturals.fromArray(elems));
    }

    @Test
    @DisplayName("Assert if set is initially empty.")
    public void testSetIsInitiallyEmpty() {
        assertTrue(setA.isEmpty());
    }

    @DisplayName("Throws IllegalArgumentException, if adding a number to the full set")
    @Test
    public void testAddToFullSet() {
        assertTrue(setA.isEmpty());
        setA.add(1);
        assertThrows(IllegalArgumentException.class, () -> setA.add(2));
    }

    @DisplayName("Throws IllegalArgumentException, if adding a non positive number to the set")
    @Test
    public void testAddNonPositive() {
        assertTrue(setA.isEmpty());
        assertThrows(IllegalArgumentException.class, () -> setA.add(0));
        assertTrue(setA.isEmpty());
        assertThrows(IllegalArgumentException.class, () -> setA.add(-1));
    }

    @DisplayName("Throws IllegalArgumentException, if adding a duplicate number to the set")
    @Test
    public void testAddDuplicate() {
        assertTrue(setC.contains(50));
        assertThrows(IllegalArgumentException.class, () -> setB.add(10));
    }

    @DisplayName("Assert if two sets intersect")
    @Test
    public void testIntersection() {
        assertTrue(setB.intersects(BoundedSetOfNaturals.fromArray(new int[]{20, 30, 40})));
        assertFalse(setB.intersects(BoundedSetOfNaturals.fromArray(new int[]{20, 30, 45})));
        assertFalse(setC.intersects(setB));
        assertTrue(setB.intersects(setC));
    }
}