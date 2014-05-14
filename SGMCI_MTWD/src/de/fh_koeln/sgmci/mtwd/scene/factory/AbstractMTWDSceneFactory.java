package de.fh_koeln.sgmci.mtwd.scene.factory;

import de.fh_koeln.sgmci.mtwd.scene.IScene;
import org.mt4j.MTApplication;

/**
 *
 * @author Daniel van der Wal
 * @version 0.2.0
 */
public abstract class AbstractMTWDSceneFactory {

    public abstract IScene createMTWDScene(MTApplication mtApp, String name);

    void createMTWDSceneContent(IScene scene) {
        scene.createBackground();
        scene.createComponents();
        scene.createEventListeners();
    }
}
