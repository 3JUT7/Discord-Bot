package command.commands.ManagemantCommands;

import command.ICommand;
import net.dv8tion.jda.api.entities.Channel;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static java.lang.Math.toIntExact;

public class purgeCommand implements ICommand {

    @Override
    public void handle(SlashCommandInteractionEvent event) {

        Map<Member, Integer> userList = new HashMap<Member, Integer>(); //List of how many Messages of the deleted ones every Member has send

        List<Message> messages = new ArrayList<>();

        TextChannel channel = (TextChannel) event.getChannel();
        Integer amount = toIntExact(event.getOption("amount").getAsLong());

        int i = amount;

        int deleteMessages = 0;

        for (Message message: channel.getIterableHistory().cache(false)) {
            if (!message.isPinned()){

                if (userList.get(message.getMember()) == null){
                    userList.put(message.getMember(), 1);
                }else {
                    userList.put(message.getMember(),userList.get(message.getMember()) + 1);
                }


                messages.add(message);
                deleteMessages = deleteMessages + 1;
            }
            if (--i <= 0) break;
        }

        channel.purgeMessages(messages);

        StringBuilder sb = new StringBuilder();

        sb.append(deleteMessages + " messages were deleted \n \n");

        for (Member member:userList.keySet()) {
            sb.append("**" + member.getEffectiveName() + ":** " + userList.get(member) + "\n");
        }

        event.reply(sb.toString()).queue(message -> {
            message.deleteOriginal().queueAfter(5, TimeUnit.SECONDS);
        });



    }

    @Override
    public String getName() {
        return "purge";
    }

    @Override
    public String getHelp() {
        return "purge messages sorted by Member or amount";
    }

    @Override
    public CommandData getCommandData() {
        return Commands.slash(this.getName(), this.getHelp())
                .addOption(OptionType.INTEGER,"amount","amount of messages to purge", true)
                .setDefaultPermissions(DefaultMemberPermissions.DISABLED);

    }

    @Override
    public String getButtonPrefix() {
        return "none";
    }
}
