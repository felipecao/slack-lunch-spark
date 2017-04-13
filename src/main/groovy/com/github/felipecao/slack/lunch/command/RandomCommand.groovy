package com.github.felipecao.slack.lunch.command

import com.github.felipecao.slack.lunch.SlackResponse
import com.github.felipecao.slack.lunch.model.Place
import org.mongodb.morphia.query.Query

class RandomCommand extends BaseCommand {

    private Random random = new Random()

    @Override
    protected def handle(def request) {

        final List<Place> allPlaces = mongoStore.findAllPlaces()

        if (!allPlaces){
            return new SlackResponse("@${request.user_name} there are no places yet! Why don't you try to create the first one by using the `/add` command?")
        }

        final Place place = allPlaces[random.nextInt(allPlaces.size())]

        return new SlackResponse("@${request.user_name} you should have lunch at: *${place.name}*")
    }
}
