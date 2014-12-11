package fr.epsi.controller;

import fr.epsi.service.connection.ConnectionService;

public class MainController
{
    private static ConnectionService connectionService;

    public MainController() {
        connectionService = new ConnectionService();
    }

    public static ConnectionService getConnectionService() {
        return connectionService;
    }
}
