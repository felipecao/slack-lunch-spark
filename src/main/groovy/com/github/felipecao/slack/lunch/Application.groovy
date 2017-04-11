package com.github.felipecao.slack.lunch

import com.github.felipecao.slack.lunch.command.MenuCommand
import com.github.felipecao.slack.lunch.command.ShowCommand
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static spark.Spark.*

class Application {

    private static Logger log = LoggerFactory.getLogger(Application.class);

    Application(MongoStore mongoStore) {
        port(getHerokuAssignedPort());
        before("/*", { q, a ->
            log.info("Received api call")
        } );
        get("/", {req, res ->
            "Slack-Lunch is working with Spark!"
        });
        path("/places", {
            post("/menu", new MenuCommand(mongoStore: mongoStore), new JsonTransformer());
            post("/show", new ShowCommand(mongoStore: mongoStore), new JsonTransformer());
        });
    }

    private static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();

        log.info("Configuring port...");

        if (processBuilder.environment().get("PORT") != null) {
            int port = Integer.parseInt(processBuilder.environment().get("PORT"));

            log.info("Listening on port " + port);
            return port;
        }

        log.info("Listening on default port 4567");
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }

    static void main(String[] args) {
        new Application(new MongoStore());
    }
}
