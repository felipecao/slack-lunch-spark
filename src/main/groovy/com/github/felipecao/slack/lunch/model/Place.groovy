package com.github.felipecao.slack.lunch.model

import org.bson.types.ObjectId
import org.mongodb.morphia.annotations.Entity
import org.mongodb.morphia.annotations.Id

@Entity("places")
class Place {

    @Id
    ObjectId id;

    String name;

    String url;
}
