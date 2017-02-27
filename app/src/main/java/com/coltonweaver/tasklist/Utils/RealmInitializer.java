package com.coltonweaver.tasklist.Utils;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Colton Weaver on 2/25/2017.
 * The purpose of this class is to handle initialization of the realm configuration
 * Currently handling migration needs by just clearing the current database on model changes
 */

public class RealmInitializer {

    public static void init() {
        RealmConfiguration config = new RealmConfiguration.Builder()
                .schemaVersion(2)
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(config);
    }

}
