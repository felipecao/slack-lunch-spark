package com.github.felipecao.slack.lunch.command

import com.github.felipecao.slack.lunch.MongoStore
import com.github.felipecao.slack.lunch.SlackResponse
import com.github.felipecao.slack.lunch.model.Place
import spock.lang.Specification

class RandomCommandSpec extends Specification {

    private static final def PLACES_NAMES = ["place0", "place1"]

    private RandomCommand command

    private MongoStore store

    private Random random

    def setup() {
        store = Mock()
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
        1 * store.findAllPlaces() >> PLACES_NAMES.collect { new Place(name: it) }
        1 * random.nextInt(2) >> 0
    }
}
