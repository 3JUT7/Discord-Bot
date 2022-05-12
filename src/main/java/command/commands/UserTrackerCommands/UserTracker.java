package command.commands.UserTrackerCommands;

import command.ICommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

import java.io.IOException;

public class UserTracker implements ICommand {

    @Override
    public void handle(SlashCommandInteractionEvent event) throws IOException {

    }

    @Override
    public String getName() {
        return "userTracker";
    }

    @Override
    public String getHelp() {
        return null;
    }

    @Override
    public CommandData getCommandData() {
        return null;
    }


}
