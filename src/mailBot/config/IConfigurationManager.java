package mailBot.config;

import mailBot.model.mail.Person;

import java.util.List;

public interface IConfigurationManager {
    List<Person> getVictims();

    List<String> getMessages();
}
