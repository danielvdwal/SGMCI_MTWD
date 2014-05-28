package de.fh_koeln.sgmci.mtwd.scene;

import de.fh_koeln.sgmci.mtwd.IMain;
import de.fh_koeln.sgmci.mtwd.controller.AbstractMTWDSceneController;
import de.fh_koeln.sgmci.mtwd.customelements.AbstractKeyboard;
import org.mt4j.MTApplication;
import org.mt4j.sceneManagement.AbstractScene;
import org.mt4j.sceneManagement.transition.BlendTransition;
import org.mt4j.sceneManagement.transition.FadeTransition;
import org.mt4j.util.MT4jSettings;
import org.mt4j.util.opengl.GLFBO;

/**
 *
 * @author Daniel van der Wal
 * @version 0.2.0
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
    /**
     * The scale factor for all keyboards.
     */
    float componentScaleFactor;
    
    AbstractMTWDSceneController controller;

    public AbstractMTWDScene(MTApplication mtApp, String name) {
        super(mtApp, name);
        this.mtApp = mtApp;
        this.componentScaleFactor = (mtApp.getWidth() * 0.4f) / 850;
        // Set a scene transition for our StartScene.
        // Blend transition only available using opengl supporting the FBO extenstion.
        if (MT4jSettings.getInstance().isOpenGlMode() && GLFBO.isSupported(mtApp)) {
            this.setTransition(new BlendTransition(mtApp, 1200));
        } else {
            this.setTransition(new FadeTransition(mtApp));
        }
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
        nextScene.updateController();
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

    @Override
    public void updateScene() {
    }
    
    @Override
    public void updateController() {
        controller.update();
    }

    @Override
    public void setMain(IMain main) {
        this.main = main;
    }
}
