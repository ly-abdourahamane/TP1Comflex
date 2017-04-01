package cryptage;

import definitionsComposants.TransfertCompositeCrypte;

public class Main {
	public static void main(String [] args) {
		TransfertCompositeCrypte.Component c = (new TransfertCompositeCrypteImpl()).newComponent();
		c.unServiceTransfertCrypte().transmettre();
	}
}
