package de.fh_koeln.sgmci.mtwd.scene;

import org.mt4j.MTApplication;
import org.mt4j.sceneManagement.AbstractScene;

/**
 *
 * @author Robert Scherbarth
 */
public class StartScene extends AbstractScene implements IScene {

    public StartScene(MTApplication mtApp, String name) {
        super(mtApp, name);
    }

    @Override
    public void init() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void shutDown() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
