package com.github.felipecao.slack.lunch.command

import com.github.felipecao.slack.lunch.SlackResponse
import com.github.felipecao.slack.lunch.model.Place
import org.mongodb.morphia.query.Query

class ShowCommand extends BaseCommand {

    @Override
    protected def handle(def request) {

        final List<Place> places = mongoStore.findAllPlaces()

        if (!places){
            return new SlackResponse("@${request.user_name} there are no places yet! Why don't you try to create the first one by using the `/add` command?")
        }

        final String names = places.name.collect{ n -> "*${n}*"}.join("\n")

        return new SlackResponse("@${request.user_name} these are the places in our database: \n${names}")
    }
}
