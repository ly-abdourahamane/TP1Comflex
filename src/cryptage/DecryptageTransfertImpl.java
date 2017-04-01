package cryptage;

import interfaces.InterfaceServer;
import definitionsComposants.ComposantDecryptageTansfert;

public class DecryptageTransfertImpl extends ComposantDecryptageTansfert{

	@Override
	protected InterfaceServer make_serviceDecryptage() {
		// TODO Auto-generated method stub
		return new InterfaceServer() {
			
			@Override
			public void recevoir(String message) {
				String result = message.toLowerCase();
				System.out.println("[Decryptage] reçu = "+message +" sortie = "+result);
				requires().unServerFinal().recevoir(result);
			}
		};
	}

}
