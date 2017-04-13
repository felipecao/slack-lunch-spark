package com.github.felipecao.slack.lunch.command

import com.github.felipecao.slack.lunch.SlackResponse
import com.github.felipecao.slack.lunch.model.Place
import org.mongodb.morphia.query.Query

class RandomCommand extends BaseCommand {

    private Random random = new Random()

    @Override
    protected def handle(def request) {

        final Query<Place> query = mongoStore.datastore.createQuery(Place.class)
        final List<Place> allPlaces = query.asList()
        final Place place = allPlaces[random.nextInt(allPlaces.size())]

        return new SlackResponse("@${request.user_name} you should have lunch at: *${place.name}*")
    }
}
