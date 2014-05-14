package de.fh_koeln.sgmci.mtwd.controller.strategy;

import de.fh_koeln.sgmci.mtwd.controller.AbstractMTWDSceneController;

/**
 * This class is used as the super class for all UserContinueStrategies.<br >
 * It implements the IUserContinueStrategy interface without implementing its
 * methods, due to different requirements regarding the number of users using
 * the application.
 *
 * @author Daniel van der Wal
 * @version 0.2.0
 */
public abstract class AbstractUserContinueStrategy implements IUserContinueStrategy {

    /**
     * The controller to be checked for the requirements that have to be
     * fulfilled to continue.
     */
    final AbstractMTWDSceneController controller;

    public AbstractUserContinueStrategy(AbstractMTWDSceneController controller) {
        this.controller = controller;
    }
}
