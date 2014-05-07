package de.fh_koeln.sgmci.mtwd.scene;

import org.mt4j.sceneManagement.Iscene;

/**
 *
 * @author Robert Scherbarth, Daniel van der Wal
 */
public interface IScene extends Iscene {
    
    void setNextScene(IScene nextScene);
    IScene getNextScene();
    void gotoNextScene();
    void createBackground();
    void createEventListeners();
    void createComponents();
    void startScene();
}
