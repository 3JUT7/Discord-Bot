package listeners;

import command.ICommand;
import command.commands.UtilityCommands.helpCommand;
import core.CommandManager;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;



public class ButtonListener extends ListenerAdapter {
    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {

        String componentId = event.getComponentId();

        ICommand command  = CommandManager.commands.
                stream()
                .filter(cmd -> componentId.startsWith(cmd.getButtonPrefix())).findFirst().get();

        command.ButtonAction(event);


    }
}
