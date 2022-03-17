package listeners;

import core.CommandManager;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Config;

import java.io.IOException;

public class SlashCommandListener extends ListenerAdapter  {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageListener.class);
    public static CommandManager manager = new CommandManager();


    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        User user = event.getUser();

        if (user.isBot()) {
            return;
        }

        String prefix = Config.prefix;
        String raw = event.getName();

        try {
            manager.handleSlashCommand(event);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
