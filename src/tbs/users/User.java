package tbs.users;

public abstract class User {
    private int id;
    private String password;
    private String name;

    public User(int id, String password, String name) {
        this.id = id;
        this.password = password;
        this.name = name;
    }
    public int getId() {
        return id;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
