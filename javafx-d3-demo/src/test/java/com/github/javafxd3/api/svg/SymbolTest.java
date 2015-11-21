package com.github.javafxd3.api.svg;

import com.github.javafxd3.api.AbstractTestCase;
import com.github.javafxd3.api.D3;
import com.github.javafxd3.api.functions.ConstantDatumFunction;
import com.github.javafxd3.api.svg.Symbol.Type;
import com.github.javafxd3.api.svg.datumfunction.IndexDatumFunction;

@SuppressWarnings("javadoc")
public class SymbolTest extends AbstractTestCase {

	

	@Override	
	public void doTest() {
		
		D3 d3 = new D3(webEngine);
		
		// check the symbol types match the Symbol.Type.values
		String[] types = symbolTypes();
		for (int i = 0; i < types.length; i++) {
			String type = types[i];
			System.out.println("SYMBOL TYPE: " + type);
			Type typeEnum = Symbol.Type.valueOf(type.toUpperCase().replace('-',
					'_'));
			if (typeEnum == null) {
				fail("the symbol type " + type + " is not implemented");
			}
		}

		Symbol symbol = d3.svg().symbol();
		symbol.size(32);
		symbol.size(new IndexDatumFunction());

		symbol.type(Type.CIRCLE);
		symbol.type(new ConstantDatumFunction<Type>(Type.CIRCLE));
	}
	
	/**
	 * Return the list of supported symbol types, which match the
	 * {@link Type#values()}.
	 * <p>
	 * 
	 * @return the list of supported symbol types
	 */
	public String[] symbolTypes(){
		
		D3 d3 = new D3(webEngine);
		Object result = d3.eval("this.svg.symbolTypes");
		String resultString = result.toString();
		String[] symbolTypes = resultString.split(",");
		return symbolTypes;
		
	};

}
