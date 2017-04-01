package transfert;

import interfaces.InterfaceServer;
import definitionsComposants.ComposantServerTransfert;

public class ServerTransfertImpl extends ComposantServerTransfert{
	private static int id = 1;
	@Override
	protected InterfaceServer make_unService() {
		// TODO Auto-generated method stub
		return new InterfaceServer() {
			
			@Override
			public void recevoir(String donnee) {
				System.out.println("[server "+(id++)+"] "+donnee+" World!");
			}
		};
	}

}
