package de.fh_koeln.sgmci.mtwd.scene.factory;

import de.fh_koeln.sgmci.mtwd.scene.EvaluationScene;
import de.fh_koeln.sgmci.mtwd.scene.IScene;
import org.mt4j.MTApplication;

/**
 *
 * @author Daniel van der Wal, Robert Scherbarth
 * @version 0.1.0
 */
public class EvaluationSceneFactory extends AbstractMTWDSceneFactory {

    @Override
    public IScene createMTWDScene(MTApplication mtApp, String name) {
        IScene scene = new EvaluationScene(mtApp, name);
        createMTWDSceneContent(scene);
        return scene;
    }
}
