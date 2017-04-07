package com.github.felipecao.slack.lunch

import com.google.gson.Gson
import spark.ResponseTransformer;

class JsonTransformer implements ResponseTransformer {

    private Gson gson = new Gson();

    @Override
    String render(Object model) {
        return gson.toJson(model);
    }

}