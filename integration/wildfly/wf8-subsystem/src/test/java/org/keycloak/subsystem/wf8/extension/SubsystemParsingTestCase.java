/*
 * Copyright 2013 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @author tags. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.keycloak.subsystem.wf8.extension;

import org.jboss.as.subsystem.test.AbstractSubsystemBaseTest;
import org.jboss.dmr.ModelNode;
import org.junit.Test;

import java.io.IOException;


/**
 * Tests all management expects for subsystem, parsing, marshaling, model definition and other
 * Here is an example that allows you a fine grained controller over what is tested and how. So it can give you ideas what can be done and tested.
 * If you have no need for advanced testing of subsystem you look at {@link SubsystemBaseParsingTestCase} that testes same stuff but most of the code
 * is hidden inside of test harness
 *
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @author Tomaz Cerar
 * @author <a href="marko.strukelj@gmail.com">Marko Strukelj</a>
 */
public class SubsystemParsingTestCase extends AbstractSubsystemBaseTest {

    public SubsystemParsingTestCase() {
        super(KeycloakExtension.SUBSYSTEM_NAME, new KeycloakExtension());
    }

    @Test
    public void testJson() throws Exception {
        ModelNode node = new ModelNode();
        node.get("realm").set("demo");
        node.get("resource").set("customer-portal");
        node.get("realm-public-key").set("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCrVrCuTtArbgaZzL1hvh0xtL5mc7o0NqPVnYXkLvgcwiC3BjLGw1tGEGoJaXDuSaRllobm53JBhjx33UNv+5z/UMG4kytBWxheNVKnL6GgqlNabMaFfPLPCF8kAgKnsi79NMo+n6KnSY8YeUmec/p2vjO2NjsSAVcWEQMVhJ31LwIDAQAB");
        node.get("auth-url").set("http://localhost:8080/auth-server/rest/realms/demo/protocol/openid-connect/login");
        node.get("code-url").set("http://localhost:8080/auth-server/rest/realms/demo/protocol/openid-connect/access/codes");
        node.get("ssl-required").set("external");
        node.get("expose-token").set(true);
        ModelNode credential = new ModelNode();
        credential.get("password").set("password");
        node.get("credentials").set(credential);

        System.out.println("json=" + node.toJSONString(false));
    }

    @Override
    protected String getSubsystemXml() throws IOException {
        return readResource("keycloak-1.1.xml");
    }
}
