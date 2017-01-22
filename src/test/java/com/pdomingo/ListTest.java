package com.pdomingo;

import com.pdomingo.exceptions.IndexOutOfBoundsException;
import com.pdomingo.implementations.list.ArrayList;
import com.pdomingo.implementations.list.LinkedList;
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

	private List<String> testList;
	private Class<List<String>> listClass;

	public ListTest(Class<List<String>> list) {
		this.listClass = list;
	}

	@Parameterized.Parameters
	public static Collection<Class> instancesToTest() {
		return Arrays.asList(ArrayList.class, LinkedList.class); // Revisar http://stackoverflow.com/a/6724555/3385498
	}

	@Before
	public void reset() {
		try {
			this.testList = listClass.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void shouldCreateEmptyList() {
		assertTrue(testList.size() == 0);
		assertTrue(testList.isEmpty());
	}

	/* --------- ADD TESTS --------- */

	@Test
	public void shouldAddItem() {

		String obj = "item1";
		testList.add(obj);

		assertTrue(testList.size() == 1);
		assertEquals(obj, testList.get(0));
	}

	@Test
	public void shouldAddIterable() {

		String item1 = "item1";
		String item2 = "item2";
		String item3 = "item3";

		Iterable<String> iterObj = () ->
				new Iterator<String>() {

					String[] data = new String[]{item1, item2, item3};
					int idx = 0;

					@Override
					public boolean hasNext() {
						return idx < data.length;
					}

					@Override
					public String next() {
						return data[idx++];
					}
				};

		testList.addAll(iterObj);

		assertTrue(testList.size() == 3);
		assertEquals(item1, testList.get(0));
		assertEquals(item2, testList.get(1));
		assertEquals(item3, testList.get(2));
	}

	@Test
	public void shouldGetItem() {

		String item1 = "item1";
		String item2 = "item2";
		String item3 = "item3";

		testList.add(item1);
		testList.add(item2);
		testList.add(item3);

		assertTrue(testList.size() == 3);
		assertEquals(item1, testList.get(0));
		assertEquals(item2, testList.get(1));
		assertEquals(item3, testList.get(2));
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void shouldThrowIndexOutOfBoundsOnGet() {
		testList.get(0);
	}

	/* --------- PUT TESTS --------- */

	@Test
	public void shouldPutItem() {

		String item1 = "item1";
		String item2 = "item2";

		testList.add(item1);

		assertTrue(testList.size() == 1);
		assertEquals(item1, testList.get(0));

		testList.put(item2, 0);

		assertTrue(testList.size() == 1);
		assertEquals(item2, testList.get(0));
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void shouldThrowIndexOutOfBoundsOnPut() {
		testList.put("", 0);
	}

	/* --------- REMOVE TESTS --------- */

	@Test
	public void shouldRemoveItemByIndex() {

		String item1 = "item1";
		String item2 = "item2";
		String item3 = "item3";
		String removedItem;

		testList.add(item1);
		testList.add(item2);
		testList.add(item3);

		assertTrue(testList.size() == 3);

		removedItem = testList.remove(2);

		assertEquals(removedItem, item3);
		assertTrue(testList.size() == 2);

		removedItem = testList.remove(0);

		assertEquals(removedItem, item1);
		assertTrue(testList.size() == 1);

		removedItem = testList.remove(0);

		assertEquals(removedItem, item2);
		assertTrue(testList.size() == 0);

		assertTrue(testList.isEmpty());
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void shouldThrowIndexOutOfBoundsOnRemove() {
		testList.remove(0);
	}

	@Test
	public void shouldRemoveItemByReference() {

		String item1 = "item1";
		String item2 = "item2";
		String item3 = "item3";

		testList.add(item1);
		testList.add(item2);
		testList.add(item3);

		assertTrue(testList.size() == 3);

		// Pre [item1, item2, item3]
		assertTrue(testList.remove(item2));
		assertTrue(testList.size() == 2);
		assertEquals(testList.get(0), item1);
		assertEquals(testList.get(1), item3);
		// Pos [item1, item3]

		// Pre [item1, item3]
		assertTrue(testList.remove(item1));
		assertTrue(testList.size() == 1);
		assertEquals(testList.get(0), item3);
		// Pos [item3]

		// Pre [item3]
		assertTrue(testList.remove(item3));
		assertTrue(testList.isEmpty());
		// Pos []
	}

	@Test
	public void shouldNotRemoveItemByReference() {

		String item1 = "item1";
		String item2 = "item2";

		testList.add(item1);

		// Pre [item1]
		assertFalse(testList.remove(item2));
		assertFalse(testList.isEmpty());
		assertEquals(testList.get(0), item1);
		// Pos [item1]
	}

	@Test
	public void shouldRemoveAllItemsByReference() {

		String item1 = "item1";
		String item2 = "item2";
		String item3 = "item3";

		testList.add(item1);
		testList.add(item2);
		testList.add(item3);

		// Pre [item1, item2, item3]
		testList.removeAll(Arrays.asList(item1, item3));
		assertEquals(testList.get(0), item2);
		assertTrue(testList.size() == 1);
		// Pos [item2]

		// Pre [item2]
		testList.removeAll(Arrays.asList(item1, item2));
		assertTrue(testList.isEmpty());
		// Pos []
	}

	@Test
	public void shouldNotRemoveAllItemsByReference() {

		String item1 = "item1";
		String item2 = "item2";
		String item3 = "item3";

		testList.add(item1);

		// Pre [item1]
		testList.removeAll(Arrays.asList(item2, item3));
		assertTrue(testList.size() == 1);
		// Pos [item1]
	}

	/* --------- CLEAR TESTS --------- */

	@Test
	public void shouldClearList() {

		String item1 = "item1";
		String item2 = "item2";
		String item3 = "item3";

		testList.add(item1);
		testList.add(item2);
		testList.add(item3);

		// Pre [item1, item2, item3]
		testList.clear();
		assertTrue(testList.isEmpty());
		// Pos []
	}

	@Test
	public void shouldLeftListEmpty() {

		// Pre []
		testList.clear();
		assertTrue(testList.isEmpty());
		// Pos []
	}

	/* --------- CONTAINS TESTS --------- */

	@Test
	public void shouldContainItems() {

		String item1 = "item1";
		String item2 = "item2";
		String item3 = "item3";

		testList.add(item1);
		testList.add(item2);
		testList.add(item3);

		// Pre [item1, item2, item3]
		assertTrue(testList.contains(item2));
		assertTrue(testList.contains(item3));
		assertTrue(testList.contains(item1));

		assertFalse(testList.contains("item4"));
		assertFalse(testList.contains("item5"));
		testList.remove(item2);
		assertFalse(testList.contains("item2"));
		// Pos [item1, item3]
	}

	@Test
	public void shouldSuccess() {

		String item1 = "item1";
		String item2 = "item2";
		String item3 = "item3";
		String item4 = "item4";
		String item5 = "item5";
		String item6 = "item6";
		String item7 = "item7";
		String item8 = "item8";

		// Pre []
		testList.add(item1);
		testList.add(item2);
		// Pos [item1, item2]

		// Pre [item1, item2]
		testList.remove(0);
		// Pos [item2]

		// Pre [item2]
		testList.addAll(Arrays.asList(item4, item3));
		// Pos [item2, item4, item3]

		// Pre [item2, item4, item3]
		testList.remove(item4);
		// Pos [item2, item3]

		// Pre [item2, item3]
		testList.add(item5);
		testList.add(item7);
		// Pos [item2, item3, item5, item7]

		// Pre [item2, item3, item5, item7]
		testList.removeAll(Arrays.asList(item5));
		// Pos [item2, item3, item7]

		// Pre [item2, item3, item7]
		testList.add(item6);
		testList.remove(2);
		// Pos [item2, item3, item6]

		// Pre [item2, item3, item6]
		testList.add(item8);
		testList.remove(1);
		// Pos [item2, item6, item8]

		assertTrue(testList.size() == 3);
		assertFalse(testList.isEmpty());
		assertEquals(testList.get(0), item2);
		assertEquals(testList.get(1), item6);
		assertEquals(testList.get(2), item8);
	}
}
