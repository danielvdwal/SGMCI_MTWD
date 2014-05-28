package de.fh_koeln.sgmci.mtwd.controller;

import de.fh_koeln.sgmci.mtwd.controller.strategy.IUserContinueStrategy;
import de.fh_koeln.sgmci.mtwd.controller.strategy.UsersAreReadyContinueStrategy;
import de.fh_koeln.sgmci.mtwd.model.Idea;
import de.fh_koeln.sgmci.mtwd.model.MultitouchWaltDisneyApplication;
import de.fh_koeln.sgmci.mtwd.model.User;
import de.fh_koeln.sgmci.mtwd.scene.IScene;
import java.util.List;

/**
 * This class is used as the super class for all SceneControllers.<br >
 * It implements all necessary methods for those SceneControllers using the
 * template method pattern.<br >
 * In addition, the observer pattern (with only one observer) is used to notify
 * the scenes about changes.
 *
 * @author Daniel van der Wal
 * @version 0.2.0
 */
public abstract class AbstractMTWDSceneController {

    /**
     * The application object used through out the application to communicate
     * with the model.
     */
    final static MultitouchWaltDisneyApplication application = new MultitouchWaltDisneyApplication();

    public final static String user1Id = "user_1";
    public final static String user2Id = "user_2";
    public final static String user3Id = "user_3";
    public final static String user4Id = "user_4";

    final static User user1;
    final static User user2;
    final static User user3;
    final static User user4;
    /**
     * The id of the current problem used in the application.
     */
    static String currentProblemId;
    /**
     * The scene which is used as the observer to be notified on changes.
     */
    IScene observer;

    IUserContinueStrategy userContinueStrategy;

    static {
        application.addUser(user1Id);
        application.addUser(user2Id);
        application.addUser(user3Id);
        application.addUser(user4Id);
        user1 = application.getUser(user1Id);
        user2 = application.getUser(user2Id);
        user3 = application.getUser(user3Id);
        user4 = application.getUser(user4Id);
    }

    public AbstractMTWDSceneController(IScene observer) {
        this.observer = observer;
        // the user on the south side should be active since he/she is the moderator
        user1.setActive(true);
        userContinueStrategy = new UsersAreReadyContinueStrategy();
        updateUserContinueStrategy();
    }
    
    public void update() {}

    /**
     * Get the application object.
     *
     * @return the application object
     */
    public static MultitouchWaltDisneyApplication getApplication() {
        return application;
    }

    /**
     * Set the id of the current problem
     *
     * @param currentProblemId the id of the current problem
     */
    public static void setCurrentProblemId(String currentProblemId) {
        AbstractMTWDSceneController.currentProblemId = currentProblemId;
    }

    /**
     * Get the current problem description which was entered via the start
     * scene.
     *
     * @return a string of the problem description
     */
    public String getCurrentProblemDescription() {
        return application.getProblem(currentProblemId).getDescription();
    }

    /**
     * Get all ideas for the current problem that are still visible.
     *
     * @return a list of all ideas for the current problem that are still
     * visible
     */
    public List<Idea> getAllVisibleIdeasForCurrentProblem() {
        return application.getProblem(currentProblemId).getAllVisibleIdeas();
    }

    public static boolean isUserActive(String id) {
        return application.getUser(id).isActive();
    }

    public void setUserActive(String id, boolean active) {
        application.getUser(id).setActive(active);
        observer.updateScene();
        updateUserContinueStrategy();
    }

    public boolean isUserReadyToContinue(String id) {
        return application.getUser(id).isReadyToContinue();
    }

    public void setUserReadyToContinue(String id, boolean ready) {
        application.getUser(id).setReadyToContinue(ready);
        observer.updateScene();
        updateUserContinueStrategy();
    }

    public final void updateUserContinueStrategy() {
        userContinueStrategy.setNrOfUsers(application.getAllActiveUsers().size());
    }
}
