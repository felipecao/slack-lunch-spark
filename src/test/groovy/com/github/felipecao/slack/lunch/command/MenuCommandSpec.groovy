package com.github.felipecao.slack.lunch.command

import com.github.felipecao.slack.lunch.SlackResponse
import spock.lang.Specification

class MenuCommandSpec extends Specification {

    private MenuCommand command

    def setup() {
        command = new MenuCommand()
    }

    def "'handle' returns the available menu options"() {
        given:
        def user = "felipe"

        and:
        def request = [user_name: user]

        when:
        SlackResponse response = command.handle(request)

        then:
        response.response_type == "in_channel"
        response.text == "@$user these are the commands we have available:\n" +
                "`/add`: add a new place to have lunch\n" +
                "`/show`: shows all places saved to our database\n" +
                "`/random`: picks a random place for you to have lunch\n" +
                "`/menu`: displays this message"
    }

}
