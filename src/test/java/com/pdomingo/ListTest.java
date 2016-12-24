package com.pdomingo;

import com.pdomingo.exceptions.IndexOutOfBoundsException;
import com.pdomingo.implementations.ArrayList;
import com.pdomingo.implementations.LinkedList;
import com.pdomingo.interfaces.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Pablo on 24/12/16.
 */
@RunWith(Parameterized.class)
public class ListTest {

	public List<Object> testList;

	public ListTest(List<Object> list) {
		this.testList = list;
	}

	@Parameterized.Parameters
	public static Collection<Object[]> instancesToTest() {
		return Arrays.asList(
				new Object[]{new ArrayList<>()},
				new Object[]{new LinkedList<>()} // Revisar http://stackoverflow.com/a/6724555/3385498
		);
	}

	@Test
	public void shouldCreateEmptyList() {
		assertTrue(testList.size() == 0);
		assertTrue(testList.isEmpty());
	}

	@Test
	public void shouldAddItem() {

		Object obj = new Object();
		testList.add(obj);

		assertTrue(testList.size() == 1);
		assertEquals(obj, testList.get(0));
	}

	@Test
	public void shouldAddIterable() {

		Object obj1 = new Object();
		Object obj2 = new Object();
		Object obj3 = new Object();

		Iterable<Object> iterObj = new Iterable<Object>() {
			@Override
			public Iterator<Object> iterator() {
				return new Iterator<Object>() {

					Object[] data = new Object[]{ obj1, obj2, obj3 };
					int idx = 0;

					@Override
					public boolean hasNext() {
						return idx < data.length;
					}

					@Override
					public Object next() {
						return data[idx++];
					}
				};
			}
		};
		testList.addAll(iterObj);

		assertTrue(testList.size() == 3);
		assertEquals(obj1, testList.get(0));
		assertEquals(obj2, testList.get(1));
		assertEquals(obj3, testList.get(2));
	}

	@Test
	public void shouldGetItem() {

		Object obj1 = new Object();
		Object obj2 = new Object();
		Object obj3 = new Object();

		testList.add(obj1);
		testList.add(obj2);
		testList.add(obj3);

		assertTrue(testList.size() == 3);
		assertEquals(obj1, testList.get(0));
		assertEquals(obj2, testList.get(1));
		assertEquals(obj3, testList.get(2));
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void shouldThrowIndexOutOfBoundsOnGet() {
		testList.get(0);
	}

	/* --------- PUT TESTS --------- */

	@Test
	public void shouldPutItem() {

		Object obj1 = new Object();
		Object obj2 = new Object();

		testList.add(obj1);

		assertTrue(testList.size() == 1);
		assertEquals(obj1, testList.get(0));

		testList.put(obj2, 0);

		assertTrue(testList.size() == 1);
		assertEquals(obj2, testList.get(0));
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void shouldThrowIndexOutOfBoundsOnPut() {
		testList.put(new Object(), 0);
	}

	/* --------- REMOVE TESTS --------- */

	@Test
	public void shouldRemoveItemByIndex() {

		Object obj1 = new Object();
		Object obj2 = new Object();
		Object obj3 = new Object();
		Object removedItem;

		testList.add(obj1);
		testList.add(obj2);
		testList.add(obj3);

		assertTrue(testList.size() == 3);

		removedItem = testList.remove(2);

		assertEquals(removedItem, obj3);
		assertTrue(testList.size() == 2);

		removedItem = testList.remove(0);

		assertEquals(removedItem, obj1);
		assertTrue(testList.size() == 1);

		removedItem = testList.remove(0);

		assertEquals(removedItem, obj2);
		assertTrue(testList.size() == 0);

		assertTrue(testList.isEmpty());
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void shouldThrowIndexOutOfBoundsOnRemove() {
		testList.remove(0);
	}

	@Test
	public void shouldRemoveItemByReference() {

		Object obj1 = new Object();
		Object obj2 = new Object();
		Object obj3 = new Object();

		testList.add(obj1);
		testList.add(obj2);
		testList.add(obj3);

		assertTrue(testList.size() == 3);

		// Pre [obj1, obj2, obj3]
		assertTrue(testList.remove(obj2));
		assertTrue(testList.size() == 2);
		assertEquals(testList.get(0), obj1);
		assertEquals(testList.get(1), obj2);
		// Pos [obj1, obj3]

		// Pre [obj1, obj3]
		assertTrue(testList.remove(obj1));
		assertTrue(testList.size() == 1);
		assertEquals(testList.get(0), obj3);
		// Pos [obj3]

		// Pre [obj3]
		assertTrue(testList.remove(obj3));
		assertTrue(testList.isEmpty());
		// Pos []
	}

	@Test
	public void shouldNotRemoveItemByReference() {

		Object obj1 = new Object();

		testList.add(obj1);

		// Pre [obj1]
		assertFalse(testList.remove(new Object()));
		assertFalse(testList.isEmpty());
		// Pos [obj1]
	}


	/* --------- REMOVE TESTS --------- */
}
