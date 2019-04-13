package mailBot.smtp;

import mailBot.model.mail.Message;

import java.io.IOException;

public interface ISMTPClient {

    public void sendMessage(Message message) throws IOException;
}
