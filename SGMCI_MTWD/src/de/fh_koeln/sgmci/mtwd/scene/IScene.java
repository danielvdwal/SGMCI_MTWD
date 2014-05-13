package de.fh_koeln.sgmci.mtwd.scene;

import org.mt4j.sceneManagement.Iscene;

/**
 *
 * @author Robert Scherbarth, Daniel van der Wal
 * @version 0.1.0
 */
public interface IScene extends Iscene {
    
    void setNextScene(IScene nextScene);
    IScene getNextScene();
    void gotoNextScene();
    void createBackground();
    void createEventListeners();
    void createComponents();
    void updateScene();
}
