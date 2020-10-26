package com.area.server.components.about;

import com.area.server.components.services.model.Services;

import java.util.List;

/**
 * The type About.
 */
public class About {

    /**
     * The type Client.
     */
    public class Client {
        /**
         * The Host.
         */
        public String host = "";
    }

    /**
     * The type Server.
     */
    public class Server {

        /**
         * The Current time.
         */
        public Long current_time;

        /**
         * The Services.
         */
        public List<Services> services;
    }

    /**
     * The Client.
     */
    public Client client = new Client();

    /**
     * The Server.
     */
    public Server server = new Server();
}
