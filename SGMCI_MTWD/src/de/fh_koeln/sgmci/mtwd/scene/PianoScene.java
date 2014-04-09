/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fh_koeln.sgmci.mtwd.scene;

import de.fh_koeln.sgmci.customelements.MTPiano;
import org.mt4j.MTApplication;
import org.mt4j.sceneManagement.AbstractScene;
import org.mt4j.util.math.Vector3D;

/**
 *
 * @author danielvanderwal
 */
public class PianoScene extends AbstractScene {

    public PianoScene(MTApplication mtapp, String name) {
        super(mtapp, name);

        MTPiano piano = new MTPiano(mtapp, new Vector3D(50, 50));
        getCanvas().addChild(piano);
    }

    @Override
    public void init() {
        // TODO Auto-generated method stub
    }

    @Override
    public void shutDown() {
        // TODO Auto-generated method stub
    }
}
