package command.commands.ManagemantCommands;


import command.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import util.Config;

import java.util.List;

public class addRoleCommand implements ICommand {
    public void handle(SlashCommandInteractionEvent event) {

        List<Member> members;


        Member member = event.getMember();
        Guild guild = event.getGuild();
        Role role_to_have = event.getOption("role_to_have").getAsRole();
        Role role_to_get = event.getOption("role_to_get").getAsRole();


        if (role_to_have.getName().equals("@everyone")){
            members = guild.getMembers();
        }else {
            members = guild.getMembersWithRoles(role_to_have);
        }

        if ((member.getPermissions().contains(Permission.MANAGE_ROLES) || member.getId().equals(Config.ownerId))){
            for (Member m : members) {
                if (!m.getUser().isBot()){

                    guild.addRoleToMember(m, role_to_get).queue();
                }
            }
        }

        event.reply("added the role to the members").queue();

        
    }

    public String getName() {
        return "addrole";
    }

    public String getHelp() {
        return "add Role to all members";
    }

    public Permission getPermission() {
        return null;
    }

    public CommandData getCommandData() {
        return Commands.slash(this.getName(), this.getHelp()).addOptions(List.of(
                new OptionData(OptionType.ROLE, "role_to_have", "Role that the member needs to have to get the other roles").setRequired(true),
                new OptionData(OptionType.ROLE, "role_to_get", "Role that the member gets").setRequired(true)

            )
        ).setDefaultEnabled(false);
    }

    public String getButtonPrefix() {
        return "none";
    }
}

