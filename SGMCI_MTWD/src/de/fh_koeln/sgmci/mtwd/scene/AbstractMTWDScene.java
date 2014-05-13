package de.fh_koeln.sgmci.mtwd.scene;

import org.mt4j.MTApplication;
import org.mt4j.sceneManagement.AbstractScene;

/**
 *
 * @author Daniel van der Wal
 * @version 0.1.0
 */
public abstract class AbstractMTWDScene extends AbstractScene implements IScene {

    final MTApplication mtApp;
    IScene nextScene;
    
    public AbstractMTWDScene(MTApplication mtApp, String name) {
        super(mtApp, name);
        this.mtApp = mtApp;
    }

    @Override
    public void init() {
    }

    @Override
    public void shutDown() {
    }
    
    @Override
    public void setNextScene(IScene nextScene) {
        this.nextScene = nextScene;
    }

    @Override
    public IScene getNextScene() {
        return nextScene;
    }

    @Override
    public void gotoNextScene() {
        mtApp.pushScene();
        nextScene.startScene();
        mtApp.changeScene(nextScene);
    }
    
    @Override
    public void createBackground() {
    }
    
    @Override
    public void createEventListeners() {
    }
    
    @Override
    public void createComponents() {
    }
    
    @Override
    public void startScene() {
    }
}
