package listeners;

import net.dv8tion.jda.api.events.user.update.UserUpdateOnlineStatusEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import static functions.userTracker.userTracker.newOnlineStatus;

public class OnlineListener extends ListenerAdapter {
    @Override
    public void onUserUpdateOnlineStatus(UserUpdateOnlineStatusEvent event){

        newOnlineStatus(event);



    }
}
