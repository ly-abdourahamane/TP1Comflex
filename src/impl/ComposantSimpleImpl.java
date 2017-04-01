package impl;

import interfaces.InterfaceSimple;
import definitionsComposants.ComposantSimple;

public class ComposantSimpleImpl extends ComposantSimple{

	@Override
	protected InterfaceSimple make_monPortdeService() {
		
		return new InterfaceSimple() {	
			@Override
			public void doSomething() {
				System.out.println("My first component: ça marche");
				
			}
		};
	}

}
