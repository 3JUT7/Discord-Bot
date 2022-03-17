package command.commands.MusicCommands;

import command.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.util.List;


public class joinCommand implements ICommand {
    @Override
    public void handle(SlashCommandInteractionEvent event) {

        Guild guild = event.getGuild();
        Member member = event.getMember();
        MessageChannel channel = event.getChannel();

        if (member.getVoiceState().inAudioChannel()){

            AudioChannel audioChannel = member.getVoiceState().getChannel();
            guild.getAudioManager().openAudioConnection(audioChannel);
        }else {
            channel.sendMessage("**You have to be in a voice channel to use this command.**").queue();
        }

    }

    public String getName() {
        return "join";
    }

    public String getHelp() {
        return null;
    }

    public List<String> getAliases() {
        return List.of("summon");
    }

    public Permission getPermission() {
        return null;
    }

    public CommandData getCommandData() {
        return Commands.slash(this.getName(), this.getHelp());
    }

    public String getButtonPrefix() {
        return "none";
    }
}
