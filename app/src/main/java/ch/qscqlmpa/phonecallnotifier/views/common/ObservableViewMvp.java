package ch.qscqlmpa.phonecallnotifier.views.common;

/**
 * This interface corresponds to MVC views that need to notify MVC controllers of input events
 * Code taken from: https://github.com/techyourchance
 */
public interface ObservableViewMvp<ListenerType> extends ViewMvp {

    /**
     * Register a listener that will be notified of any input events performed on this MVC view
     */
    void registerListener(ListenerType listener);

    /**
     * Unregister a previously registered listener. Does nothing if the listener wasn't registered.
     */
    void unregisterListener(ListenerType listener);


}
