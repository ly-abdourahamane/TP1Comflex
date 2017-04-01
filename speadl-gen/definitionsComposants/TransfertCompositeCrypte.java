package definitionsComposants;

import definitionsComposants.ComposantClientTransfert;
import definitionsComposants.ComposantCryptageTransfert;
import definitionsComposants.ComposantDecryptageTansfert;
import definitionsComposants.ComposantServerTransfert;
import interfaces.InterfaceClient;
import interfaces.InterfaceServer;

@SuppressWarnings("all")
public abstract class TransfertCompositeCrypte {
  public interface Requires {
  }
  
  public interface Component extends TransfertCompositeCrypte.Provides {
  }
  
  public interface Provides {
    /**
     * This can be called to access the provided port.
     * 
     */
    public InterfaceClient unServiceTransfertCrypte();
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
    public ComposantCryptageTransfert.Component cryptage();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public ComposantDecryptageTansfert.Component decryptage();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public ComposantServerTransfert.Component server();
  }
  
  public static class ComponentImpl implements TransfertCompositeCrypte.Component, TransfertCompositeCrypte.Parts {
    private final TransfertCompositeCrypte.Requires bridge;
    
    private final TransfertCompositeCrypte implementation;
    
    public void start() {
      assert this.client != null: "This is a bug.";
      ((ComposantClientTransfert.ComponentImpl) this.client).start();
      assert this.cryptage != null: "This is a bug.";
      ((ComposantCryptageTransfert.ComponentImpl) this.cryptage).start();
      assert this.decryptage != null: "This is a bug.";
      ((ComposantDecryptageTansfert.ComponentImpl) this.decryptage).start();
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
      	throw new RuntimeException("make_client() in definitionsComposants.TransfertCompositeCrypte should not return null.");
      }
      this.client = this.implem_client._newComponent(new BridgeImpl_client(), false);
    }
    
    private void init_cryptage() {
      assert this.cryptage == null: "This is a bug.";
      assert this.implem_cryptage == null: "This is a bug.";
      this.implem_cryptage = this.implementation.make_cryptage();
      if (this.implem_cryptage == null) {
      	throw new RuntimeException("make_cryptage() in definitionsComposants.TransfertCompositeCrypte should not return null.");
      }
      this.cryptage = this.implem_cryptage._newComponent(new BridgeImpl_cryptage(), false);
    }
    
    private void init_decryptage() {
      assert this.decryptage == null: "This is a bug.";
      assert this.implem_decryptage == null: "This is a bug.";
      this.implem_decryptage = this.implementation.make_decryptage();
      if (this.implem_decryptage == null) {
      	throw new RuntimeException("make_decryptage() in definitionsComposants.TransfertCompositeCrypte should not return null.");
      }
      this.decryptage = this.implem_decryptage._newComponent(new BridgeImpl_decryptage(), false);
    }
    
    private void init_server() {
      assert this.server == null: "This is a bug.";
      assert this.implem_server == null: "This is a bug.";
      this.implem_server = this.implementation.make_server();
      if (this.implem_server == null) {
      	throw new RuntimeException("make_server() in definitionsComposants.TransfertCompositeCrypte should not return null.");
      }
      this.server = this.implem_server._newComponent(new BridgeImpl_server(), false);
    }
    
    protected void initParts() {
      init_client();
      init_cryptage();
      init_decryptage();
      init_server();
    }
    
    protected void init_unServiceTransfertCrypte() {
      // nothing to do here
    }
    
    protected void initProvidedPorts() {
      init_unServiceTransfertCrypte();
    }
    
    public ComponentImpl(final TransfertCompositeCrypte implem, final TransfertCompositeCrypte.Requires b, final boolean doInits) {
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
    
    public InterfaceClient unServiceTransfertCrypte() {
      return this.client().
      unTransfert()
      ;
    }
    
    private ComposantClientTransfert.Component client;
    
    private ComposantClientTransfert implem_client;
    
    private final class BridgeImpl_client implements ComposantClientTransfert.Requires {
      public final InterfaceServer unServer() {
        return TransfertCompositeCrypte.ComponentImpl.this.cryptage().
        serviceCryptage()
        ;
      }
    }
    
    public final ComposantClientTransfert.Component client() {
      return this.client;
    }
    
    private ComposantCryptageTransfert.Component cryptage;
    
    private ComposantCryptageTransfert implem_cryptage;
    
    private final class BridgeImpl_cryptage implements ComposantCryptageTransfert.Requires {
      public final InterfaceServer unDecryptage() {
        return TransfertCompositeCrypte.ComponentImpl.this.decryptage().
        serviceDecryptage()
        ;
      }
    }
    
    public final ComposantCryptageTransfert.Component cryptage() {
      return this.cryptage;
    }
    
    private ComposantDecryptageTansfert.Component decryptage;
    
    private ComposantDecryptageTansfert implem_decryptage;
    
    private final class BridgeImpl_decryptage implements ComposantDecryptageTansfert.Requires {
      public final InterfaceServer unServerFinal() {
        return TransfertCompositeCrypte.ComponentImpl.this.server().
        unService()
        ;
      }
    }
    
    public final ComposantDecryptageTansfert.Component decryptage() {
      return this.decryptage;
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
  
  private TransfertCompositeCrypte.ComponentImpl selfComponent;
  
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
  protected TransfertCompositeCrypte.Provides provides() {
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
  protected TransfertCompositeCrypte.Requires requires() {
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
  protected TransfertCompositeCrypte.Parts parts() {
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
  protected abstract ComposantCryptageTransfert make_cryptage();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract ComposantDecryptageTansfert make_decryptage();
  
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
  public synchronized TransfertCompositeCrypte.Component _newComponent(final TransfertCompositeCrypte.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of TransfertCompositeCrypte has already been used to create a component, use another one.");
    }
    this.init = true;
    TransfertCompositeCrypte.ComponentImpl  _comp = new TransfertCompositeCrypte.ComponentImpl(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public TransfertCompositeCrypte.Component newComponent() {
    return this._newComponent(new TransfertCompositeCrypte.Requires() {}, true);
  }
}
