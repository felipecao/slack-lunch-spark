package com.github.felipecao.slack.lunch.command

import com.github.felipecao.slack.lunch.MongoStore
import com.github.felipecao.slack.lunch.SlackResponse
import com.github.felipecao.slack.lunch.model.Place
import org.mongodb.morphia.Datastore
import org.mongodb.morphia.query.Query
import spock.lang.Specification

class RandomCommandSpec extends Specification {

    private static final def PLACES_NAMES = ["place0", "place1"]

    private RandomCommand command

    private MongoStore store

    private Datastore datastore

    private Query<Place> query

    private Random random

    def setup() {
        datastore = Mock()
        store = Mock()
        query = Mock()
        random = Mock()

        command = new RandomCommand(mongoStore: store, random: random)
    }

    def "'handle' returns a place provided by MongoStore"() {
        given:
        def user = "felipe"

        and:
        def request = [user_name: user]

        when:
        SlackResponse response = command.handle(request)

        then:
        response.response_type == "in_channel"
        response.text == "@$user you should have lunch at: *place0*"

        and:
        1 * store.datastore >> datastore
        1 * datastore.createQuery(Place.class) >> query
        1 * query.asList() >> PLACES_NAMES.collect { new Place(name: it) }
        1 * random.nextInt(2) >> 0
    }
}
