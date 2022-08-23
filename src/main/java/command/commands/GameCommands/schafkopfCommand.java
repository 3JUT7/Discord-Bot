package command.commands.GameCommands;

import command.ICommand;
import minigames.Schafkopf.Schafkopf;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class schafkopfCommand implements ICommand {


    @Override
    public void handle(SlashCommandInteractionEvent event) throws IOException {
/*
        switch (Objects.requireNonNull(event.getSubcommandName())) {
            case "game":

                Member p1 = event.getMember();
                Member p2 = event.getOption("player1", event.getMember(), OptionMapping::getAsMember);
                Member p3 = event.getOption("player2", event.getMember(), OptionMapping::getAsMember);
                Member p4 = event.getOption("player3", event.getMember(), OptionMapping::getAsMember);


                //List<Emote> emotes = event.getJDA().getGuildById(910190811659010079L).getEmotes();

                System.out.println("scvhad");

                Emote[][] emoteSorted = new Emote[4][8];

                for (Emote e : emotes) {

                    String target = e.getName().split("_")[0];

                    int targetID = 0;


                    switch (target) {

                        case "Gras":
                            targetID = 1;
                            break;
                        case "Herz":
                            targetID = 2;
                            break;
                        case "Schell":
                            targetID = 3;
                            break;
                    }


                    System.out.println(targetID);


                    switch (e.getName().replace(target + "_", "")) {
                        case "7":
                            emoteSorted[targetID][0] = e;
                            break;
                        case "8":
                            emoteSorted[targetID][1] = e;
                            break;
                        case "9":
                            emoteSorted[targetID][2] = e;
                            break;
                        case "Unter":
                            emoteSorted[targetID][3] = e;
                            break;
                        case "Ober":
                            emoteSorted[targetID][4] = e;
                            break;
                        case "Koenig":
                            emoteSorted[targetID][5] = e;
                            break;
                        case "10":
                            emoteSorted[targetID][6] = e;
                            break;
                        case "Ass":
                            emoteSorted[targetID][7] = e;
                            break;
                    }
                }

                for (Emote[] e : emoteSorted) {
                    System.out.println(Arrays.toString(e));
                }
                new Schafkopf(p1, p2, p3, p4);
        }*/
    }

    @Override
    public String getName() {
        return "schafkopf";
    }

    @Override
    public String getHelp() {
        return "The commnand for everything around Schafkopf";
    }

    @Override
    public CommandData getCommandData() {

        List<Command.Choice> choices = new ArrayList<>();

        choices.add(new Command.Choice("startGame", "start Game"));

        return Commands.slash(this.getName(), this.getHelp())
                .addSubcommands(new SubcommandData("game", "Start a game with three other members")
                        .addOptions(List.of(
                                        new OptionData(OptionType.USER, "player1", "The user you want to challenge", true),
                                        new OptionData(OptionType.USER, "player2", "The second user you want to challenge", true),
                                        new OptionData(OptionType.USER, "player3", "The third user you want to challenge", true)
                                )

                        )
                );
    }
}
