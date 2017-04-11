package com.github.felipecao.slack.lunch

import com.github.felipecao.slack.lunch.command.MenuCommand
import com.github.felipecao.slack.lunch.command.ShowCommand
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static spark.Spark.*

class Application {

    private Logger log = LoggerFactory.getLogger(Application.class);

    Application(MongoStore mongoStore) {
        port(getHerokuAssignedPort());
        before("/*", { q, a -> log.info("Received api call") } );
        get("/", {req, res -> "Slack-Lunch is working with Spark!"});
        path("/places", {
            post("/menu", new MenuCommand(mongoStore: mongoStore), new JsonTransformer());
            post("/show", new ShowCommand(mongoStore: mongoStore), new JsonTransformer());
        });
    }

    private static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();

        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }

        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }

    static void main(String[] args) {
        new Application(new MongoStore());
    }
}
