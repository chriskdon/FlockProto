package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.validation.*;

@Entity
@Table(name="Users")
public class User extends Model {
    public static Finder<Long,User> find = new Finder<Long,User>(Long.class, User.class);

    // ===== DATABASE COLUMNS =====
    @Id
    @Column(name ="ID")
    public Long id;

    @Constraints.Required
    @Column(name ="Username")
    public String username;

    @Constraints.Required
    @Column(name ="Firstname")
    public String firstname;

    @Constraints.Required
    @Column(name ="Lastname")
    public String lastname;

    @Constraints.Required
    @Column(name ="SaltedPassword")
    public String saltedPassword;

    @Constraints.Required
    @Column(name ="Salt")
    public String salt;

    @Constraints.Required
    @Column(name ="Email")
    public String email;

    @Constraints.Required
    @Column(name ="Secret")
    public String secret;
    // =========================

    public User() {}

    public User(String username, String firstname, String lastname, String saltedPassword, String salt, String email,
                String secret) {

        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.saltedPassword = saltedPassword;
        this.salt = salt;
        this.email = email;
        this.secret = secret;
    }

    public static User findBySecret(String secret) {
        return find.where().eq("Secret", secret).findUnique();
    }
}