package fr.epsi.service.command;

import fr.epsi.service.command.commands.FTPCommand;

public class FTPCommandFactory {

    public static FTPCommand createCommand(String commandName) {
        return new FTPCommand() {
        };
    }
}
