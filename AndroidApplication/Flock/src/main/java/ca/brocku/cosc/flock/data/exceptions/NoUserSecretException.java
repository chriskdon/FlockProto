package ca.brocku.cosc.flock.data.exceptions;

/**
 * Should be fired when the application cannot find the user's secret key.
 * Either they aren't logged in, or haven't registered.
 *
 * Author: Chris Kellendonk
 * Student #: 4810800
 * Date: 11/18/2013
 */
public class NoUserSecretException extends Exception {
    public NoUserSecretException() {
        super("No Secret Could Be Found");
    }
}
