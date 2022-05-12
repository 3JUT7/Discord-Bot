package core;

import command.ICommand;
import command.commands.AdminCommands.commandSettingsCommand;
import command.commands.AdminCommands.updateCommand;
import command.commands.GameCommands.schafkopfCommand;
import command.commands.ManagemantCommands.*;
import command.commands.QuizduellCommands.QuizduellCommand;
import command.commands.UserTrackerCommands.UserTracker;
import command.commands.UtilityCommands.helpCommand;
import command.commands.UtilityCommands.pingCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import util.Config;

import java.awt.*;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class CommandManager {
    public static List<ICommand> commands = new ArrayList<>();

    public CommandManager(){

        addCommand(new pingCommand());
        addCommand(new helpCommand(this));
        //addCommand(new minigames.g2048());
        addCommand(new voiceMoveCommand());
        addCommand(new changeNicknameCommand());

        addCommand(new addRoleCommand());
        addCommand(new removeRoleCommand());

        addCommand(new purgeCommand());
        addCommand(new updateCommand());
        addCommand(new commandSettingsCommand());
        addCommand(new talkCommand());
        addCommand(new QuizduellCommand());
        //addCommand(new UserTracker());
        addCommand(new schafkopfCommand());

    }

    private void addCommand(ICommand cmd){
        boolean nameFound = commands.stream().anyMatch((it) -> it.getName().equalsIgnoreCase(cmd.getName()));

        if (nameFound){
            throw new IllegalArgumentException("A command with this name is already pressent");
        }

        commands.add(cmd);
    }
    public List<ICommand> getCommands() {
        return commands;
    }

    public ICommand getCommand(final String search){

        return commands
                .stream()
                .filter(cmd -> cmd.getName().equalsIgnoreCase(search))
                .findAny()
                .orElse(null);
    }

    public void handleSlashCommand(SlashCommandInteractionEvent event) throws IOException {
        ICommand cmd = getCommand(event.getName());

        ResultSet set = LiteSQL.onQuery("SELECT enabled FROM commandSettings WHERE commandName = '" + cmd.getName() + "'");


        try {

            assert set != null;
            if (set.isClosed()){
                System.out.println("set is closed");
                LiteSQL.onUpdate("INSERT INTO commandSettings (commandName, enabled) VALUES ('" + cmd.getName() + "', 1);");

            }

            set.next();
            if (set.getInt("enabled") == 0){

                EmbedBuilder builder = new EmbedBuilder();

                builder
                        .setTitle("Command Status")
                        .setDescription(cmd.getName())
                        .setColor(Color.RED)
                        .addField("Disabled","The " + cmd.getName() + " command is currently disabled \n If you think this is wrong contact <@562708005905235978>",false)
                        .setFooter(event.getJDA().getUserById(Config.ownerId).getAsTag(), event.getJDA().getUserById(Config.ownerId).getAvatarUrl());

                event.replyEmbeds(builder.build()).queue();

                set.close();

                return;

            }
            set.close();


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        cmd.handle(event);

    }

}