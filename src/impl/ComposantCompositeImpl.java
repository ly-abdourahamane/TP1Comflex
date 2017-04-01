package impl;

import definitionsComposants.ComposantClient;
import definitionsComposants.ComposantComposite;
import definitionsComposants.ComposantServer;

public class ComposantCompositeImpl extends ComposantComposite{

	@Override
	protected ComposantClient make_client() {
		return new ClientImpl();
	}

	@Override
	protected ComposantServer make_server() {
		return new ServerImpl();
	}
	
}
