package mailBot.smtp;

import mailBot.model.Message;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class SMTPClient implements ISMTPClient {
    private static final Logger LOG = Logger.getLogger(SMTPClient.class.getName());

    private String smtpServerAddress;
    private int smtpServerPort = 25;

    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;

    public SMTPClient(String smtpServerAddress, int port) {
        this.smtpServerAddress =smtpServerAddress;
        this.smtpServerPort = port;
    }

    @Override
    public void sendMessage(Message message) throws IOException {
        LOG.info("Sending message via SMTP");
        Socket socket = new Socket(smtpServerAddress, smtpServerPort);
        writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        String line = reader.readLine();
        LOG.info(line);
        writer.printf("EHLO localhost\r\n");
        line = reader.readLine();
        LOG.info(line);
        if (!line.startsWith("250")) {
            throw new IOException("SMTP error: " + line);
        }

        while (line.startsWith("250-")) {

        }
        //TODO
    }
}
