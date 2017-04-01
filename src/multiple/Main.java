package multiple;

import definitionsComposants.TransfertMultiple;

public class Main {
	public static void main(String[] args) {
		TransfertMultiple.Component t = (new TransfertMultipleImpl()).newComponent();
		t.unTransfertMultiple().transmettre();;
	}
}
