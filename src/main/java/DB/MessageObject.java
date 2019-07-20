package main.java.DB;

public class MessageObject {
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    private int id;
    private String name;

    public MessageObject(int id, String name){
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "MessageObject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
