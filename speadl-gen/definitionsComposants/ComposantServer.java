package definitionsComposants;

import interfaces.InterfaceBasique;

@SuppressWarnings("all")
public abstract class ComposantServer {
  public interface Requires {
  }
  
  public interface Component extends ComposantServer.Provides {
  }
  
  public interface Provides {
    /**
     * This can be called to access the provided port.
     * 
     */
    public InterfaceBasique unServiceSimple();
  }
  
  public interface Parts {
  }
  
  public static class ComponentImpl implements ComposantServer.Component, ComposantServer.Parts {
    private final ComposantServer.Requires bridge;
    
    private final ComposantServer implementation;
    
    public void start() {
      this.implementation.start();
      this.implementation.started = true;
    }
    
    protected void initParts() {
      
    }
    
    protected void init_unServiceSimple() {
      assert this.unServiceSimple == null: "This is a bug.";
      this.unServiceSimple = this.implementation.make_unServiceSimple();
      if (this.unServiceSimple == null) {
      	throw new RuntimeException("make_unServiceSimple() in definitionsComposants.ComposantServer shall not return null.");
      }
    }
    
    protected void initProvidedPorts() {
      init_unServiceSimple();
    }
    
    public ComponentImpl(final ComposantServer implem, final ComposantServer.Requires b, final boolean doInits) {
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
    
    private InterfaceBasique unServiceSimple;
    
    public InterfaceBasique unServiceSimple() {
      return this.unServiceSimple;
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
  
  private ComposantServer.ComponentImpl selfComponent;
  
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
  protected ComposantServer.Provides provides() {
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
  protected abstract InterfaceBasique make_unServiceSimple();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected ComposantServer.Requires requires() {
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
  protected ComposantServer.Parts parts() {
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
  public synchronized ComposantServer.Component _newComponent(final ComposantServer.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of ComposantServer has already been used to create a component, use another one.");
    }
    this.init = true;
    ComposantServer.ComponentImpl  _comp = new ComposantServer.ComponentImpl(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public ComposantServer.Component newComponent() {
    return this._newComponent(new ComposantServer.Requires() {}, true);
  }
}
