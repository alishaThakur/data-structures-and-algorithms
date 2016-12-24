package com.pdomingo;

import com.pdomingo.implementations.ArrayList;
import com.pdomingo.interfaces.List;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */
public class ArrayListTest {

	@Test
	public void shoudAddItem() {
		List<String> arrayList = new ArrayList<>();

		String str = "test";
		arrayList.add(str);

		assertTrue(arrayList.size() == 1);
		assertEquals(str, arrayList.get(0));
	}

	@Test
	public void shoudAddIterable() {

		List<String> arrayList = new ArrayList<>();

		String str1 = "test1", str2 = "test2", str3 = "test3";
		Iterable<String> iterStr = new Iterable<String>() {
			@Override
			public Iterator<String> iterator() {
				return new Iterator<String>() {

					String[] data = new String[]{ str1, str2, str3 };
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
			}
		};
		arrayList.addAll(iterStr);

		assertTrue(arrayList.size() == 3);
		assertEquals(str1, arrayList.get(0));
		assertEquals(str2, arrayList.get(1));
		assertEquals(str3, arrayList.get(2));
	}
}
