package de.fh_koeln.sgmci.mtwd.scene;

import de.fh_koeln.sgmci.mtwd.IMain;
import org.mt4j.MTApplication;
import org.mt4j.sceneManagement.AbstractScene;

/**
 *
 * @author Daniel van der Wal
 * @version 0.1.0
 */
public abstract class AbstractMTWDScene extends AbstractScene implements IScene {

    /**
     * The main object which is used to start the application.<br >
     * (Necessary for MT4J objects).
     */
    final MTApplication mtApp;
    /**
     * The scene to be proceeded to.
     */
    IScene nextScene;
    /**
     * The main object which is used to start the application.
     */
    IMain main;

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
        nextScene.updateScene();
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
    public void updateScene() {
    }

    @Override
    public void setMain(IMain main) {
        this.main = main;
    }
}
