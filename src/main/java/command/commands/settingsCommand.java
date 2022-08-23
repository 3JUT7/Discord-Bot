package command.commands;

import command.ICommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public abstract class settingsCommand implements ICommand {

    public void handle(SlashCommandInteractionEvent event) {

    }

    public String getName() {
        return "settings";
    }

    public String getHelp() {
        return null;
    }

}
