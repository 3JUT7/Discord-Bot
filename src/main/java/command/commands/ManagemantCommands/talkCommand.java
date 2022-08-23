package command.commands.ManagemantCommands;

import command.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.AudioChannel;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.util.ArrayList;
import java.util.List;

public class talkCommand implements ICommand {

    private static List<String > buttonNames = new ArrayList<String>();

    @Override
    public void handle(SlashCommandInteractionEvent event) {
        if (!event.getMember().getVoiceState().inAudioChannel()){
            event.reply("You need to be in a Voicechannel to use this Command").queue();
        }

        AudioChannel channel = event.getMember().getVoiceState().getChannel();
        List<Member>  members = channel.getMembers();

        for (Member m:members) {
            if (!m.hasPermission(Permission.VOICE_MUTE_OTHERS)){
                m.mute(true).queue();
            }
        }
        event.reply("muted everyone in the voice channel").addActionRow(Button.success(this.getName() + channel.getId(), "press to unmute everyone")).queue();
        buttonNames.add(this.getName() + channel.getId());
    }

    @Override
    public void ButtonAction(ButtonInteractionEvent event) {

        Guild guild = event.getGuild();
        String componentId = event.getComponentId();
        componentId = componentId.replace(this.getName(),"");
        VoiceChannel channel = guild.getVoiceChannelById(componentId);
        List<Member>  members = channel.getMembers();

        for (Member m:members) {
            if (!m.hasPermission(Permission.VOICE_MUTE_OTHERS)){
                m.mute(false).queue();
            }
        }

        event.reply("unmuted everybody").queue();

    }

    @Override
    public String getName() {
        return "talk";
    }

    @Override
    public String getHelp() {
        return "Mute everyone in the voicechannel excluding Members that could entmute theirselfs";
    }

    @Override
    public CommandData getCommandData() {
        return Commands.slash(getName(), getHelp())
                .setDefaultPermissions(DefaultMemberPermissions.DISABLED);
    }

    @Override
    public String getButtonPrefix(){
        return getName();
    }




}
