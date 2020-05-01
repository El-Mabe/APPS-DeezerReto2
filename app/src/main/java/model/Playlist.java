package model;

public class Playlist {

    private String name;
    private String user_creator;
    private String num_items;
    private String description;
    private String num_fans;


    public Playlist() {
    }

    public Playlist(String name, String user_creator, String num_items, String description, String num_fans) {
        this.name = name;
        this.user_creator = user_creator;
        this.num_items = num_items;
        this.description = description;
        this.num_fans = num_fans;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser_creator() {
        return user_creator;
    }

    public void setUser_creator(String user_creator) {
        this.user_creator = user_creator;
    }

    public String getNum_items() {
        return num_items;
    }

    public void setNum_items(String num_items) {
        this.num_items = num_items;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNum_fans() {
        return num_fans;
    }

    public void setNum_fans(String num_fans) {
        this.num_fans = num_fans;
    }
}
