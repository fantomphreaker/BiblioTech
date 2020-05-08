public class User {

    private String username;
    private String password;
    private String name;
    private boolean isAdmin;

    User(String uName, String pWord, String name, boolean isAdmin) {
        username = uName;
        password = pWord;
        this.isAdmin = isAdmin;

    }

    String getUsername() {
        return username;
    }

    String getPassword() {
        return password;
    }

    boolean isAdmin() {
        return isAdmin;
    }


}