package command.commands.AdminCommands;

import command.ICommand;
import core.CommandManager;
import core.LiteSQL;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static listeners.SlashCommandListener.manager;

public class commandSettingsCommand implements ICommand {
    @Override
    public void handle(SlashCommandInteractionEvent event) {
        ICommand cmd = manager.getCommand(event.getOption("command").getAsString());
        switch (event.getSubcommandName()){
            case "enable": {
                if (event.getOption("boolean") == null) {

                    ResultSet set = LiteSQL.onQuery("SELECT enabled FROM commandSettings WHERE commandName = '" + cmd.getName() + "'");

                    String status = "null";

                    try {
                        set.next();
                        if (set.getInt("enabled") == 1) {
                            status = "enabled";
                        } else {
                            status = "disabled";
                        }

                        set.close();
                        event.reply("The " + cmd.getName() + " command is currently " + status).queue();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                } else if (event.getOption("boolean") != null){

                    int status = 0;

                    if (event.getOption("boolean").getAsBoolean()){
                        status = 1;
                    }

                    LiteSQL.onUpdate("UPDATE commandSettings SET enabled = '" + status + "' WHERE commandName = '" + cmd.getName() + "';");

                    event.reply("disabled the " + cmd.getName() + "Command").queue();

                }
            }
        }

    }

    @Override
    public String getName() {
        return "command";
    }

    @Override
    public String getHelp() {
        return "Change settings for each command";
    }

    @Override
    public Permission getPermission() {
        return null;
    }

    @Override
    public CommandData getCommandData() {
        List<Command.Choice> choices = new ArrayList<>();
        for (ICommand cmd:CommandManager.commands) {
            choices.add(new Command.Choice(cmd.getName(), cmd.getName()));
        }

        return Commands.slash(this.getName(), this.getHelp())
                .addSubcommands(List.of(
                        new SubcommandData("enable","Enable or disable a command")
                                .addOptions(List.of(
                                    new OptionData(OptionType.STRING,"command","The Command to enable or disable", true)
                                            .addChoices(choices),
                                    new OptionData(OptionType.BOOLEAN,"boolean","boolean if you want to enable the command")
                                ))
                )).setDefaultEnabled(false);
    }

    public String getButtonPrefix() {
        return "none";
    }
}
