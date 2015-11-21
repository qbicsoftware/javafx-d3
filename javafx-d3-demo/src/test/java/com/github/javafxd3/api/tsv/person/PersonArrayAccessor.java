package com.github.javafxd3.api.tsv.person;

import com.github.javafxd3.api.dsv.DsvArrayAccessor;

public class PersonArrayAccessor implements DsvArrayAccessor<Person> {
	@Override
	public Person parse(final String[] row, final int index) {
		return new Person(row[0], Integer.parseInt(row[1]));
	}
}
