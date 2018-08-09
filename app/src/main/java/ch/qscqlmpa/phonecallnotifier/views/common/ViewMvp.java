package ch.qscqlmpa.phonecallnotifier.views.common;

import android.view.View;

/**
 * MVC view interface.
 * MVC view is a "dumb" component used for presenting information to the user.<br>
 * Please note that MVC view is not the same as Android View - MVC view will usually wrap one or
 * more Android View's while adding logic for communication with MVC Controller and MVC Model.
 * Code taken from: https://github.com/techyourchance
 */
public interface ViewMvp {

    /**
     * Get the root Android View which is used internally by this MVP View for presenting data
     * to the user.<br>
     * The returned Android View might be used by an MVP Controller in order to query or alter the
     * properties of either the root Android View itself, or any of its child Android View's.
     * @return root Android View of this MVP View
     */
    View getRootView();
}
