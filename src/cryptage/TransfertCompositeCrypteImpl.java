package cryptage;

import transfert.ClientTransfertImpl;
import transfert.ServerTransfertImpl;
import definitionsComposants.ComposantClientTransfert;
import definitionsComposants.ComposantCryptageTransfert;
import definitionsComposants.ComposantDecryptageTansfert;
import definitionsComposants.ComposantServerTransfert;
import definitionsComposants.TransfertCompositeCrypte;

public class TransfertCompositeCrypteImpl extends TransfertCompositeCrypte{

	@Override
	protected ComposantClientTransfert make_client() {
		// TODO Auto-generated method stub
		return new ClientTransfertImpl();
	}

	@Override
	protected ComposantCryptageTransfert make_cryptage() {
		// TODO Auto-generated method stub
		return new CryptageTansfertImpl();
	}

	@Override
	protected ComposantDecryptageTansfert make_decryptage() {
		// TODO Auto-generated method stub
		return new DecryptageTransfertImpl();
	}

	@Override
	protected ComposantServerTransfert make_server() {
		// TODO Auto-generated method stub
		return new ServerTransfertImpl();
	}

	
}
