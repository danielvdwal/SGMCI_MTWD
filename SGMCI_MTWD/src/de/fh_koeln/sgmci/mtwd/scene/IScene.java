package de.fh_koeln.sgmci.mtwd.scene;

import de.fh_koeln.sgmci.mtwd.IMain;
import org.mt4j.sceneManagement.Iscene;

/**
 *
 * @author Robert Scherbarth, Daniel van der Wal
 * @version 0.2.0
 */
public interface IScene extends Iscene {

    void setNextScene(IScene nextScene);

    IScene getNextScene();

    void gotoNextScene();

    void createBackground();

    void createEventListeners();

    void createComponents();

    void startScene();

    void updateScene();

    void updateController();

    /**
     * Sets the main object which is used to start the application.
     *
     * @param main the object which is used to start the application
     */
    void setMain(IMain main);
}
