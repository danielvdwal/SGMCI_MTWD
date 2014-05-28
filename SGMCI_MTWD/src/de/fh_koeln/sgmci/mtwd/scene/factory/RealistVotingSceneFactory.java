package de.fh_koeln.sgmci.mtwd.scene.factory;

import de.fh_koeln.sgmci.mtwd.scene.IScene;
import de.fh_koeln.sgmci.mtwd.scene.RealistVotingScene;
import org.mt4j.MTApplication;

/**
 *
 * @author Daniel van der Wal
 * @version 0.3.0
 */
public class RealistVotingSceneFactory extends AbstractMTWDSceneFactory {

    @Override
    public IScene createMTWDScene(MTApplication mtApp, String name) {
        IScene scene = new RealistVotingScene(mtApp, name);
        createMTWDSceneContent(scene);
        return scene;
    }
}
