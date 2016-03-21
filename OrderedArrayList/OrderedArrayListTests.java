import static org.junit.Assert.*;
import org.junit.Test; 

public class OrderedArrayListTests {
	
	@Test
	public void testCompareTo() {
		String a = "Zeno.";
		String b = "Abacus";
		//System.out.println("Compare value is : " + a.compareTo(b));
	}

	@Test
	public void construct() {
		OrderedArrayList<String> a = new OrderedArrayList<String>();
		assertEquals("Size should be 0 after construction!", 0, a.size());
	}
	@Test
	public void insertOneItem() {
		OrderedArrayList<String> a = new OrderedArrayList<String>();
		assertEquals("Size should be 0 after construction!", 0, a.size());
		a.insert("Hello!");
		assertEquals("Size should be 1 after one insert!", 1, a.size());
	}
	@Test
	public void findWithJustOneItem() {
		int i;
		OrderedArrayList<String> a = new OrderedArrayList<String>();
		a.insert("Hello!");
		i = a.find("Hello!");
		assertEquals("Can't properly find the only string that should be in the list!", 0, i );
		i = a.find("Hellooooooooo!");
		assertEquals("find() expecting -1, but got something else", -1, i );
	}
	@Test
	public void removeWithJustOneItem() {
		int i;
		OrderedArrayList<String> a = new OrderedArrayList<String>();
		a.insert("Hello!");
		i = a.find("Hello!");
		assertEquals("Can't properly find the only string that should be in the list!", 0, i );
		i = a.remove("Hel"+"lo!");
		assertEquals("remove() expecting 1, but got something else", 1, i );
	}
	@Test
	public void removeWithTwoDifferentItems() {
		int i;
		OrderedArrayList<String> a = new OrderedArrayList<String>();
		a.insert("Hello!");
		a.insert("Zeno.");
		i = a.find("Hello!");
		assertEquals("Can't properly find the only string that should be in the list!", 0, i );
		i = a.remove("Hel"+"lo!");
		assertEquals("remove() expecting 1, but got something else", 1, i );
	}
	@Test
	public void removeWhenThereAreDuplicates() {
		int i;
		OrderedArrayList<String> a = new OrderedArrayList<String>();
		a.insert("Hello!");
		a.insert("Hello!");
		a.insert("Hello!");
		a.insert("Zeno.");
		a.insert("Abacus");
		i = a.find("Hello!");
		assertEquals("find() :: doesn't find the first occurance!", 1, i );
		i = a.remove("Hel"+"lo!");
		assertEquals("remove() expecting 3, but got something else", 3, i );
	}
	@Test
	public void testCounting() {
		int i;
		
		OrderedArrayList<String> a = new OrderedArrayList<String>();
		a.insert("Hello!");
		a.insert("H"+"ello!");
		a.insert("Hell"+"o!");
		a.insert("Zeno.");
		a.insert("Abacus");
		assertEquals("size() is incorrect.", 5, a.size());
		assertEquals("insert() placed an item incorrectly or get() is broken", "Abacus", a.get(0));
		assertEquals("insert() placed an item incorrectly or get() is broken", "Hello!", a.get(1));
		assertEquals("insert() placed an item incorrectly or get() is broken", "Hello!", a.get(2));
		assertEquals("insert() placed an item incorrectly or get() is broken", "Hello!", a.get(3));
		assertEquals("insert() placed an item incorrectly or get() is broken", "Zeno.", a.get(4));
		assertEquals("countEquivalent() is broken or insert isn't working correctly.", 1, a.countEquivalent(0));
		assertEquals("countEquivalent() is broken.", 3, a.countEquivalent(1));
		assertEquals("countEquivalent() is broken.", 1, a.countEquivalent(4));
		
		i = a.find("Hello!");
		assertEquals("find() :: doesn't find the first occurance!", 1, i );
		i = a.remove("Hel"+"lo!");
		assertEquals("remove() expecting 3, but got something else", 3, i );
	}

}
