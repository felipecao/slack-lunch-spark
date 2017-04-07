package com.github.felipecao.slack.lunch.command

import com.github.felipecao.slack.lunch.SlackResponse
import com.github.felipecao.slack.lunch.model.Place
import org.mongodb.morphia.query.Query

class ShowCommand extends BaseCommand {

    @Override
    protected Object handle(def request) {

        final Query<Place> query = mongoStore.datastore.createQuery(Place.class)
        final List<Place> places = query.asList()
        final String names = places.name.collect{ n -> "*${n}*"}.join("\n")

        return new SlackResponse("@${request.user_name} these are the places in our database: \n${names}")
    }
}
