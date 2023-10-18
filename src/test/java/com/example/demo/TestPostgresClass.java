package com.example.demo;

import com.example.demo.initializer.Postgres;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class TestPostgresClass implements BeforeAllCallback, AfterAllCallback {
    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        Postgres.container.close();
    }

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        Postgres.container.start();
    }
}
