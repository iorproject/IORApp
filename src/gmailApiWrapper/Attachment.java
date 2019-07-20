package gmailApiWrapper;

public class Attachment {

    private final String name;
    private final String id;
    private final FileFormat type;


    public Attachment(String name, String id, FileFormat format) {

        this.name = name;
        this.id = id;
        this.type = format;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public FileFormat getType() {
        return type;
    }
}
