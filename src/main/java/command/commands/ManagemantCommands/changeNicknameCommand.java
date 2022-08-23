package command.commands.ManagemantCommands;


import command.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.List;

public class changeNicknameCommand implements ICommand {

    public void handle(SlashCommandInteractionEvent event) {

        Role role = event.getOption("role").getAsRole();
        String nickname = event.getOption("nickname").getAsString();


        MessageChannel channel = event.getChannel();
        Guild guild = event.getGuild();

        for (Member member : guild.getMembersWithRoles(role)) {
            member.modifyNickname(nickname).queue();
        }
    }

    public String getName() {
        return "changenickname";
    }

    public String getHelp() {
        return "Change the Nickname of all Members with a role ";
    }

    @Override
    public CommandData getCommandData() {
        return Commands.slash(this.getName(), this.getHelp())
                .setDefaultPermissions(DefaultMemberPermissions.DISABLED)
                .addOptions(List.of(
                        new OptionData(OptionType.ROLE,"role", "The Role the user needs to have to get his nickname changed",true),
                        new OptionData(OptionType.STRING,"nickname","The nickname the members get", true)
                ));
    }

    public String getButtonPrefix() {
        return "none";
    }
}
