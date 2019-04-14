package mailBot;

import mailBot.config.ConfigurationManager;
import mailBot.config.IConfigurationManager;
import mailBot.model.prank.Prank;
import mailBot.model.prank.PrankGenerator;
import mailBot.smtp.ISMTPClient;
import mailBot.smtp.SMTPClient;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            IConfigurationManager configurationManager = new ConfigurationManager();
            PrankGenerator prankGenerator = new PrankGenerator(configurationManager);

            ISMTPClient smtpClient =
                    new SMTPClient(configurationManager.getSmtpServerAdress(), configurationManager.getSmtpServerPort());

            List<Prank> pranks = prankGenerator.generatePranks();

            for (Prank prank : pranks) {
                smtpClient.sendMessage(prank.generateMailMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}