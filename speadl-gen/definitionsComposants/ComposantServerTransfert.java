package definitionsComposants;

import interfaces.InterfaceServer;

@SuppressWarnings("all")
public abstract class ComposantServerTransfert {
  public interface Requires {
  }
  
  public interface Component extends ComposantServerTransfert.Provides {
  }
  
  public interface Provides {
    /**
     * This can be called to access the provided port.
     * 
     */
    public InterfaceServer unService();
  }
  
  public interface Parts {
  }
  
  public static class ComponentImpl implements ComposantServerTransfert.Component, ComposantServerTransfert.Parts {
    private final ComposantServerTransfert.Requires bridge;
    
    private final ComposantServerTransfert implementation;
    
    public void start() {
      this.implementation.start();
      this.implementation.started = true;
    }
    
    protected void initParts() {
      
    }
    
    protected void init_unService() {
      assert this.unService == null: "This is a bug.";
      this.unService = this.implementation.make_unService();
      if (this.unService == null) {
      	throw new RuntimeException("make_unService() in definitionsComposants.ComposantServerTransfert shall not return null.");
      }
    }
    
    protected void initProvidedPorts() {
      init_unService();
    }
    
    public ComponentImpl(final ComposantServerTransfert implem, final ComposantServerTransfert.Requires b, final boolean doInits) {
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
    
    private InterfaceServer unService;
    
    public InterfaceServer unService() {
      return this.unService;
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
  
  private ComposantServerTransfert.ComponentImpl selfComponent;
  
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
  protected ComposantServerTransfert.Provides provides() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("provides() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if provides() is needed to initialise the component.");
    }
    return this.selfComponent;
  }
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract InterfaceServer make_unService();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected ComposantServerTransfert.Requires requires() {
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
  protected ComposantServerTransfert.Parts parts() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("parts() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if parts() is needed to initialise the component.");
    }
    return this.selfComponent;
  }
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public synchronized ComposantServerTransfert.Component _newComponent(final ComposantServerTransfert.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of ComposantServerTransfert has already been used to create a component, use another one.");
    }
    this.init = true;
    ComposantServerTransfert.ComponentImpl  _comp = new ComposantServerTransfert.ComponentImpl(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public ComposantServerTransfert.Component newComponent() {
    return this._newComponent(new ComposantServerTransfert.Requires() {}, true);
  }
}
