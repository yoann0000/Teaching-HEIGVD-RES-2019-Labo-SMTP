package mailBot.smtp;

import mailBot.model.Message;

import java.io.IOException;

public interface ISMTPClient {

    public void sendMessage(Message message) throws IOException;
}
