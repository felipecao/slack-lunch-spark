package com.github.felipecao.slack.lunch.command

import com.github.felipecao.slack.lunch.MongoStore
import com.github.felipecao.slack.lunch.SlackResponse
import com.github.felipecao.slack.lunch.model.Place
import org.mongodb.morphia.Datastore
import org.mongodb.morphia.query.Query
import spock.lang.Specification

class ShowCommandSpec extends Specification {

    private static final def PLACES_NAMES = ["place0", "place1"]

    private ShowCommand command

    private MongoStore store

    private Datastore datastore

    private Query<Place> query

    def setup() {
        datastore = Mock()
        store = Mock()
        query = Mock()

        command = new ShowCommand(mongoStore: store)
    }

    def "'handle' returns the places names provided by MongoStore"() {
        given:
        def user = "felipe"

        and:
        def request = [user_name: user]

        when:
        SlackResponse response = command.handle(request)

        then:
        response.response_type == "in_channel"
        response.text == "@$user these are the places in our database: \n*place0*\n*place1*"

        and:
        1 * store.datastore >> datastore
        1 * datastore.createQuery(Place.class) >> query
        1 * query.asList() >> PLACES_NAMES.collect { new Place(name: it) }
    }
}
