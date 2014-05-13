package de.fh_koeln.sgmci.mtwd.scene;

import de.fh_koeln.sgmci.mtwd.scene.factory.AbstractMTWDSceneFactory;
import de.fh_koeln.sgmci.mtwd.scene.factory.EvaluationSceneFactory;
import org.mt4j.MTApplication;
import static org.mt4j.MTApplication.initialize;

/**
 *
 * @author danielvanderwal, Robert Scherbarth
 * @version 0.20
 */
public class EvaluationSceneTest extends MTApplication {

    public static void main(String[] args) {
        initialize();
    }

    @Override
    public void startUp() {
        AbstractMTWDSceneFactory factory = new EvaluationSceneFactory();
        IScene scene = factory.createMTWDScene(this, "Evaluation Scene");
        
        this.addScene(scene);
        scene.updateScene();
    }
}