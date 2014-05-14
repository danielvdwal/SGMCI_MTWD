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
 * It extends the MTApplication class and initializes necessary factories to
 * create the individual scenes that are used throughout the whole application.
 *
 * @author Daniel van der Wal
 * @version 0.1.0
 */
public class Main extends MTApplication {

    private MTApplication main;
    private IScene splashScene;
    private IScene startScene;
    private IScene dreamerScene;
    private IScene realistVotingScene;
    private IScene realistCommentingScene;
    private IScene criticerCommentingScene;
    private IScene criticerVotingScene;
    private IScene evaluationScene;

    public static void main(String[] args) {
        initialize(true);
    }

    @Override
    public void startUp() {
        main = this;

        AbstractMTWDSceneFactory splashSceneFactory = new SplashSceneFactory();
        splashScene = splashSceneFactory.createMTWDScene(main, "Splash Scene");
        addScene(splashScene);

        new Thread(new Runnable() {

            @Override
            public void run() {
                AbstractMTWDSceneFactory startSceneFactory = new StartSceneFactory();
                AbstractMTWDSceneFactory dreamerSceneFactory = new DreamerSceneFactory();
                AbstractMTWDSceneFactory realistVotingSceneFactory = new RealistVotingSceneFactory();
                AbstractMTWDSceneFactory realistCommentingSceneFactory = new RealistCommentingSceneFactory();
                AbstractMTWDSceneFactory criticerCommentingSceneFactory = new CriticerCommentingSceneFactory();
                AbstractMTWDSceneFactory criticerVotingSceneFactory = new CriticerVotingSceneFactory();
                AbstractMTWDSceneFactory evaluationSceneFactory = new EvaluationSceneFactory();

                startScene = startSceneFactory.createMTWDScene(main, "Start Scene");
                dreamerScene = dreamerSceneFactory.createMTWDScene(main, "Dreamer Scene");
                realistVotingScene = realistVotingSceneFactory.createMTWDScene(main, "Realist Voting Scene");
                realistCommentingScene = realistCommentingSceneFactory.createMTWDScene(main, "Realist Commenting Scene");
                criticerCommentingScene = criticerCommentingSceneFactory.createMTWDScene(main, "Criticer Commenting Scene");
                criticerVotingScene = criticerVotingSceneFactory.createMTWDScene(main, "Criticer Voting Scene");
                evaluationScene = evaluationSceneFactory.createMTWDScene(main, "Evaluation Scene");

                splashScene.setNextScene(startScene);
                startScene.setNextScene(dreamerScene);
                dreamerScene.setNextScene(realistVotingScene);
                realistVotingScene.setNextScene(realistCommentingScene);
                realistCommentingScene.setNextScene(criticerCommentingScene);
                criticerCommentingScene.setNextScene(criticerVotingScene);
                criticerVotingScene.setNextScene(evaluationScene);
                evaluationScene.setNextScene(startScene);

                splashScene.updateScene();

                addScene(startScene);
                addScene(dreamerScene);
                addScene(realistVotingScene);
                addScene(realistCommentingScene);
                addScene(criticerCommentingScene);
                addScene(criticerVotingScene);
                addScene(evaluationScene);
            }
        }).start();
    }
}
