package ca.brocku.cosc.flock.data.api.json.models;

/**
 * Created by Chris Kellendonk
 * Student #: 4810800
 * Date: 12/6/2013.
 */
public class ErrorTypes {
    public static final int ERROR_TYPE_FATAL = -1;   // Unrecoverable logic errors
    public static final int ERROR_TYPE_USER = -2;    // Errors that can be handled by applicaiton log (e.g. wrong password)
}
