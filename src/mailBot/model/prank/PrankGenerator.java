package mailBot.model.prank;

import mailBot.config.ConfigurationManager;
import mailBot.config.IConfigurationManager;
import mailBot.model.mail.Person;
import mailBot.model.mail.Group;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Logger;

public class PrankGenerator {
    private IConfigurationManager configurationManager;

    public PrankGenerator(IConfigurationManager configurationManager){
        this.configurationManager = configurationManager;
    }

    public List<Prank> generatePranks(){
        List<Prank> pranks = new ArrayList<>();

        List<String> messages = configurationManager.getMessages();
        int messageIndex = 0;

        int numberOfGroups = configurationManager.getNumberOfGroups();
        int numberOfVictims = configurationManager.getVictims().size();

        // 3 victimes par groupe minimum
        if(numberOfVictims / numberOfGroups < 3){
            numberOfGroups = numberOfVictims / 3;
            LOG.warning("Pas assez de victimes afin de générer le nombre désiré de groupe. On ne peut générer qu'un" +
                    " nombre maximal de " + numberOfGroups + " groupes afin d'avoir au minimum 3 victimes par groupe");
        }

        List<Group> groups = generateGroups(configurationManager.getVictims(), numberOfGroups);
        for(Group group : groups){
            Prank prank = new Prank();

            List<Person> victims = group.getMembers();
            Collections.shuffle(victims);
            Person sender = victims.remove(0);
            prank.setVictimSender(sender);
            prank.addVictimRecipient(victims);

            prank.addWitnessRecipient(configurationManager.getWitnessesToCC());

            String message = messages.get(messageIndex);
            messageIndex = (messageIndex + 1) % messages.size();
            prank.setMessage(message);

            pranks.add(prank);
        }
        return pranks;
    }
    private static final Logger LOG = Logger.getLogger(PrankGenerator.class.getName());

    public List<Group> generateGroups(List<Person> victims, int numberOfGroups){
        List<Person> availableVictims = new ArrayList<>(victims);
        Collections.shuffle(availableVictims);
        List<Group> groups = new ArrayList<>();
        for(int i = 0; i < numberOfGroups; ++i){
            Group group = new Group();
            groups.add(group);
        }
        int turn = 0;
        Group targetGroup;
        while(availableVictims.size() > 0){
            targetGroup = groups.get(turn);
            turn = (turn + 1) % groups.size();
            Person victim = availableVictims.remove(0);
            targetGroup.addMember(victim);
        }
        return groups;
    }
}
