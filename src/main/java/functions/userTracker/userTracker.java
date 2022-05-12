package functions.userTracker;

import core.LiteSQL;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.user.update.UserUpdateOnlineStatusEvent;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class userTracker {

    public static HashMap<User, Long> lastUserUpdate = new HashMap<User, Long>();


    public static void newOnlineStatus (UserUpdateOnlineStatusEvent event){

        User user = event.getUser();
        long time = System.currentTimeMillis();

        if (!event.getGuild().getId().equals("790885170411601920")){
            return;
        }

        //If the user hadn't been added to the Hashmap add him to it and set his last time a minute back so it can be updated
        if (!lastUserUpdate.containsKey(user)){
            lastUserUpdate.put(user, time - 60000);
        }


        if (lastUserUpdate.get(user) + 60000 <= System.currentTimeMillis()){
            onlineStatusChange(user, event.getNewOnlineStatus());
        }else{
            System.out.println("remaining wait time: " + (lastUserUpdate.get(user) - System.currentTimeMillis() + 60000));
        }

        lastUserUpdate.put(user, time);

    }

    public static void onlineStatusChange(User user, OnlineStatus status){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String dateTime = LocalDateTime.now().format(formatter);

        LiteSQL.onUpdate("INSERT INTO onlineStatuses (userid, time, status) VALUES ('" + user.getId() + "', '" + dateTime + "', '" + status + "')");
    }
}
