package multiple;

import transfert.ClientTransfertImpl;
import transfert.ServerTransfertImpl;
import definitionsComposants.ComposantClientTransfert;
import definitionsComposants.ComposantPont;
import definitionsComposants.ComposantServerTransfert;
import definitionsComposants.TransfertMultiple;

public class TransfertMultipleImpl extends TransfertMultiple{

	@Override
	protected ComposantClientTransfert make_client() {
		// TODO Auto-generated method stub
		return new ClientTransfertImpl();
	}

	@Override
	protected ComposantPont make_pont() {
		// TODO Auto-generated method stub
		return new PontImpl();
	}

	@Override
	protected ComposantServerTransfert make_server() {
		// TODO Auto-generated method stub
		return new ServerTransfertImpl();
	}

}
