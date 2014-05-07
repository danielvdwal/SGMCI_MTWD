package de.fh_koeln.sgmci.mtwd;

import de.fh_koeln.sgmci.mtwd.scene.IScene;
import de.fh_koeln.sgmci.mtwd.scene.factory.AbstractMTWDSceneFactory;
import de.fh_koeln.sgmci.mtwd.scene.factory.DreamerSceneFactory;
import de.fh_koeln.sgmci.mtwd.scene.factory.StartSceneFactory;
import org.mt4j.MTApplication;

/**
 *
 * @author danielvanderwal
 */
public class Main extends MTApplication {

    public static void main(String[] args) {
        initialize(true);
    }

    @Override
    public void startUp() {
        AbstractMTWDSceneFactory startSceneFactory = new StartSceneFactory();
        AbstractMTWDSceneFactory dreamerSceneFactory = new DreamerSceneFactory();
        
        IScene startScene = startSceneFactory.createMTWDScene(this, "Start Scene");
        IScene dreamerScene = dreamerSceneFactory.createMTWDScene(this, "Dreamer Scene");
        
        startScene.setNextScene(dreamerScene);
        
        addScene(startScene);
        addScene(dreamerScene);
    }
}