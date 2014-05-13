package de.fh_koeln.sgmci.mtwd.controller.strategy;

/**
 * This interface is used to check if requirements are met, so that the scene
 * can go on to the next scene.<br >
 * Due to different requirements related to the number of users using 
 * the application, the strategy pattern is used.
 * 
 * @author Daniel van der Wal
 * @version 0.1.0
 */
public interface IUserContinueStrategy {
    
    /**
     * Check if all requirements are met to go to the next scene.
     * 
     * @return true, if all requirements are met to go to the next scene<br >
     * false, if one or more of these requirements are not met
     */
    boolean canContinue();
}
