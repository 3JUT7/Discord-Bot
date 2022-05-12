package command.commands.AdminCommands;

import command.ICommand;
import core.CommandManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.privileges.CommandPrivilege;
import net.dv8tion.jda.internal.interactions.CommandDataImpl;
import org.apache.commons.io.filefilter.FalseFileFilter;
import util.Config;

import java.util.List;

import static core.DiscordBot.jda;

public class updateCommand implements ICommand {
    @Override
    public void handle(SlashCommandInteractionEvent event) {


        Guild guild = event.getGuild();

        event.deferReply(true).queue();
        event.getHook().editOriginal("updating all commands know").queue();

        boolean delete = false;
        try {
             delete = event.getOption("delete").getAsBoolean();
        }catch (NullPointerException ignored){}

        updateCommands(guild, delete);

        event.getHook().editOriginal("updated all commands successfully").queue();


    }

    public void updateCommands(Guild guild, boolean delete) {
        if (delete) {
            guild.retrieveCommands().queue(commands -> {

                for (Command cmd : commands) {
                    cmd.delete().queue();
                }

            });
        }


        for (ICommand cmd : CommandManager.commands) {

            guild.upsertCommand(cmd.getCommandData()).queue();

        }


        CommandData cmd = Commands.slash("trest", "test");

    }

    @Override
    public String getName() {
        return "updatecommands";
    }

    @Override
    public String getHelp() {
        return "Update all Slash Commands";
    }

    @Override
    public Permission getPermission() {
        return null;
    }

    @Override
    public CommandData getCommandData() {
        return Commands.slash(this.getName(), this.getHelp())
                .setDefaultEnabled(false)
                .addOptions(new OptionData(OptionType.BOOLEAN,"delete","delete all commands?",false));
    }

    public String getButtonPrefix() {
        return "none";
    }


}
