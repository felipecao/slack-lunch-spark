package com.github.felipecao.slack.lunch.command

import com.github.felipecao.slack.lunch.SlackResponse

class MenuCommand extends BaseCommand {

    @Override
    def handle(def request) throws Exception {
        return new SlackResponse(
                "@${request.user_name} these are the commands we have available:\n" +
                        "`/add`: add a new place to have lunch\n" +
                        "`/show`: shows all places saved to our database\n" +
                        "`/random`: picks a random place for you to have lunch\n" +
                        "`/menu`: displays this message"
        )
    }
}
