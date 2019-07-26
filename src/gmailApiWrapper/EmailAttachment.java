package gmailApiWrapper;

public abstract class EmailAttachment {

    protected final String name;
    protected final String id;
    protected final FileFormat type;
    protected byte[] bytesData = null;
    protected String stringData = null;


    public EmailAttachment(String name, String id, FileFormat format) {

        this.name = name;
        this.id = id;
        this.type = format;
    }

    public String getName() {
        return name;
    }

    public FileFormat getType() {
        return type;
    }

    public byte[] getBytes() {

        return this.bytesData;
    }

    public String getString() {
        return stringData;
    }
}
