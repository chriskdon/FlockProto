package models;

import com.avaje.ebean.Expr;
import com.avaje.ebean.Expression;
import com.avaje.ebean.ExpressionList;
import play.data.validation.Constraints;
import play.db.ebean.Model;
import scala.annotation.unchecked.uncheckedStable;

import javax.persistence.*;
import java.util.List;

/**
 * Represents a connection between two users.
 */
@Entity
@Table(name="Connections")
public class Connection extends Model {
    private static Finder<Long, Connection> find = new Finder<Long, Connection>(Long.class, Connection.class);

    // ===== DATABASE COLUMNS =====
    @Id
    @Column(name ="UserA")
    public Long userAID;

    @Id
    @Column(name ="UserB")
    public Long userBID;

    @Constraints.Required
    @Column(name ="Accepted")
    public Boolean accepted;

    @Constraints.Required
    @Column(name ="Visible")
    public Boolean visible;
    // =============================

    public Connection() { }
    public Connection(long userAID, long userBID, boolean accepted, boolean visible) {
        this.userAID = userAID;
        this.userBID = userBID;
        this.accepted = accepted;
        this.visible = visible;
    }

    /**
     * Accept a connection between two people.
     *
     * @param currentUserID
     * @param friendUserID
     */
    public static void acceptConnection(long currentUserID, long friendUserID) throws Exception {
        // Only allow a friend to accept a request sent to them
        Connection conn = Connection.find.where().eq("UserA", friendUserID).eq("UserB", currentUserID).findUnique();

        if(conn == null) { throw new Exception("Invalid connection"); }

        conn.accepted = true;
        conn.save();
    }

    /**
     * Remove the connection from the database, it was declined.
     *
     * @param userAID user A ID
     * @param userBID user B ID
     */
    public static void declineConnection(long userAID, long userBID) throws Exception {
        Connection conn = getConnection(userAID, userBID).findUnique();

        if(conn == null) { throw new Exception("Invalid connection"); }

        conn.delete();
    }


    /**
     * Is this friend request already in the database.
     *
     * @param userAID
     * @param userBID
     * @return
     */
    public static boolean alreadyExists(long userAID, long userBID) {
        return (getConnection(userAID, userBID).findRowCount() > 0);
    }

    /**
     * Get the connection involving these two users.
     *
     * @param userAID
     * @param userBID
     */
    private static ExpressionList<Connection> getConnection(long userAID, long userBID) {
        Expression a = Expr.and(Expr.eq("UserA", userAID), Expr.eq("UserB", userBID));
        Expression b = Expr.and(Expr.eq("UserB", userAID), Expr.eq("UserA", userBID));

        return find.where().or(a, b);
    }

    /**
     * Get all connections by UserA
     * @param user
     * @return
     */
    public static List<Connection> searchByUserA(User user) {
        return searchByUser("UserA", user);
    }

    /**
     * Get all connections by UserB
     * @param user
     * @return
     */
    public static List<Connection> searchByUserB(User user) {
        return searchByUser("UserB", user);
    }

    /**
     * Search for a user.
     *
     * @param column    The column name (UserA, UserB)
     * @param user      The user to search with.
     *
     * @return          The list of connections found matching the description
     */
    private static List<Connection> searchByUser(String column, User user) {
        return find.where().eq(column,user.id).findList();
    }

    /**
     * Get friend information.
     *
     * Throw exception if they are not friends.
     *
     * @param currentUserID
     * @param friendUserID
     * @return
     */
    public static User getFriendInformation(long currentUserID, long friendUserID) throws Exception {
        // Make sure they are actually friends
        if(!areFriends(currentUserID, friendUserID)) { throw new Exception("No Friend connection"); }

        return User.find.byId(friendUserID);
    }

    /**
     * Check if two user's are friends.
     *
     * @param currentUserID
     * @param friendUserID
     * @return
     */
    public static boolean areFriends(long currentUserID, long friendUserID) {
        Connection conn = Connection.getConnection(currentUserID, friendUserID).findUnique();

        return (conn != null && conn.accepted);
    }

    /**
     * Check if the current user can see the friend.
     *
     * @param currentUserID
     * @param friendUserID
     * @return
     */
    public static boolean isVisible(long currentUserID, long friendUserID) {
        Connection conn = Connection.getConnection(currentUserID, friendUserID).findUnique();

        return (conn != null && conn.visible);
    }
}
