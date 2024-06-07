package lib;

import lib.Models.User;

import java.io.Serializable;

public class Request implements Serializable {
    String type;
    Commands commands = null;
    User user = null;

    public void setCommands(Commands commands) {
        type = "command";
        this.commands = commands;
    }

    public String getType() {
        return type;
    }

    public Commands getCommands() {
        return commands;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        type = "user";
        this.user = user;
    }
}
