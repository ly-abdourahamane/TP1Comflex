package impl;

import definitionsComposants.ComposantClient;
import interfaces.InterfaceAmeliore;

public class ClientImpl extends ComposantClient{

	@Override
	protected InterfaceAmeliore make_unServiceAmeliore() {
		// TODO Auto-generated method stub
		return new InterfaceAmeliore() {
			@Override
			public int calculAmeliore(int donnee) {
				System.out.println("Client:Service Ameliore appel a service Basique donnee = "+donnee);
				donnee = requires().unServiceBasique().calculBasique(donnee);
				System.out.println("Client:Service Ameliore resultat service basique = "+donnee);
				donnee = donnee*10;
				System.out.println("Client:Service Ameliore resultat service ameliore = "+donnee);
				return donnee;
			}
		};
	}
	
}
