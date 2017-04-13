package com.github.felipecao.slack.lunch

import com.github.felipecao.slack.lunch.command.MenuCommand
import com.github.felipecao.slack.lunch.command.RandomCommand
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
            post("/random", new RandomCommand(mongoStore: mongoStore), new JsonTransformer());
        });
    }

    private static int getHerokuAssignedPort() {
        Integer defaultPort = 4567;
        String port = new ProcessBuilder().environment().getOrDefault("PORT", defaultPort.toString());

        log.info("Listening on port " + port);
        return Integer.valueOf(port);
    }

    static void main(String[] args) {
        new Application(new MongoStore());
    }
}
