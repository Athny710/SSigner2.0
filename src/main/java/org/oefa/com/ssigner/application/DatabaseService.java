package org.oefa.com.ssigner.application;

import org.oefa.com.ssigner.infra.output.adapter.H2DatabaseAdapter;

public class DatabaseService {

    private static final H2DatabaseAdapter h2DatabaseAdapter = new H2DatabaseAdapter();

    public static void setupDatabase() throws Exception {
        h2DatabaseAdapter.setupDatabase();

    }
}
