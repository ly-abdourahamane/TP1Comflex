package transfert;

import definitionsComposants.ComposantClientTransfert;
import definitionsComposants.ComposantServerTransfert;
import definitionsComposants.TransfertComposite;

public class TransfertCompositeImpl extends TransfertComposite{

	@Override
	protected ComposantClientTransfert make_client() {
		return new ClientTransfertImpl();
	}

	@Override
	protected ComposantServerTransfert make_server() {
		return new ServerTransfertImpl();
	}
	
}
