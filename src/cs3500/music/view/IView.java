package cs3500.music.view;

/**
 * Created by sahaj on 6/14/2017.
 */

/**
 * Interface for generic model. It can be any type of view: visual, audial, etc.
 * It only has one requirement, and that is "activating the view".
 * This interface's serves the purposes of unifying views to provide convenince
 * to the view factory.
 */
public interface IView {

    /**
     * "Activates" the current view. This can mean many things, depending one
     * what type of view. If visual, this probably suggests initializing the
     * scene. If audial, it probably suggests playing the music, and so on.
     */
    void activate();
}
