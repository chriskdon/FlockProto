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
    // =============================

    public Connection() { }
    public Connection(long userAID, long userBID, boolean accepted) {
        this.userAID = userAID;
        this.userBID = userBID;
        this.accepted = accepted;
    }

    /**
     * Accept a connection between two people.
     *
     * @param userAID
     * @param userBID
     */
    public static void acceptConnection(long userAID, long userBID) throws Exception {
        Connection conn = getConnection(userAID, userBID).findUnique();

        if(conn == null) { throw new Exception("Invalid Connection"); }

        conn.accepted = true;
        conn.save();
    }

    /**
     * Remove the connection from the database, it was declined.
     *
     * @param userAID User A ID
     * @param userBID User B ID
     */
    public static void declineConnection(long userAID, long userBID) {
        Connection conn = getConnection(userAID, userBID).findUnique();

        if(conn != null) { conn.delete(); }
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
}
