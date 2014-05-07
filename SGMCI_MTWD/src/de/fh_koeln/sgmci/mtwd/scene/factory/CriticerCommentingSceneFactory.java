package de.fh_koeln.sgmci.mtwd.scene.factory;

import de.fh_koeln.sgmci.mtwd.scene.CriticerCommentingScene;
import de.fh_koeln.sgmci.mtwd.scene.IScene;
import org.mt4j.MTApplication;

/**
 *
 * @author danielvanderwal
 */
public class CriticerCommentingSceneFactory extends AbstractMTWDSceneFactory {

    @Override
    public IScene createMTWDScene(MTApplication mtApp, String name) {
        IScene scene = new CriticerCommentingScene(mtApp, name);
        super.createMTWDSceneContent(scene);
        return scene;
    }
}
