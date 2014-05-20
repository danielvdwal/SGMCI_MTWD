package de.fh_koeln.sgmci.mtwd.scene;

import de.fh_koeln.sgmci.mtwd.IMain;
import de.fh_koeln.sgmci.mtwd.TestScene;
import de.fh_koeln.sgmci.mtwd.scene.factory.AbstractMTWDSceneFactory;
import de.fh_koeln.sgmci.mtwd.scene.factory.SplashSceneFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mt4j.MTApplication;
import static org.mt4j.MTApplication.initialize;

/**
 *
 * @author danielvanderwal
 */
public class SplashSceneTest extends MTApplication implements IMain {

    public static void main(String[] args) {
        initialize();
    }

    @Override
    public void startUp() {
        AbstractMTWDSceneFactory factory = new SplashSceneFactory();
        IScene scene = factory.createMTWDScene(this, "Splash Scene");

        this.addScene(scene);
        scene.setNextScene(new TestScene(this, "Test Scene"));
        scene.setMain(this);
        scene.updateScene();
    }

    @Override
    public void loadResources() {
        System.out.println("Loading resources");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            Logger.getLogger(SplashSceneTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
