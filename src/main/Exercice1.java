package main;

import definitionsComposants.ComposantSimple;
import impl.ComposantSimpleImpl;

public class Exercice1 {

	public static void main(String[] args) {
		ComposantSimple.Component comp = (new ComposantSimpleImpl()).newComponent();
		comp.monPortdeService().doSomething();

	}

}
