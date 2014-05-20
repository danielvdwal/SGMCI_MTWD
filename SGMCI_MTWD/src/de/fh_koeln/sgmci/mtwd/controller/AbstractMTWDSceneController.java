package de.fh_koeln.sgmci.mtwd.controller;

import de.fh_koeln.sgmci.mtwd.model.Idea;
import de.fh_koeln.sgmci.mtwd.model.MultitouchWaltDisneyApplication;
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

    /**
     * The id of the current problem used in the application.
     */
    static String currentProblemId;
    /**
     * A flag to check if a user on the south side of the Multi-touch table is
     * active.
     */
    static boolean user1Activate; // SOUTH
    /**
     * A flag to check if a user on the north side of the Multi-touch table is
     * active.
     */
    static boolean user2Activate; // NORTH
    /**
     * A flag to check if a user on the west side of the Multi-touch table is
     * active.
     */
    static boolean user3Activate; // WEST
    /**
     * A flag to check if a user on the east side of the Multi-touch table is
     * active.
     */
    static boolean user4Activate; // EAST
    /**
     * A flag to check if the user on the south side of the Multi-touch table is
     * ready to continue to the next scene.
     */
    boolean user1ReadyToContinue;
    /**
     * A flag to check if the user on the north side of the Multi-touch table is
     * ready to continue to the next scene.
     */
    boolean user2ReadyToContinue;
    /**
     * A flag to check if the user on the west side of the Multi-touch table is
     * ready to continue to the next scene.
     */
    boolean user3ReadyToContinue;
    /**
     * A flag to check if the user on the east side of the Multi-touch table is
     * ready to continue to the next scene.
     */
    boolean user4ReadyToContinue;
    /**
     * The scene which is used as the observer to be notified on changes.
     */
    IScene observer;

    public AbstractMTWDSceneController(IScene observer) {
        this.observer = observer;
        // the user on the south side should be active since he/she is the moderator
        user1Activate = true;
    }
    
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

    /**
     * Get the newest idea for the current problem.
     *
     * @return the newest idea for the current problem
     */
    public Idea popNewestIdeaForCurrentProblem() {
        return application.getProblem(currentProblemId).popNewestIdea();
    }

    /**
     * Check if a user on the south side of the Multi-touch table is using the
     * application.
     *
     * @return true, if the user on the south side of the Multi-touch table uses
     * the application<br >
     * false, if there is no user on the south side of the Multi-touch table
     */
    public static boolean isUser1Activate() {
        return user1Activate;
    }
    
    /**
     * Sets if the user on the south side of the Multi-touch table is active.
     *
     * @param active the new value to be set
     */
    public void setUser1Activate(boolean active) {
        user1Activate = active;
        observer.updateScene();
    }

    /**
     * Check if a user on the north side of the Multi-touch table is using the
     * application.
     *
     * @return true, if the user on the north side of the Multi-touch table uses
     * the application<br >
     * false, if there is no user on the north side of the Multi-touch table
     */
    public static boolean isUser2Activate() {
        return user2Activate;
    }
    
    /**
     * Sets if the user on the north side of the Multi-touch table is active.
     *
     * @param active the new value to be set
     */
    public void setUser2Activate(boolean active) {
        user2Activate = active;
        observer.updateScene();
    }

    /**
     * Check if a user on the west side of the Multi-touch table is using the
     * application.
     *
     * @return true, if the user on the west side of the Multi-touch table uses
     * the application<br >
     * false, if there is no user on the west side of the Multi-touch table
     */
    public static boolean isUser3Activate() {
        return user3Activate;
    }
    
    /**
     * Sets if the user on the west side of the Multi-touch table is active.
     *
     * @param active the new value to be set
     */
    public void setUser3Activate(boolean active) {
        user3Activate = active;
        observer.updateScene();
    }

    /**
     * Check if a user on the east side of the Multi-touch table is using the
     * application.
     *
     * @return true, if the user on the east side of the Multi-touch table uses
     * the application<br >
     * false, if there is no user on the east side of the Multi-touch table
     */
    public static boolean isUser4Activate() {
        return user4Activate;
    }
    
    /**
     * Sets if the user on the east side of the Multi-touch table is active.
     *
     * @param active the new value to be set
     */
    public void setUser4Activate(boolean active) {
        user4Activate = active;
        observer.updateScene();
    }

    /**
     * Check if the user on the south side of the Multi-touch table is ready to
     * continue to the next scene.
     *
     * @return true, if the user on the south side of the Multi-touch table is
     * ready to continue to the next scene<br >
     * false, if there the user on the south side of the Multi-touch table is
     * not ready
     */
    public boolean isUser1ReadyToContinue() {
        return user1ReadyToContinue;
    }

    /**
     * Sets if the user on the south side of the Multi-touch table is ready to
     * continue to the next scene.
     *
     * @param ready the new value to be set
     */
    public void setUser1ReadyToContinue(boolean ready) {
        this.user1ReadyToContinue = ready;
    }

    /**
     * Check if the user on the north side of the Multi-touch table is ready to
     * continue to the next scene.
     *
     * @return true, if the user on the north side of the Multi-touch table is
     * ready to continue to the next scene<br >
     * false, if there the user on the north side of the Multi-touch table is
     * not ready
     */
    public boolean isUser2ReadyToContinue() {
        return user2ReadyToContinue;
    }

    /**
     * Sets if the user on the north side of the Multi-touch table is ready to
     * continue to the next scene.
     *
     * @param ready the new value to be set
     */
    public void setUser2ReadyToContinue(boolean ready) {
        this.user2ReadyToContinue = ready;
    }

    /**
     * Check if the user on the west side of the Multi-touch table is ready to
     * continue to the next scene.
     *
     * @return true, if the user on the west side of the Multi-touch table is
     * ready to continue to the next scene<br >
     * false, if there the user on the west side of the Multi-touch table is not
     * ready
     */
    public boolean isUser3ReadyToContinue() {
        return user3ReadyToContinue;
    }

    /**
     * Sets if the user on the west side of the Multi-touch table is ready to
     * continue to the next scene.
     *
     * @param ready the new value to be set
     */
    public void setUser3ReadyToContinue(boolean ready) {
        this.user3ReadyToContinue = ready;
    }

    /**
     * Check if the user on the east side of the Multi-touch table is ready to
     * continue to the next scene.
     *
     * @return true, if the user on the east side of the Multi-touch table is
     * ready to continue to the next scene<br >
     * false, if there the user on the east side of the Multi-touch table is not
     * ready
     */
    public boolean isUser4ReadyToContinue() {
        return user4ReadyToContinue;
    }

    /**
     * Sets if the user on the east side of the Multi-touch table is ready to
     * continue to the next scene.
     *
     * @param ready the new value to be set
     */
    public void setUser4ReadyToContinue(boolean ready) {
        this.user4ReadyToContinue = ready;
    }
}
