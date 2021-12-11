package com.example.db;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.command.Command;
import org.kie.api.command.KieCommands;
import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.kie.server.client.RuleServicesClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RuleTest {

    static final Logger log = LoggerFactory.getLogger(RuleTest.class);

    private static final String droolsUrl = "http://localhost:8080/kie-server/services/rest/server";
    private static final String username = "rhdmAdmin";
    private static final String password = "admin@123";

    private static final MarshallingFormat FORMAT = MarshallingFormat.JSON;

    private static KieServicesConfiguration kieServicesConfig;
    private static KieServicesClient kieServicesClient;

    @Before
    public void setup() {
        kieServicesConfig = KieServicesFactory.newRestConfiguration(droolsUrl, username, password);
        kieServicesConfig.setMarshallingFormat(FORMAT);

        Set<Class<?>> allClasses = new HashSet<Class<?>>();
        kieServicesConfig.addExtraClasses(allClasses);

        kieServicesClient = KieServicesFactory.newKieServicesClient(kieServicesConfig);
    }

    @After
    public void teardown() {

    }

    @Test
    public void test() {

        RuleServicesClient rulesClient = kieServicesClient.getServicesClient(RuleServicesClient.class);

        KieCommands commandFactory = KieServices.Factory.get().getCommands();

        List<Command<?>> commands = new ArrayList<>();

        Command<?> fireAllRules = commandFactory.newFireAllRules();
        Command<?> dispose = commandFactory.newDispose();
        commands.addAll(Arrays.asList(fireAllRules, dispose));

        Command<?> batchCommand = commandFactory.newBatchExecution(commands);
        rulesClient.executeCommandsWithResults("drools-access-database",
                batchCommand);

    }
}