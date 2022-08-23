package command.commands.UtilityCommands;

import command.ICommand;
import core.CommandManager;
import net.dv8tion.jda.api.EmbedBuilder;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.components.selections.SelectMenu;
import util.Config;

import java.util.ArrayList;
import java.util.List;

public class helpCommand implements ICommand{

    private final CommandManager manager;

    public helpCommand(CommandManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(SlashCommandInteractionEvent event) {

        EmbedBuilder builder = new EmbedBuilder();

        SelectMenu menu;

        SelectMenu.Builder selectionMenuBuilder = SelectMenu.create("menu:class")
                .setPlaceholder("Select your Command to get help");


        for (ICommand cmd:CommandManager.commands) {
            selectionMenuBuilder.addOption(cmd.getName(),"help_" + cmd.getName());
        }

        menu = selectionMenuBuilder.build();

        if (event.getOption("cmd") != null){
            String search = event.getOption("cmd").getAsString();
            ICommand command = manager.getCommand(search);
            event.reply(command.getHelp() + "   ").queue();

        }

        else{
            builder
                    .setTitle("All Commands of L11on")
                    .setColor(10181046)
                    .setDescription("Use /help <command> for specific command help")
                    .setFooter("Bot managed by " + event.getJDA().getUserById(Config.ownerId).getAsTag(),event.getJDA().getUserById(Config.ownerId).getAvatarUrl());



            event.replyEmbeds(builder.build()).addActionRow(menu).queue();

        }




    }

    public String getName() {
        return "help";
    }

    public String getHelp() {
        return "Shows the list with commands in the bot\n" +
                "Usage: `!!help [command]`";
    }

    @Override
    public CommandData getCommandData() {

        List<Command.Choice> choices = new ArrayList<>();
        for (ICommand cmd:CommandManager.commands) {
            choices.add(new Command.Choice(cmd.getName(), cmd.getName()));
        }

        return Commands.slash(this.getName(), this.getHelp())
                                .addOptions(List.of(
                                        new OptionData(OptionType.STRING,"command","Get help to the specified Command", false)
                                                .addChoices(choices))
                                )

                .setDefaultPermissions(DefaultMemberPermissions.ENABLED);
    }

    @Override
    public String getButtonPrefix() {
        return "none";
    }


}
