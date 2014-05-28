package de.fh_koeln.sgmci.mtwd.controller.strategy;

/**
 * This class is used as the super class for all UserContinueStrategies.<br >
 * It implements the IUserContinueStrategy interface without implementing its
 * methods, due to different requirements regarding the number of users using
 * the application.
 *
 * @author Daniel van der Wal
 * @version 0.3.0
 */
public abstract class AbstractUserContinueStrategy implements IUserContinueStrategy {

    int nrOfUsers;

    @Override
    public void setNrOfUsers(int nrOfUsers) {
        if (nrOfUsers == 0) {
            this.nrOfUsers = 1;
        } else {
            this.nrOfUsers = nrOfUsers;
        }
    }
}
