package transfert;

import interfaces.InterfaceClient;
import definitionsComposants.ComposantClientTransfert;

public class ClientTransfertImpl extends ComposantClientTransfert{

	@Override
	protected InterfaceClient make_unTransfert() {
		// TODO Auto-generated method stub
		return new InterfaceClient() {
			
			@Override
			public void transmettre() {
				String donnee = "Hello";
				System.out.println("[Client] message =" +donnee);
				requires().unServer().recevoir(donnee);
			}
		};
	}

}
