/**
* 
* The MIT License (MIT)
*
* Copyright (c) 2016 emanuelbrici
*
* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:
*
* The above copyright notice and this permission notice shall be included in all
* copies or substantial portions of the Software.
*
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
* SOFTWARE.
*
**/

/**
 * Created by Emanuel Brici on 4/5/14.
 */

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Test;


public class BSTTests {

	@Test 
	public void checkConstructor() {
		BST<String> t = new BST<String>();
		TestCase.assertNotNull(t);
		TestCase.assertNull(t.getRoot());
	}
	
	@Test
	public void addOneString() {
		BST<String> t = new BST<String>();
		t.add("Hello");
		TestCase.assertNotNull(t.getRoot());
		TestCase.assertNull(t.getRoot().getParent());
		TestCase.assertEquals("Hello", t.getRoot().getData());
	}
	
	@Test
	public void addTwoStrings() {
		BST<String> t = new BST<String>();
		t.add("Hello");
		t.add("Kitty");
		TestCase.assertNotNull(t.getRoot());
		TestCase.assertEquals("Hello", t.getRoot().getData());
		TestCase.assertEquals("Kitty", t.getRoot().getRight().getData());
		TestCase.assertNull(t.getRoot().getLeft());
		TestCase.assertNull(t.getRoot().getRight().getRight());
		TestCase.assertEquals(t.getRoot(), t.getRoot().getRight().getParent());
	}

	@Test
	public void addThreeStrings() {
		BST<String> t = new BST<String>();
		t.add("Hello");
		t.add("Crazy");
		t.add("Pikachu");
		TestCase.assertNotNull(t.getRoot());
		TestCase.assertEquals("Hello", t.getRoot().getData());
		TestCase.assertEquals("Pikachu", t.getRoot().getRight().getData());
		TestCase.assertEquals("Crazy", t.getRoot().getLeft().getData());
		TestCase.assertNull(t.getRoot().getRight().getRight());
		TestCase.assertNull(t.getRoot().getRight().getLeft());
		TestCase.assertNull(t.getRoot().getLeft().getRight());
		TestCase.assertNull(t.getRoot().getLeft().getLeft());
		TestCase.assertEquals(t.getRoot(), t.getRoot().getRight().getParent());
		TestCase.assertEquals(t.getRoot(), t.getRoot().getLeft().getParent());
	}
	
	@Test
	public void checkAddingEqualsStrings() {
		BST<String> t = new BST<String>();
		t.add("Hello");
		t.add("Hello");
		TestCase.assertNotNull(t.getRoot().getRight());
	}
	
	@Test
	public void checkMinimumMaximum() {
		BST<Integer> bsti = new BST<Integer>();
		
		for(int i = 0; i < 100; i++) {
			bsti.add(i);
		}
		TestCase.assertEquals(new Integer(0), bsti.minimum().getData());
		TestCase.assertEquals(new Integer(99), bsti.maximum().getData());
		}

	@Test
	public void checkMaximumMinimum() {
		BST<Integer> bsti = new BST<Integer>();

		for(int i = 99; i >= 0; i--) {
			bsti.add(i);
		}
		TestCase.assertEquals(new Integer(0), bsti.minimum().getData());
		TestCase.assertEquals(new Integer(99), bsti.maximum().getData());
	}
	
	@Test
	public void checkRemoveSimple() {
		BST<Integer> bsti = new BST<Integer>();
		BST.Node<Integer> ni = bsti.remove(10);
		TestCase.assertNull(ni);
	}

	@Test
	public void checkRemoveOnlyNode() {
		BST<Integer> bsti = new BST<Integer>();
		bsti.add(10);
		BST.Node<Integer> ni = bsti.remove(10);
		TestCase.assertNotNull(ni);
	}

	@Test
	public void checkRemoveLeftNode() {
		BST<Integer> bsti = new BST<Integer>();
		bsti.add(10);
		bsti.add(3);
		BST.Node<Integer> ni = bsti.remove(3);
		TestCase.assertNotNull(ni);
		TestCase.assertEquals(new Integer(10), bsti.getRoot().getData());
		TestCase.assertNull(bsti.getRoot().getLeft());
		TestCase.assertNull(bsti.getRoot().getRight());
	}

	@Test
	public void checkRemoveRightNode() {
		BST<Integer> bsti = new BST<Integer>();
		bsti.add(10);
		bsti.add(20);
		BST.Node<Integer> ni = bsti.remove(20);
		TestCase.assertNotNull(ni);
		TestCase.assertEquals(new Integer(10), bsti.getRoot().getData());
		TestCase.assertNull(bsti.getRoot().getLeft());
		TestCase.assertNull(bsti.getRoot().getRight());
	}

	@Test
	public void checkRemoveWithTwoChildren() {
		BST<Integer> bsti = new BST<Integer>();
		bsti.add(10);
		bsti.add(20);
		bsti.add(30);
		bsti.add(15);
		
		TestCase.assertEquals(new Integer(10), bsti.getRoot().getData());
		TestCase.assertEquals(new Integer(20), bsti.getRoot().getRight().getData());
		BST.Node<Integer> ni = bsti.remove(20);
		TestCase.assertNotNull(ni);
		TestCase.assertEquals(new Integer(10), bsti.getRoot().getData());
		TestCase.assertNull(bsti.getRoot().getLeft());
		TestCase.assertEquals(new Integer(30), bsti.getRoot().getRight().getData());
	}

	@Test
	public void checkRemoveWithTwoChildren2() {
		BST<Integer> bsti = new BST<Integer>();
		bsti.add(10);
		bsti.add(20);
		bsti.add(30);
		bsti.add(28);
		bsti.add(15);
		
		TestCase.assertEquals(new Integer(10), bsti.getRoot().getData());
		TestCase.assertEquals(new Integer(20), bsti.getRoot().getRight().getData());
		BST.Node<Integer> ni = bsti.remove(20);
		TestCase.assertNotNull(ni);
		TestCase.assertEquals(new Integer(10), bsti.getRoot().getData());
		TestCase.assertNull(bsti.getRoot().getLeft());
		TestCase.assertEquals(new Integer(28), bsti.getRoot().getRight().getData());
	}

}

