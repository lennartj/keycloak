package org.keycloak.testsuite.forms;

import java.util.LinkedList;
import java.util.List;

import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.authentication.ClientAuthenticationFlowContext;
import org.keycloak.authentication.authenticators.client.AbstractClientAuthenticator;
import org.keycloak.events.Details;
import org.keycloak.models.AuthenticationExecutionModel;
import org.keycloak.models.ClientModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.provider.ProviderConfigProperty;

/**
 * @author <a href="mailto:mposolda@redhat.com">Marek Posolda</a>
 */
public class PassThroughClientAuthenticator extends AbstractClientAuthenticator {

    public static final String PROVIDER_ID = "client-passthrough";
    public static String clientId = "test-app";

    public static final AuthenticationExecutionModel.Requirement[] REQUIREMENT_CHOICES = {
            AuthenticationExecutionModel.Requirement.REQUIRED
    };

    @Override
    public void authenticateClient(ClientAuthenticationFlowContext context) {
        ClientModel client = context.getRealm().getClientByClientId(clientId);
        if (client == null) {
            context.failure(AuthenticationFlowError.CLIENT_NOT_FOUND, null);
            return;
        }

        context.getEvent().client(client);
        context.setClient(client);
        context.success();
    }

    @Override
    public boolean requiresClient() {
        return false;
    }

    @Override
    public boolean configuredFor(KeycloakSession session, RealmModel realm, ClientModel client) {
        return true;
    }

    @Override
    public String getDisplayType() {
        return "PassThrough Client Validation";
    }

    @Override
    public boolean isConfigurable() {
        return false;
    }

    @Override
    public boolean isConfigurablePerClient() {
        return false;
    }

    @Override
    public AuthenticationExecutionModel.Requirement[] getRequirementChoices() {
        return REQUIREMENT_CHOICES;
    }

    @Override
    public String getHelpText() {
        return "Automatically authenticates client 'test-app' ";
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return new LinkedList<>();
    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }
}
