package command.commands.AdminCommands;

import command.ICommand;
import core.CommandManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;
import java.util.List;

public class updateCommand implements ICommand {
    @Override
    public void handle(SlashCommandInteractionEvent event) {


        Guild guild = event.getGuild();

        event.deferReply(true).queue();
        event.getHook().editOriginal("updating all commands").queue();

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

        List<CommandData> commandDataList = new ArrayList<>();

        for (ICommand cmd : CommandManager.commands) {
            commandDataList.add(cmd.getCommandData());
            System.out.println(cmd.getName());
        }
        guild.updateCommands().addCommands(commandDataList).submit();

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
    public CommandData getCommandData() {
        return Commands.slash(this.getName(), this.getHelp())
                .setDefaultPermissions(DefaultMemberPermissions.ENABLED)
                .addOptions(new OptionData(OptionType.BOOLEAN,"delete","delete all commands?",false));
    }

    public String getButtonPrefix() {
        return "none";
    }


}
