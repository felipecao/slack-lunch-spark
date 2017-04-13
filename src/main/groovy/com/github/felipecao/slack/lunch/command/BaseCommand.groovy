package com.github.felipecao.slack.lunch.command

import com.github.felipecao.slack.lunch.MongoStore
import groovy.json.JsonSlurper
import spark.Request
import spark.Response
import spark.Route

abstract class BaseCommand implements Route {

    private JsonSlurper jsonSlurper = new JsonSlurper()

    protected MongoStore mongoStore

    protected abstract Object handle(def request)

    @Override
    def handle(Request request, Response response) throws Exception {
        response.type("application/json")
        return handle(jsonSlurper.parseText(request.body()))
    }
}
