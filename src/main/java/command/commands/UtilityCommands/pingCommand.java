package command.commands.UtilityCommands;

import command.ICommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

public class pingCommand implements ICommand {
    @Override
    public void handle(SlashCommandInteractionEvent event) {
        JDA jda = event.getJDA();



        event.getChannel().sendMessage("ping ist Kacke ").queue();



    }

    public String getName() {
        return "ping";
    }

    public String getHelp(){
        return "Shows the current Ping from the bot to the discord servers";
    }

    public CommandData getCommandData() {
        return Commands.slash(this.getName(), this.getHelp())

                .setDefaultPermissions(DefaultMemberPermissions.DISABLED);
    }

    public String getButtonPrefix() {
        return "none";
    }

}
