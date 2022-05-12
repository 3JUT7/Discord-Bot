package command.commands.QuizduellCommands;

import command.ICommand;
import core.LiteSQL;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;

import java.awt.*;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class QuizduellCommand implements ICommand {

    @Override
    public void handle(SlashCommandInteractionEvent event) throws IOException {

        switch (Objects.requireNonNull(event.getSubcommandName())) {
            case "new_question":
                questionRequest(new String[]{
                        event.getOption("question").getAsString(),
                        event.getOption("correct_answer").getAsString(),
                        event.getOption("wrong_answer_1").getAsString(),
                        event.getOption("wrong_answer_2").getAsString(),
                        event.getOption("wrong_answer_3").getAsString(),
                }, event);
                break;
            case "challenge":
                challengePlayer(event);

        }


    }

    public void challengePlayer(SlashCommandInteractionEvent event){
        User user = Objects.requireNonNull(event.getOption("user")).getAsUser();

        event.reply(user.getAsMention() + ", " + event.getMember().getAsMention() + "is challenging your knowledge are you better than him/her?").addActionRow(
                Button.success(this.getName() + "_yes","Yes (0)"),
                Button.danger(this.getName() + "_no","No (0)")
        ).queue();


    }

    public void questionRequest(String[] parameters, SlashCommandInteractionEvent event) {

        MessageChannel channel = event.getChannel();

        ResultSet set = LiteSQL.onQuery("SELECT question FROM quizduell_questions WHERE question = '" + parameters[0] + "'");

        try {
            if (set.next()) {

                event.reply("This question already exists").queue();

                return;
            }

            set.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println(Arrays.toString(parameters));

        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Should this question be added?");
        builder.setDescription(parameters[0]);
        builder.setColor(new Color(200, 40, 243));

        builder.addField("Right Answer", parameters[1], true);
        builder.addField("Wrong Answers", parameters[2] + "\n" + parameters[3] + "\n" + parameters[4], true);

        event.replyEmbeds(builder.build())

                .queue(embed -> {
                    embed.retrieveOriginal().queue(msg -> {
                        msg.addReaction("upvote:953913306920402954").queue();
                        msg.addReaction("downvote:953913144332402698").queue();
                    });

                });

    }

    public static void quizduellReactionEventHandler(MessageReactionAddEvent event) {

        Message msg = event.retrieveMessage().complete();

        MessageEmbed msgEmbed = msg.getEmbeds().get(0);

        String question = msgEmbed.getDescription();
        String correct_answer = msgEmbed.getFields().get(0).getValue();

        String[] wrong_answers = Objects.requireNonNull(msgEmbed.getFields().get(1).getValue()).split("\\r?\\n");

        System.out.println(Arrays.toString(wrong_answers));


        MessageReaction react = msg.getReactions().stream().filter(messageReaction -> messageReaction.getReactionEmote().getName().equals("upvote")).findFirst().get();


        if (react.getCount() > 1) {
            System.out.println("running SQL");

            ResultSet set = LiteSQL.onQuery("SELECT question FROM quizduell_questions WHERE question = '" + question + "'");

            try {
                if (set.next()) {

                    msg.delete().queue();

                    return;
                } else {
                    LiteSQL.onUpdate("INSERT INTO quizduell_questions (question, correct_answer, wrong_answer_1, wrong_answer_2, wrong_answer_3) VALUES ('" + question + "', '" + correct_answer + "', '" + wrong_answers[0] + "', '" + wrong_answers[1] + "', '" + wrong_answers[2] + "');");
                }

                set.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }


    @Override
    public String getName() {
        return "quizduell";
    }

    @Override
    public String getHelp() {
        return "testtstststst";
    }

    @Override
    public CommandData getCommandData() {
        return Commands.slash(this.getName(), this.getHelp())
                .setDefaultEnabled(true)
                .addSubcommands(new SubcommandData("challenge", "Challenge another member for a game")
                        .addOptions(List.of(
                                        new OptionData(OptionType.USER, "user", "The user you want to challenge", true),
                                new OptionData(OptionType.ATTACHMENT,"atra","test")
                                )

                        )
                )
                .addSubcommands(new SubcommandData("new_question", "Make a request to add a new question")
                        .addOptions(List.of(
                                        new OptionData(OptionType.STRING, "question", "The question you want to add", true),
                                        new OptionData(OptionType.STRING, "correct_answer", "The first wrong answer to your Question", true),
                                        new OptionData(OptionType.STRING, "wrong_answer_1", "The second wrong answer to your Question", true),
                                        new OptionData(OptionType.STRING, "wrong_answer_2", "The third wrong answer to your Question", true),
                                        new OptionData(OptionType.STRING, "wrong_answer_3", "The fourth wrong answer to your Question", true)


                                )
                        )
                );
    }

    @Override
    public String getButtonPrefix() {
        return this.getName();
    }

    @Override
    public void ButtonAction(ButtonInteractionEvent event) {
        event.deferEdit().queue();

        String label = event.getButton().getLabel();

        int count = Integer.parseInt(String.valueOf(label.charAt(label.length() - 2))) + 1;

        label = label.substring(0, label.length() - 2);

        System.out.println(label);

        if (event.getComponent().getStyle().equals(ButtonStyle.SUCCESS)) {
            event.editButton(Button.success(event.getButton().getId(), label + count + ")")).queue();
        } else {
            event.editButton(Button.danger(event.getButton().getId(), label + count + ")")).queue();

        }


    }
}
