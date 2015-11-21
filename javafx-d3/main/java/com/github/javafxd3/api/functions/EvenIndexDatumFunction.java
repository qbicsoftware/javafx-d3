package com.github.javafxd3.api.functions;

import com.github.javafxd3.api.functions.DatumFunction;

/**
 * A DatumFunction that returns true if the index is even
 */
public class EvenIndexDatumFunction implements DatumFunction<Boolean> {
	

	//#region METHODS

	@Override
	public Boolean apply(Object context, Object datum, int index) {		
		boolean isEven =  (index % 2) == 0;
		return isEven;
	}
	
	//#end region

}
