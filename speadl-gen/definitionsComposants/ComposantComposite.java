package definitionsComposants;

import definitionsComposants.ComposantClient;
import definitionsComposants.ComposantServer;
import interfaces.InterfaceAmeliore;
import interfaces.InterfaceBasique;

@SuppressWarnings("all")
public abstract class ComposantComposite {
  public interface Requires {
  }
  
  public interface Component extends ComposantComposite.Provides {
  }
  
  public interface Provides {
    /**
     * This can be called to access the provided port.
     * 
     */
    public InterfaceAmeliore unServiceComplet();
  }
  
  public interface Parts {
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public ComposantClient.Component client();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public ComposantServer.Component server();
  }
  
  public static class ComponentImpl implements ComposantComposite.Component, ComposantComposite.Parts {
    private final ComposantComposite.Requires bridge;
    
    private final ComposantComposite implementation;
    
    public void start() {
      assert this.client != null: "This is a bug.";
      ((ComposantClient.ComponentImpl) this.client).start();
      assert this.server != null: "This is a bug.";
      ((ComposantServer.ComponentImpl) this.server).start();
      this.implementation.start();
      this.implementation.started = true;
    }
    
    private void init_client() {
      assert this.client == null: "This is a bug.";
      assert this.implem_client == null: "This is a bug.";
      this.implem_client = this.implementation.make_client();
      if (this.implem_client == null) {
      	throw new RuntimeException("make_client() in definitionsComposants.ComposantComposite should not return null.");
      }
      this.client = this.implem_client._newComponent(new BridgeImpl_client(), false);
    }
    
    private void init_server() {
      assert this.server == null: "This is a bug.";
      assert this.implem_server == null: "This is a bug.";
      this.implem_server = this.implementation.make_server();
      if (this.implem_server == null) {
      	throw new RuntimeException("make_server() in definitionsComposants.ComposantComposite should not return null.");
      }
      this.server = this.implem_server._newComponent(new BridgeImpl_server(), false);
    }
    
    protected void initParts() {
      init_client();
      init_server();
    }
    
    protected void init_unServiceComplet() {
      // nothing to do here
    }
    
    protected void initProvidedPorts() {
      init_unServiceComplet();
    }
    
    public ComponentImpl(final ComposantComposite implem, final ComposantComposite.Requires b, final boolean doInits) {
      this.bridge = b;
      this.implementation = implem;
      
      assert implem.selfComponent == null: "This is a bug.";
      implem.selfComponent = this;
      
      // prevent them to be called twice if we are in
      // a specialized component: only the last of the
      // hierarchy will call them after everything is initialised
      if (doInits) {
      	initParts();
      	initProvidedPorts();
      }
    }
    
    public InterfaceAmeliore unServiceComplet() {
      return this.client().
      unServiceAmeliore()
      ;
    }
    
    private ComposantClient.Component client;
    
    private ComposantClient implem_client;
    
    private final class BridgeImpl_client implements ComposantClient.Requires {
      public final InterfaceBasique unServiceBasique() {
        return ComposantComposite.ComponentImpl.this.server().
        unServiceSimple()
        ;
      }
    }
    
    public final ComposantClient.Component client() {
      return this.client;
    }
    
    private ComposantServer.Component server;
    
    private ComposantServer implem_server;
    
    private final class BridgeImpl_server implements ComposantServer.Requires {
    }
    
    public final ComposantServer.Component server() {
      return this.server;
    }
  }
  
  /**
   * Used to check that two components are not created from the same implementation,
   * that the component has been started to call requires(), provides() and parts()
   * and that the component is not started by hand.
   * 
   */
  private boolean init = false;;
  
  /**
   * Used to check that the component is not started by hand.
   * 
   */
  private boolean started = false;;
  
  private ComposantComposite.ComponentImpl selfComponent;
  
  /**
   * Can be overridden by the implementation.
   * It will be called automatically after the component has been instantiated.
   * 
   */
  protected void start() {
    if (!this.init || this.started) {
    	throw new RuntimeException("start() should not be called by hand: to create a new component, use newComponent().");
    }
  }
  
  /**
   * This can be called by the implementation to access the provided ports.
   * 
   */
  protected ComposantComposite.Provides provides() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("provides() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if provides() is needed to initialise the component.");
    }
    return this.selfComponent;
  }
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected ComposantComposite.Requires requires() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("requires() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if requires() is needed to initialise the component.");
    }
    return this.selfComponent.bridge;
  }
  
  /**
   * This can be called by the implementation to access the parts and their provided ports.
   * 
   */
  protected ComposantComposite.Parts parts() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("parts() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if parts() is needed to initialise the component.");
    }
    return this.selfComponent;
  }
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract ComposantClient make_client();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract ComposantServer make_server();
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public synchronized ComposantComposite.Component _newComponent(final ComposantComposite.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of ComposantComposite has already been used to create a component, use another one.");
    }
    this.init = true;
    ComposantComposite.ComponentImpl  _comp = new ComposantComposite.ComponentImpl(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public ComposantComposite.Component newComponent() {
    return this._newComponent(new ComposantComposite.Requires() {}, true);
  }
}
