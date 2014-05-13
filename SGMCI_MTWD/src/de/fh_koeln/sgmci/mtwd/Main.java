package de.fh_koeln.sgmci.mtwd;

import de.fh_koeln.sgmci.mtwd.scene.IScene;
import de.fh_koeln.sgmci.mtwd.scene.factory.AbstractMTWDSceneFactory;
import de.fh_koeln.sgmci.mtwd.scene.factory.DreamerSceneFactory;
import de.fh_koeln.sgmci.mtwd.scene.factory.RealistCommentingSceneFactory;
import de.fh_koeln.sgmci.mtwd.scene.factory.RealistVotingSceneFactory;
import de.fh_koeln.sgmci.mtwd.scene.factory.StartSceneFactory;

import org.mt4j.MTApplication;

/**
 * This class is used as the main entry point for the application.<br >
 * It extends the MTApplication class and initializes necessary factories 
 * to create the individual scenes that are used throughout the whole 
 * application.
 * 
 * @author Daniel van der Wal
 * @version 0.1.0
 */
public class Main extends MTApplication {

    public static void main(String[] args) {
        initialize(true);
    }

    @Override
    public void startUp() {
        AbstractMTWDSceneFactory startSceneFactory = new StartSceneFactory();
        AbstractMTWDSceneFactory dreamerSceneFactory = new DreamerSceneFactory();
        AbstractMTWDSceneFactory realistVotingSceneFactory = new RealistVotingSceneFactory();
        AbstractMTWDSceneFactory realistCommentingSceneFactory = new RealistCommentingSceneFactory();
        
        IScene startScene = startSceneFactory.createMTWDScene(this, "Start Scene");
        IScene dreamerScene = dreamerSceneFactory.createMTWDScene(this, "Dreamer Scene");
        IScene realistVotingScene = realistVotingSceneFactory.createMTWDScene(this, "Realist Voting Scene");
        IScene realistCommentingScene = realistCommentingSceneFactory.createMTWDScene(this, "Realist Commenting Scene");
        
        startScene.setNextScene(dreamerScene);
        dreamerScene.setNextScene(realistVotingScene);
        realistVotingScene.setNextScene(realistCommentingScene);
        realistCommentingScene.setNextScene(startScene);
        
        //addScene(startScene);
        //addScene(dreamerScene);
        addScene(realistVotingScene);
        addScene(realistCommentingScene);
        
        realistVotingScene.startScene();
        //realistCommentingScene.startScene();
    }
}
