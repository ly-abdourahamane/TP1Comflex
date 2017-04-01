package multiple;

import interfaces.InterfaceServer;
import definitionsComposants.ComposantPont;

public class PontImpl extends ComposantPont{

	@Override
	protected InterfaceServer make_unServicePont() {
		// TODO Auto-generated method stub
		return new InterfaceServer() {
			@Override
			public void recevoir(String donnee) {
				System.out.println("[Pont] message reçu = "+donnee);
				requires().premierService().recevoir(donnee);
				requires().secondService().recevoir(donnee);
				requires().troisiemeService().recevoir(donnee);
			}
		};
	}

}
