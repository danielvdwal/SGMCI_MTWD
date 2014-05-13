package de.fh_koeln.sgmci.mtwd.scene;

import de.fh_koeln.sgmci.mtwd.scene.factory.AbstractMTWDSceneFactory;
import de.fh_koeln.sgmci.mtwd.scene.factory.RealistCommentingSceneFactory;
import org.mt4j.MTApplication;
import static org.mt4j.MTApplication.initialize;

/**
 *
 * @author danielvanderwal
 */
public class RealistCommentingSceneTest extends MTApplication {

    public static void main(String[] args) {
        initialize();
    }

    @Override
    public void startUp() {
        AbstractMTWDSceneFactory factory = new RealistCommentingSceneFactory();
        IScene scene = factory.createMTWDScene(this, "Realist Commenting Scene");
        
        this.addScene(scene);
        scene.startScene();
    }
}