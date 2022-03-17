package command.commands.Quizduell;

import command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class QuizduellCommand implements ICommand {

    @Override
    public void handle(SlashCommandInteractionEvent event) throws IOException {


        System.out.println("quizing");


        if (Objects.equals(event.getSubcommandName(), "new_question")){
            questionRequest(new String[]{
                    event.getOption("question").getAsString(),
                    event.getOption("correct_answer").getAsString(),
                    event.getOption("wrong_answer_1").getAsString(),
                    event.getOption("wrong_answer_2").getAsString(),
                    event.getOption("wrong_answer_3").getAsString(),
            }, event.getMessageChannel());
            event.reply("ja ne muss noch machen").queue();
        }


    }


    public void questionRequest(String[] args, MessageChannel channel){
        System.out.println(Arrays.toString(args));

        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Should this question be added");
        builder.setDescription(args[0]);
        builder.setColor(new Color(200, 40, 243));

        builder.addField("Right Answer", args[1],true);
        builder.addField("Wrong Answers", args[2] + "\n" + args[3] + "\n" + args[4],true);

        channel.sendMessageEmbeds(builder.build())
                .setActionRow(
                        Button.success(this.getName() + "_yes","Yes (0)"),
                        Button.danger(this.getName() + "_no","No (0)")

                )
                .queue();
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

        if (event.getComponent().getStyle().equals(ButtonStyle.SUCCESS)){
            event.editButton(Button.success(event.getButton().getId(),label + count + ")")).queue();
        } else{
            event.editButton(Button.danger(event.getButton().getId(),label + count + ")")).queue();

        }



    }
}
