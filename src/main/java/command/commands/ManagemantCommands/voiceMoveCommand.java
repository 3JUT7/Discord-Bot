package command.commands.ManagemantCommands;

import command.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.List;

public class voiceMoveCommand implements ICommand {
    public void handle(SlashCommandInteractionEvent event) {



        Member member = event.getMember();
        MessageChannel channel = event.getChannel();
        Guild guild = event.getGuild();

        AudioChannel channelMoveFrom = (AudioChannel) event.getOption("channelfrom").getAsGuildChannel();
        AudioChannel channelMoveTo = (AudioChannel) event.getOption("channelto").getAsGuildChannel();


        if (!member.hasPermission(Permission.VOICE_MOVE_OTHERS)){
            event.reply("**This Command requires you to have the `Move Members` permission to use it**").queue();
            return;
        }


        event.reply("Moving everyone from " + channelMoveFrom.getName() + " to " + channelMoveTo.getName()).queue();


        if (!channelMoveFrom.getType().equals(ChannelType.VOICE)){
            event.reply("You need to set an Voicechannel to move from").queue();
        }

        if (!channelMoveTo.getType().equals(ChannelType.VOICE)){
            event.reply("You need to set an Voicechannel to move from").queue();
        }

        for (Member m : channelMoveFrom.getMembers()) {
           guild.moveVoiceMember(m, (VoiceChannel) channelMoveTo).queue();
        }






    }

    public String getName() {
        return "voicemove";
    }

    public String getHelp() {
        return "Moves all Users of a Voice to another Voice";
    }

    public CommandData getCommandData() {
        return Commands.slash(this.getName(), this.getHelp()).addOptions(List.of(
                new OptionData(OptionType.CHANNEL, "channelfrom", "The Channel where to move the Members from. Needs to be a voice channel").setRequired(true),
                new OptionData(OptionType.CHANNEL, "channelto", "The Channel where to move the Members to. Needs to be a voice channel").setRequired(true)

            )
        ).setDefaultEnabled(false);
    }

    public Permission getPermission() {
        return null;
    }

    public String getButtonPrefix() {
        return "none";
    }
}
