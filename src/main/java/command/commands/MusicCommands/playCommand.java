package command.commands.MusicCommands;


import command.ICommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.util.List;

public class playCommand implements ICommand {
    public void handle(SlashCommandInteractionEvent event) {


    }

    public String getName() {
        return "play";
    }

    public String getHelp() {
        return "Connects to your current voicechannel if not connected and plays music by link or by yt search";
    }

    public List<String> getAliases() {
        return List.of("p");
    }


    public CommandData getCommandData() {
        return Commands.slash(this.getName(), this.getHelp())
                .addOption(OptionType.STRING,"Song name or url","the name of the song the bot should play")
                .setDefaultPermissions(DefaultMemberPermissions.DISABLED);
    }

    /*

    public List<OptionData> getOptionData() {
        return List.of(
                new OptionData(OptionType.STRING, "link/query", "Plays a song with the given name or url.")

        );

    }

     */

    public String getButtonPrefix() {
        return "none";
    }

}
