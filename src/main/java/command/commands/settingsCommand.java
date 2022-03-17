package command.commands;

import command.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.List;

public abstract class settingsCommand implements ICommand {

    public void handle(SlashCommandInteractionEvent event) {

    }

    public String getName() {
        return "settings";
    }

    public String getHelp() {
        return null;
    }

    public Permission getPermission() {
        return null;
    }

}
