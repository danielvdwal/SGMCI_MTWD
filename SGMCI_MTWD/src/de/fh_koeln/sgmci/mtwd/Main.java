package de.fh_koeln.sgmci.mtwd;

import de.fh_koeln.sgmci.mtwd.scene.IScene;
import de.fh_koeln.sgmci.mtwd.scene.factory.AbstractMTWDSceneFactory;
import de.fh_koeln.sgmci.mtwd.scene.factory.CriticerVotingSceneFactory;
import de.fh_koeln.sgmci.mtwd.scene.factory.DreamerSceneFactory;
import de.fh_koeln.sgmci.mtwd.scene.factory.RealistVotingSceneFactory;
import de.fh_koeln.sgmci.mtwd.scene.factory.RealistCommentingSceneFactory;
import de.fh_koeln.sgmci.mtwd.scene.factory.SplashSceneFactory;
import de.fh_koeln.sgmci.mtwd.scene.factory.StartSceneFactory;
import de.fh_koeln.sgmci.mtwd.scene.factory.EvaluationSceneFactory;
import de.fh_koeln.sgmci.mtwd.scene.factory.CriticerCommentingSceneFactory;

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
        AbstractMTWDSceneFactory splashSceneFactory = new SplashSceneFactory();
        IScene splashScene = splashSceneFactory.createMTWDScene(this, "Splash Scene");
        addScene(splashScene);
        

        AbstractMTWDSceneFactory startSceneFactory = new StartSceneFactory();
        AbstractMTWDSceneFactory dreamerSceneFactory = new DreamerSceneFactory();
        AbstractMTWDSceneFactory realistVotingSceneFactory = new RealistVotingSceneFactory();
        AbstractMTWDSceneFactory realistCommentingSceneFactory = new RealistCommentingSceneFactory();
        AbstractMTWDSceneFactory criticerCommentingSceneFactory = new CriticerCommentingSceneFactory();
        AbstractMTWDSceneFactory criticerVotingSceneFactory = new CriticerVotingSceneFactory();
        AbstractMTWDSceneFactory evaluationSceneFactory = new EvaluationSceneFactory();
        
        IScene startScene = startSceneFactory.createMTWDScene(this, "Start Scene");
        IScene dreamerScene = dreamerSceneFactory.createMTWDScene(this, "Dreamer Scene");
        IScene realistVotingScene = realistVotingSceneFactory.createMTWDScene(this, "Realist Voting Scene");
        IScene realistCommentingScene = realistCommentingSceneFactory.createMTWDScene(this, "Realist Commenting Scene");
        IScene criticerCommentingScene = criticerCommentingSceneFactory.createMTWDScene(this, "Criticer Commenting Scene");
        IScene criticerVotingScene = criticerVotingSceneFactory.createMTWDScene(this, "Criticer Voting Scene");
        IScene evaluationScene = evaluationSceneFactory.createMTWDScene(this, "Evaluation Scene");
        
        
        
        splashScene.setNextScene(startScene);
        startScene.setNextScene(dreamerScene);
        dreamerScene.setNextScene(realistVotingScene);
        realistVotingScene.setNextScene(realistCommentingScene);
        realistCommentingScene.setNextScene(criticerCommentingScene);
        criticerCommentingScene.setNextScene(criticerVotingScene);
        criticerVotingScene.setNextScene(evaluationScene);
        evaluationScene.setNextScene(startScene);
        
        
        addScene(startScene);
        addScene(dreamerScene);
        addScene(realistVotingScene);
        addScene(realistCommentingScene);
        addScene(criticerCommentingScene);
        addScene(criticerVotingScene);
        addScene(evaluationScene);
        splashScene.updateScene();
    }
}
