package cryptage;

import interfaces.InterfaceServer;
import definitionsComposants.ComposantCryptageTransfert;

public class CryptageTansfertImpl extends ComposantCryptageTransfert{

	@Override
	protected InterfaceServer make_serviceCryptage() {
		return new InterfaceServer() {
			@Override
			public void recevoir(String message) {
				//on recçoit on crypte et on transfer
				String result = message.toUpperCase();
				System.out.println("[Cryptage] reçu = "+message +" sortie = "+result);
				requires().unDecryptage().recevoir(result);
			}
		};
	}

}
