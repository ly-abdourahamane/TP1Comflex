package main;

import impl.ComposantCompositeImpl;
import definitionsComposants.ComposantComposite;

public class Exercice2 {
	
	public static void main(String []args) {
		int a = 5;
		ComposantComposite.Component c = (new ComposantCompositeImpl()).newComponent();
		a = c.unServiceComplet().calculAmeliore(5);
		System.out.println(a);
	}
	
}
