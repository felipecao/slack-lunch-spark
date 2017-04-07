package com.github.felipecao.slack.lunch

import com.mongodb.MongoClient
import com.mongodb.MongoClientURI
import org.mongodb.morphia.Datastore
import org.mongodb.morphia.Morphia

class MongoStore {

    private static final String MONGO_COLLECTION = "lunch"
    private static final String MONGO_URI = "mongodb://..." + MONGO_COLLECTION

    private final Morphia morphia
    final Datastore datastore

    MongoStore() {
        morphia = new Morphia();
        morphia.mapPackage("com.github.felipecao.slack.lunch.model")

        datastore = morphia.createDatastore(new MongoClient(new MongoClientURI(MONGO_URI)), MONGO_COLLECTION);
    }
}
