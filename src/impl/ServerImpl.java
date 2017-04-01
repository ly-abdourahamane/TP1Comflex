package impl;

import definitionsComposants.ComposantServer;
import interfaces.InterfaceBasique;

public class ServerImpl extends ComposantServer{
	@Override
	protected InterfaceBasique make_unServiceSimple() {
		// TODO Auto-generated method stub
		return new InterfaceBasique() {
			
			@Override
			public int calculBasique(int donnee) {
				System.out.println("Server: Sevice Basique donnee = "+ donnee);
				donnee = donnee+2;
				System.out.println("Server: Service Basique resultat calcul = "+donnee);
				return donnee;
			}
		};
	}
	

}
