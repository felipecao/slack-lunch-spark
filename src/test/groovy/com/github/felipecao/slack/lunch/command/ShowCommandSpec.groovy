package com.github.felipecao.slack.lunch.command

import com.github.felipecao.slack.lunch.MongoStore
import com.github.felipecao.slack.lunch.SlackResponse
import com.github.felipecao.slack.lunch.model.Place
import spock.lang.Specification

class ShowCommandSpec extends Specification {

    private static final def PLACES_NAMES = ["place0", "place1"]

    private ShowCommand command

    private MongoStore store

    def setup() {
        store = Mock()
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
        1 * store.findAllPlaces() >> PLACES_NAMES.collect { new Place(name: it) }
    }

    def "'handle' suggests using `/add`"() {
        given:
        def user = "felipe"

        and:
        def request = [user_name: user]

        when:
        SlackResponse response = command.handle(request)

        then:
        response.response_type == "in_channel"
        response.text == "@$user there are no places yet! Why don't you try to create the first one by using the `/add` command?"

        and:
        1 * store.findAllPlaces() >> []
    }
}
