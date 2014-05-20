package de.fh_koeln.sgmci.mtwd;

import de.fh_koeln.sgmci.mtwd.scene.AbstractMTWDScene;
import de.fh_koeln.sgmci.mtwd.scene.IScene;
import org.mt4j.MTApplication;

/**
 *
 * @author danielvanderwal
 */
public class TestScene extends AbstractMTWDScene implements IScene {

    public TestScene(MTApplication mtApplication, String name) {
        super(mtApplication, name);
    }
}
