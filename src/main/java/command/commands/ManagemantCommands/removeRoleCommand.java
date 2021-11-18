package command.commands.ManagemantCommands;

import command.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import util.Config;

import java.util.List;

public class removeRoleCommand implements ICommand {

    public void handle(SlashCommandEvent event) {

        List<Member> members;


        Member member = event.getMember();
        Guild guild = event.getGuild();
        Role role_to_have = event.getOption("role_to_have").getAsRole();
        Role role_to_get = event.getOption("role_to_remove").getAsRole();


        if (role_to_have.getName().equals("@everyone")){
            members = guild.getMembers();
        }else {
            members = guild.getMembersWithRoles(role_to_have);
        }

        if ((member.getPermissions().contains(Permission.MANAGE_ROLES) || member.getId().equals(Config.ownerId))){
            for (Member m : members) {
                if (!m.getUser().isBot()){

                    guild.removeRoleFromMember(m, role_to_get).queue();
                }
            }
        }

        event.reply("removed the role from the members").queue();
    }

    public String getName() {
        return "removerole";
    }

    public String getHelp() {
        return "remove Role from all members";
    }

    public String getCategory() {
        return "UtilityCmd";
    }

    public List<String> getAliases() {
        return List.of("rR");
    }

    public Permission getPermission() {
        return null;
    }

    public CommandData getCommandData() {
        return new CommandData(this.getName(), this.getHelp()).addOptions(List.of(
                new OptionData(OptionType.ROLE, "role_to_have", "Role that the member needs to have to remove the other roles").setRequired(true),
                new OptionData(OptionType.ROLE, "role_to_remove", "Role that the member gets").setRequired(true)
        )).setDefaultEnabled(false);
    }
}
