package core;

import command.ICommand;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import net.dv8tion.jda.api.interactions.commands.privileges.CommandPrivilege;
import util.Config;

import java.util.List;

import static core.DiscordBot.jda;

public class ReadyListener extends ListenerAdapter {
    @Override
    public void onReady(ReadyEvent event) {

    }
}
