package definitionsComposants;

import definitionsComposants.ComposantClientTransfert;
import definitionsComposants.ComposantPont;
import definitionsComposants.ComposantServerTransfert;
import interfaces.InterfaceClient;
import interfaces.InterfaceServer;

@SuppressWarnings("all")
public abstract class TransfertMultiple {
  public interface Requires {
  }
  
  public interface Component extends TransfertMultiple.Provides {
  }
  
  public interface Provides {
    /**
     * This can be called to access the provided port.
     * 
     */
    public InterfaceClient unTransfertMultiple();
  }
  
  public interface Parts {
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public ComposantClientTransfert.Component client();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public ComposantPont.Component pont();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public ComposantServerTransfert.Component server();
  }
  
  public static class ComponentImpl implements TransfertMultiple.Component, TransfertMultiple.Parts {
    private final TransfertMultiple.Requires bridge;
    
    private final TransfertMultiple implementation;
    
    public void start() {
      assert this.client != null: "This is a bug.";
      ((ComposantClientTransfert.ComponentImpl) this.client).start();
      assert this.pont != null: "This is a bug.";
      ((ComposantPont.ComponentImpl) this.pont).start();
      assert this.server != null: "This is a bug.";
      ((ComposantServerTransfert.ComponentImpl) this.server).start();
      this.implementation.start();
      this.implementation.started = true;
    }
    
    private void init_client() {
      assert this.client == null: "This is a bug.";
      assert this.implem_client == null: "This is a bug.";
      this.implem_client = this.implementation.make_client();
      if (this.implem_client == null) {
      	throw new RuntimeException("make_client() in definitionsComposants.TransfertMultiple should not return null.");
      }
      this.client = this.implem_client._newComponent(new BridgeImpl_client(), false);
    }
    
    private void init_pont() {
      assert this.pont == null: "This is a bug.";
      assert this.implem_pont == null: "This is a bug.";
      this.implem_pont = this.implementation.make_pont();
      if (this.implem_pont == null) {
      	throw new RuntimeException("make_pont() in definitionsComposants.TransfertMultiple should not return null.");
      }
      this.pont = this.implem_pont._newComponent(new BridgeImpl_pont(), false);
    }
    
    private void init_server() {
      assert this.server == null: "This is a bug.";
      assert this.implem_server == null: "This is a bug.";
      this.implem_server = this.implementation.make_server();
      if (this.implem_server == null) {
      	throw new RuntimeException("make_server() in definitionsComposants.TransfertMultiple should not return null.");
      }
      this.server = this.implem_server._newComponent(new BridgeImpl_server(), false);
    }
    
    protected void initParts() {
      init_client();
      init_pont();
      init_server();
    }
    
    protected void init_unTransfertMultiple() {
      // nothing to do here
    }
    
    protected void initProvidedPorts() {
      init_unTransfertMultiple();
    }
    
    public ComponentImpl(final TransfertMultiple implem, final TransfertMultiple.Requires b, final boolean doInits) {
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
    
    public InterfaceClient unTransfertMultiple() {
      return this.client().
      unTransfert()
      ;
    }
    
    private ComposantClientTransfert.Component client;
    
    private ComposantClientTransfert implem_client;
    
    private final class BridgeImpl_client implements ComposantClientTransfert.Requires {
      public final InterfaceServer unServer() {
        return TransfertMultiple.ComponentImpl.this.pont().
        unServicePont()
        ;
      }
    }
    
    public final ComposantClientTransfert.Component client() {
      return this.client;
    }
    
    private ComposantPont.Component pont;
    
    private ComposantPont implem_pont;
    
    private final class BridgeImpl_pont implements ComposantPont.Requires {
      public final InterfaceServer premierService() {
        return TransfertMultiple.ComponentImpl.this.server().
        unService()
        ;
      }
      
      public final InterfaceServer secondService() {
        return TransfertMultiple.ComponentImpl.this.server().
        unService()
        ;
      }
      
      public final InterfaceServer troisiemeService() {
        return TransfertMultiple.ComponentImpl.this.server().
        unService()
        ;
      }
    }
    
    public final ComposantPont.Component pont() {
      return this.pont;
    }
    
    private ComposantServerTransfert.Component server;
    
    private ComposantServerTransfert implem_server;
    
    private final class BridgeImpl_server implements ComposantServerTransfert.Requires {
    }
    
    public final ComposantServerTransfert.Component server() {
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
  
  private TransfertMultiple.ComponentImpl selfComponent;
  
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
  protected TransfertMultiple.Provides provides() {
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
  protected TransfertMultiple.Requires requires() {
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
  protected TransfertMultiple.Parts parts() {
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
  protected abstract ComposantClientTransfert make_client();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract ComposantPont make_pont();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract ComposantServerTransfert make_server();
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public synchronized TransfertMultiple.Component _newComponent(final TransfertMultiple.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of TransfertMultiple has already been used to create a component, use another one.");
    }
    this.init = true;
    TransfertMultiple.ComponentImpl  _comp = new TransfertMultiple.ComponentImpl(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public TransfertMultiple.Component newComponent() {
    return this._newComponent(new TransfertMultiple.Requires() {}, true);
  }
}
