package commons;

public class User {
    enum Language {
        EN,
        NL
    }
    private String username;
    private String email;
    private String password;
    private String serverURL;
    private String IBAN;
    private String BIC;
    private Language language;

    /**
     * Constructor method for a User
     * @param username the username of the User
     * @param email the email of the User
     * @param password the password of the User
     */
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.language = Language.EN;
    }

    /**
     * Getter method for User username
     * @return the username of the User
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter method for an User's username
     * @param username new username of the User
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * Getter method for User email
     * @return the email of the User
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter method for an User's e-mail
     * @param email new e-mail of the User
     * @throws EmailFormatException
     */
    public void setEmail(String email) throws EmailFormatException {
        if (email.indexOf('@') == -1) {
            throw new EmailFormatException();
        }
        this.email = email;
    }
    /**
     * Getter method for User password
     * @return the password of the User
     */
    public String getPassword() {
        return password;
    }
    /**
     * Setter method for an User's password
     * @param password new password of the User
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * Getter method for User's connected server
     * @return the server of the User
     */
    public String getServerURL() {
        return serverURL;
    }
    /**
     * Setter method for an User's server URL
     * @param serverURL new server URL of the User
     */
    public void setServerURL(String serverURL) {
        this.serverURL = serverURL;
    }
    /**
     * Getter method for User IBAN
     * @return the IBAN of the User
     */
    public String getIBAN() {
        return IBAN;
    }

    /**
     * Setter method for an User's IBAN
     * @param IBAN new IBAN of the User
     * @throws IBANFormatException
     */
    public void setIBAN(String IBAN) throws IBANFormatException {
        if (IBAN.length() != 34) {
            throw new IBANFormatException();
        }
        this.IBAN = IBAN;
    }
    /**
     * Getter method for User BIC
     * @return the BIC of the User
     */
    public String getBIC() {
        return BIC;
    }

    /**
     * Setter method for an User's BIC
     * @param BIC new BIC of the User
     * @throws BICFormatException
     */
    public void setBIC(String BIC) throws BICFormatException {
        if (BIC.length() != 11) {
            throw new BICFormatException();
        }
        this.BIC = BIC;
    }
    /**
     * Getter method for User's preferred language
     * @return the language of the User
     */
    public Language getLanguage() {
        return language;
    }
    /**
     * Setter method for an User's preferred language
     * @param language new language of the User
     */
    public void setLanguage(Language language) {
        this.language = language;
    }

    /**
     * ToString method for a User
     * @return information of a User in a readable format (excluding the password)
     */
    @Override
    public String toString() {
        return "User Information:\n" +
                "Username: " + username + "\n" +
                "E-mail: " + email + "\n" +
                "Server: " + serverURL + "\n" +
                "IBAN: " + IBAN + "\n" +
                "BIC: " + BIC + "\n" +
                "Preferred Language: " + language +
                "\n";
    }

    static class IBANFormatException extends Exception {

    }

    class BICFormatException extends Exception {
    }

    class EmailFormatException extends Exception {
    }
}
