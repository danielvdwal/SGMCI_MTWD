package de.fh_koeln.sgmci.mtwd.scene;

import de.fh_koeln.sgmci.mtwd.TestScene;
import de.fh_koeln.sgmci.mtwd.scene.factory.AbstractMTWDSceneFactory;
import de.fh_koeln.sgmci.mtwd.scene.factory.StartSceneFactory;
import org.mt4j.MTApplication;
import static org.mt4j.MTApplication.initialize;

/**
 *
 * @author danielvanderwal
 */
public class StartSceneTest extends MTApplication {

    public static void main(String[] args) {
        initialize();
    }

    @Override
    public void startUp() {
        AbstractMTWDSceneFactory factory = new StartSceneFactory();
        IScene scene = factory.createMTWDScene(this, "Start Scene");

        this.addScene(scene);
        scene.updateScene();
        scene.setNextScene(new TestScene(this, "Test scene"));
    }
}
