/*
 * Copyright 2016 Red Hat Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.vertx.mqtt.it;

import io.vertx.core.Vertx;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.mqtt.MqttClient;
import io.vertx.mqtt.MqttClientOptions;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.*;

/**
 * MQTT client testing on client identifier
 */
public class MqttClientIdIT extends MqttClientBaseIT {

  private static final Logger log = LoggerFactory.getLogger(MqttClientIdIT.class);

  @Test
  public void afterConnectClientIdGenerated(TestContext context) throws InterruptedException {

    MqttClientOptions options = new MqttClientOptions();
    MqttClient client = MqttClient.create(Vertx.vertx(), options);

    assertThat(options.getClientId(), nullValue());

    client.connect(port, host).onComplete(context.asyncAssertSuccess(v -> {

      assertTrue(client.clientId().length() == 36);
      assertThat(client.clientId(), notNullValue());
      assertFalse(client.clientId().isEmpty());

      log.info("Client connected with generated client id = " + client.clientId());
    }));
  }

  @Test
  public void afterConnectClientId(TestContext context) {

    MqttClientOptions options = new MqttClientOptions();
    options.setClientId("myClient");
    MqttClient client = MqttClient.create(Vertx.vertx(), options);

    client.connect(port, host).onComplete(context.asyncAssertSuccess(v -> {

      assertThat(client.clientId(), notNullValue());
      assertFalse(client.clientId().isEmpty());
      assertEquals(client.clientId(), "myClient");

      log.info("Client connected with requested client id = " + client.clientId());
    }));
  }
}
