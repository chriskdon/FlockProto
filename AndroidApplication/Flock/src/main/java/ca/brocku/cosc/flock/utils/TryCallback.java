package ca.brocku.cosc.flock.utils;

/**
 * Used when doing actions that involve trying to do
 * an action that may fail.
 *
 * Author: Chris Kellendonk
 * Student #: 4810800
 * Date: 12/8/2013
 */
public abstract class TryCallback {
    public abstract void success();
    public void failure() {};
}
