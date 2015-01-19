package fr.epsi.service.connection;

public enum ConnectionState {
    /**
     * Socket connectée
     */
    CONNECTED("connected"),

    /**
     * Utilisateur authentifié
     */
    AUTHENTICATED("authenticated"),

    /**
     * Socket déconnectée
     */
    DISCONNECTED("disconnected");


    private String state;

    ConnectionState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return state;
    }
}
