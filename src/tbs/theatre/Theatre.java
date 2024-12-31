package tbs.theatre;

public class Theatre{
    private int id;
    private String password;
    private String name;
    private String location;

    public Theatre(int id, String password, String name, String location) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.location = location;
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
    public String getLocation() {
        return location;
    }
}