import static org.junit.Assert.*;

import org.junit.Test;


public class MoveToFrontListTests {

	@Test
	public void testConstruction() {
		MoveToFrontList l = new MoveToFrontList();
		assertNotNull(l);
	}
	@Test
	public void testFindOnEmptyList() {
		MoveToFrontList l = new MoveToFrontList();
		assertNull("I should't find anything in an empty list!", l.find(""));
	}
	@Test
	public void testSizeOnEmptyList() {
		MoveToFrontList l = new MoveToFrontList();
		assertEquals("Size of empty list should be 0", l.size(), 0);
	}
	@Test
	public void testRankWithNoItems() {
		MoveToFrontList l = new MoveToFrontList();
		assertEquals("Size of empty list should be 0", l.size(), 0);
		assertEquals("Check the rank for an empty list...", 
				l.rank("Hi"), 0);
	}
	@Test
	public void testAddOneItem() {
		MoveToFrontList l = new MoveToFrontList();
		l.incrementCount("Hi");
		assertEquals("Adding to the list with incrementCount is broken, or size() returns a bad value", 
				l.size(), 1);
	}
	@Test
	public void testRankWithOneItem() {
		MoveToFrontList l = new MoveToFrontList();
		l.incrementCount("Hi");
		assertEquals("Adding to the list with incrementCount is broken, or size() returns a bad value", 
				l.size(), 1);
		assertEquals("The rank of a your first item should be 0", 
				l.rank("Hi"), 0);
		assertEquals("The rank of a your first item should be 0", 
				l.rank("Hip"), 1);

	}
	
	@Test
	public void testRankWithTwoItems() {
		MoveToFrontList l = new MoveToFrontList();
		l.incrementCount("Hi");
		assertEquals("Adding to the list with incrementCount is broken, or size() returns a bad value", 
				l.size(), 1);
        l.incrementCount("World");
		assertEquals("Adding to the list with incrementCount is broken, or size() returns a bad value", 
				l.size(), 2);
		assertEquals("The rank of a your first item should be 0", 
				l.rank("World"), 0);
		assertEquals("The rank of a your first item should be 0", 
				l.rank("Hip"), 2);
	}
	
	@Test
	public void testSpliceOut() {
		MoveToFrontList l = new MoveToFrontList();
		l.incrementCount("Hi");
		assertEquals("Adding to the list with incrementCount is broken, or size() returns a bad value", 
				l.size(), 1);
		StringCountElement toSplicOut = l.find("Hi");
		l.spliceOut(toSplicOut);
		assertEquals("When splicing out wiht l containing 1 node, the size should be 0.",l.size(), 0);	
	}
		
	@Test
	public void testThreeSpliceIn() {
		MoveToFrontList l = new MoveToFrontList();
		l.incrementCount("Hi");
		assertEquals("Adding to the list with incrementCount is broken, or size() returns a bad value", 
				l.size(), 1);
        l.incrementCount("World");
		assertEquals("Adding to the list with incrementCount is broken, or size() returns a bad value", 
				l.size(), 2);
		l.incrementCount("Hello");
		assertEquals("Adding to the list with incrementCount is broken, or size() returns a bad value", 
				l.size(), 3);
		StringCountElement toSpliceIn = new StringCountElement();
		toSpliceIn.key = "spliceIn";
		toSpliceIn.count = 1;
		l.spliceIn(toSpliceIn, 1);
		assertEquals("SpliceIn should have rank 1", l.rank("spliceIn") , 1);
		StringCountElement key2 = new StringCountElement();
		key2.key = "key2";
		key2.count = 1;
		l.spliceIn(key2, 3);
		assertEquals("Key2 should be at rand 3", l.rank(key2.key), 3);
		l.incrementCount("Hi");
		assertEquals("Adding to the list with incrementCount is broken, or size() returns a bad value", 
				l.size(), 5);
		
	}
	
}
