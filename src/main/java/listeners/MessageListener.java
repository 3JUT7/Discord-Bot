package listeners;

        import command.ICommand;
        import core.CommandManager;
        import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
        import net.dv8tion.jda.api.interactions.commands.privileges.CommandPrivilege;
        import net.dv8tion.jda.api.entities.*;
        import net.dv8tion.jda.api.hooks.ListenerAdapter;

        import util.Config;

        import java.util.List;
        import java.util.Objects;

        import static core.DiscordBot.jda;

public class MessageListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        Guild guild = event.getGuild();

        if (event.getMessage().getContentDisplay().contains("update")){

            if (event.getAuthor().getId().equals(Config.ownerId)){
                System.out.println("updating all commands");
                for (ICommand cmd: CommandManager.commands) {

                    System.out.println(cmd.getName());

                    guild.upsertCommand(cmd.getCommandData()).queue();
                }

            }
        }
    }
}