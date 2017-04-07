package com.github.felipecao.slack.lunch

class SlackResponse {

    String response_type = "in_channel"
    String text

    SlackResponse(def text) {
        this.text = text.toString()
    }
}
