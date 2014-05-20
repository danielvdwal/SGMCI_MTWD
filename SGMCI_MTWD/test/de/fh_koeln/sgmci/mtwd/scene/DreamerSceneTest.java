package de.fh_koeln.sgmci.mtwd.scene;

import de.fh_koeln.sgmci.mtwd.TestScene;
import de.fh_koeln.sgmci.mtwd.controller.AbstractMTWDSceneController;
import de.fh_koeln.sgmci.mtwd.scene.factory.AbstractMTWDSceneFactory;
import de.fh_koeln.sgmci.mtwd.scene.factory.DreamerSceneFactory;
import org.mt4j.MTApplication;
import static org.mt4j.MTApplication.initialize;

/**
 *
 * @author danielvanderwal
 */
public class DreamerSceneTest extends MTApplication {

    public static void main(String[] args) {
        initialize();
    }

    @Override
    public void startUp() {
        AbstractMTWDSceneFactory factory = new DreamerSceneFactory();
        IScene scene = factory.createMTWDScene(this, "Dreamer Scene");
        
        AbstractMTWDSceneController.setCurrentProblemId("problem_001");
        AbstractMTWDSceneController.getApplication().addProblem("problem_001", "Test problem");

        this.addScene(scene);
        scene.startScene();
        scene.setNextScene(new TestScene(this, "Test scene"));
    }
}
