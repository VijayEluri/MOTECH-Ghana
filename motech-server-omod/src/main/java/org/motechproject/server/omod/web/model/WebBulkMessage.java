package org.motechproject.server.omod.web.model;

public class WebBulkMessage {

    private String content;
    private String recipients;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRecipients() {
        return recipients;
    }

    public void setRecipients(String recipients) {
        this.recipients = recipients;
    }

    public String content() {
        return content;
    }

    public String recipients() {
        return recipients;
    }

}
