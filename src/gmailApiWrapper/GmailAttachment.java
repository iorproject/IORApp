package gmailApiWrapper;

import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class GmailAttachment extends EmailAttachment {

    private GmailApiWrapper gmailApiWrapper;
    private String emailMessageId;


    public GmailAttachment(String name, String id, FileFormat format) {

        super(name, id, format);
    }


    public void setEmailApiWrapper(GmailApiWrapper gmailApiWrapper) {
        this.gmailApiWrapper = gmailApiWrapper;
    }

    public void setGmailMessage(String emailMessageId) {
        this.emailMessageId = emailMessageId;
    }

    @Override
    public byte[] getBytes() {

        if (this.bytesData == null) {

            this.bytesData = gmailApiWrapper.getAttachmentBytes(this.id, emailMessageId);
        }

        return this.bytesData;

    }

    @Override
    public String getString() {

        if (this.bytesData == null)
            this.bytesData = gmailApiWrapper.getAttachmentBytes(this.id, emailMessageId);

        if (this.stringData == null) {

            this.stringData = parseBytesToString();
        }

        return  this.stringData;
    }


    private String parseBytesToString() {

        String content = null;
        Tika tikaParser = new Tika();
        Metadata metadata = new Metadata();
        tikaParser.setMaxStringLength(-1);

        InputStream inputStream = new ByteArrayInputStream(this.bytesData);
        try {
            content = tikaParser.parseToString(inputStream, metadata);
            content = content.replaceAll("\r", "")
                    .replaceAll("\t", "")
                    .replaceAll("\n +", "\n")
                    .replaceAll("\n+", "\n");
        } catch (Exception e) {

        }

        return content;
    }
}
