package sample.daffodil.sample2;

/**
 * Created by DAFFODIL-29 on 3/12/2018.
 */
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(indices = {@Index(value = {"email_id", "mobile"},
        unique = true)})
public class User {

    @PrimaryKey(autoGenerate = true)
    public int userId;

    @ColumnInfo(name = "email_id")
    public String email;

    @ColumnInfo(name = "first_name")
    public String firstName;

    @ColumnInfo(name = "last_name")
    public String lastName;

    @ColumnInfo(name = "mobile")
    public String mobile;

    @ColumnInfo(name = "password")
    public String password;


    public User(String email,String firstName,String lastName,String mobile,String password){
        this.userId=userId;
        this.email=email;
        this.firstName=firstName;
        this.lastName=lastName;
        this.mobile=mobile;
        this.password=password;
    }

    public int getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMobile() {
        return mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
