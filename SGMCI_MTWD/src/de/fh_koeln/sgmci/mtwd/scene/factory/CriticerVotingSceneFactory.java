package de.fh_koeln.sgmci.mtwd.scene.factory;

import de.fh_koeln.sgmci.mtwd.scene.CriticerVotingScene;
import de.fh_koeln.sgmci.mtwd.scene.IScene;
import org.mt4j.MTApplication;

/**
 *
 * @author Daniel van der Wal
 * @version 0.1.0
 */
public class CriticerVotingSceneFactory extends AbstractMTWDSceneFactory {

    @Override
    public IScene createMTWDScene(MTApplication mtApp, String name) {
        IScene scene = new CriticerVotingScene(mtApp, name);
        createMTWDSceneContent(scene);
        return scene;
    }
}