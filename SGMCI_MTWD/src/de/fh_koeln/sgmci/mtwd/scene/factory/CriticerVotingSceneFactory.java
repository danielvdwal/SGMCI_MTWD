package de.fh_koeln.sgmci.mtwd.scene.factory;

import de.fh_koeln.sgmci.mtwd.scene.CriticerVotingScene;
import de.fh_koeln.sgmci.mtwd.scene.IScene;
import org.mt4j.MTApplication;

/**
 *
 * @author danielvanderwal
 */
public class CriticerVotingSceneFactory extends AbstractMTWDSceneFactory {

    @Override
    public IScene createMTWDScene(MTApplication mtApp, String name) {
        IScene scene = new CriticerVotingScene(mtApp, name);
        super.createMTWDSceneContent(scene);
        return scene;
    }
}