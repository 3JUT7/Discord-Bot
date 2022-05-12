package functions;

import core.LiteSQL;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class stickyRoles {
    public static void guildMemberJoin(GuildMemberJoinEvent event) {

        Guild guild = event.getGuild();
        Member member = event.getMember();

        long guildId = guild.getIdLong();
        long memberId = member.getIdLong();
        List<Long> roleIds = new ArrayList<Long>();

        if (member.getUser().isBot()){
            return;
        }




        ResultSet set = LiteSQL.onQuery("SELECT stickyRoles FROM guildSettings WHERE guildId = " + guildId);


        try {
            assert set != null;
            if (set.getInt("stickyRoles") == 0){
                return;
            }
            set.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }



        set = LiteSQL.onQuery("SELECT roleId FROM stickyRoles WHERE guildId = " + guildId + " AND memberId = " + memberId);

        try {

            while (set.next()){
                roleIds.add(set.getLong("roleId"));
            }


            set.close();
        }catch (SQLException e){
            e.printStackTrace();
        }

        for (Long roleId:roleIds) {
            Role role = guild.getRoleById(roleId);

            guild.addRoleToMember(member, role).queue();
        }

    }


    public static void guildMemberLeave(GuildMemberRemoveEvent event){
        Long memberId = event.getMember().getIdLong();
        Long guildId = event.getGuild().getIdLong();

        System.out.println("leaving");
        LiteSQL.onUpdate("DELETE FROM stickyRoles WHERE memberId='" + memberId + "' AND guildId='" + guildId + "'");

        List<Role> roles = event.getMember().getRoles();

        for (Role r : roles) {
            LiteSQL.onUpdate("INSERT INTO stickyRoles (guildId, memberId, roleId) VALUES(" + guildId + ", " + memberId + ", " + r.getIdLong() + ")");
        }

    }

}
