package transfert;

import definitionsComposants.TransfertComposite;

public class Main {

	public static void main(String[] args) {
		TransfertComposite.Component t = (new TransfertCompositeImpl()).newComponent();
		t.unServiceTransfer().transmettre();
	}

}
