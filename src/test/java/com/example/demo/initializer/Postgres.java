package com.example.demo.initializer;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import org.testcontainers.containers.PostgreSQLContainer;

public class Postgres {

    public static final PostgreSQLContainer<?> container;

    static {
        container = new PostgreSQLContainer<>("postgres")
                .withDatabaseName("demo")
                .withUsername("postgres")
                .withPassword("postgres")
                .withCreateContainerCmdModifier(cmd -> cmd.getHostConfig()
                        .withPortBindings(new PortBinding(Ports.Binding.bindPort(5433), ExposedPort.tcp(5432))));
    }
}
