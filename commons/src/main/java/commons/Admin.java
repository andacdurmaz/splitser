package commons;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class Admin {

    @Id
    private String email;
    private String password;


    /**
     * Constructor for mapping
     */
    public Admin() {
        // for object mapper
    }

    /**
     * Constructor of the admin class
     *
     * @param email    the email of the admin
     * @param password the password of the admin
     */
    public Admin(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /**
     * Method that returns the email of the admin
     *
     * @return the email of the admin
     */
    public String getEmail() {
        return email;
    }

    /**
     * Method to set the email of an admin
     *
     * @param email the email you want to set to the admin
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Method that returns the password of the admin
     *
     * @return the password of the admin
     */
    public String getPassword() {
        return password;
    }

    /**
     * Method to set the password of an admin
     *
     * @param password the password you want to set to the admin
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * A method to check if 2 objects are equal
     *
     * @param o The object you want to compare to
     * @return Returns true if the objects are equal and otherwise returns false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Admin admin = (Admin) o;
        return Objects.equals(email, admin.email) && Objects.equals(password, admin.password);
    }

    /**
     * Method to generate a hashcode
     *
     * @return returns the generated hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(email, password);
    }

    /**
     * Method which makes a string from the attribute of the admin
     *
     * @return returns the string which is generated
     */
    @Override
    public String toString() {
        return "Admin{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
