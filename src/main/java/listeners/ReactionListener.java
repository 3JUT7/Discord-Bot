package listeners;

import command.commands.QuizduellCommands.QuizduellCommand;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import static core.DiscordBot.jda;

public class ReactionListener extends ListenerAdapter {

    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event){
        Message msg = event.retrieveMessage().complete();

        MessageEmbed msgEmbed = msg.getEmbeds().get(0);

        if (event.getMember().getUser().equals(jda.getSelfUser())){
            return;
        }

        if (msg.getEmbeds().size() == 1){
            if (msgEmbed.getTitle().equals("Should this question be added?")){
                if (msg.getAuthor().equals(jda.getSelfUser())){



                    //msg.getReactions().stream().filter(messageReaction -> messageReaction.getReactionEmote().getName().equals("upvote"));

                    QuizduellCommand.quizduellReactionEventHandler(event);


                }
            }

        }
    }
}