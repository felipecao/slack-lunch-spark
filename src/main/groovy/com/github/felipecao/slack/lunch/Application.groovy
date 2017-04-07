package com.github.felipecao.slack.lunch

import com.github.felipecao.slack.lunch.command.MenuCommand
import com.github.felipecao.slack.lunch.command.ShowCommand
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static spark.Spark.*

class Application {

    private Logger log = LoggerFactory.getLogger(Application.class);

    Application(MongoStore mongoStore) {
        before("/*", { q, a -> log.info("Received api call") } );
        path("/places", {
            post("/menu", new MenuCommand(mongoStore: mongoStore), new JsonTransformer());
            post("/show", new ShowCommand(mongoStore: mongoStore), new JsonTransformer());
        });
    }

    static void main(String[] args) {
        new Application(new MongoStore());
    }
}
