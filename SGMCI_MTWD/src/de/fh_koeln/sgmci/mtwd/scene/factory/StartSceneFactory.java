package de.fh_koeln.sgmci.mtwd.scene.factory;

import de.fh_koeln.sgmci.mtwd.scene.IScene;
import de.fh_koeln.sgmci.mtwd.scene.StartScene;
import org.mt4j.MTApplication;

/**
 *
 * @author danielvanderwal
 */
public class StartSceneFactory extends AbstractMTWDSceneFactory {

    @Override
    public IScene createMTWDScene(MTApplication mtApp, String name) {
        IScene scene = new StartScene(mtApp, name);
        super.createMTWDSceneContent(scene);
        return scene;
    }
}
