package de.fh_koeln.sgmci.mtwd.scene.factory;

import de.fh_koeln.sgmci.mtwd.scene.IScene;
import de.fh_koeln.sgmci.mtwd.scene.RealistCommentingScene;
import org.mt4j.MTApplication;

/**
 *
 * @author danielvanderwal
 */
public class RealistCommentingSceneFactory extends AbstractMTWDSceneFactory {

    @Override
    public IScene createMTWDScene(MTApplication mtApp, String name) {
        IScene scene = new RealistCommentingScene(mtApp, name);
        super.createMTWDSceneContent(scene);
        return scene;
    }
}