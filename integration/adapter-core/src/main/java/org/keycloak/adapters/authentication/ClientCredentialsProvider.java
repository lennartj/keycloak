package org.keycloak.adapters.authentication;

import java.util.Map;

import org.keycloak.adapters.KeycloakDeployment;

/**
 * The simple SPI for authenticating clients/applications . It's used by adapter during all OIDC backchannel requests to Keycloak server
 * (codeToToken exchange, refresh token or backchannel logout) . You can also use it in your application during direct access grants or service account request
 * (See the service-account example from Keycloak demo for more info)
 *
 * When you implement this SPI on the adapter (application) side, you also need to implement {@link org.keycloak.authentication.ClientAuthenticator} on the server side,
 * so your server is able to authenticate client
 *
 * You must specify a file
 * META-INF/services/org.keycloak.adapters.authentication.ClientCredentialsProvider in the WAR that this class is contained in (or in the JAR that is attached to the WEB-INF/lib or as jboss module
 * if you want to share the implementation among more WARs). This file must have the fully qualified class name of all your ClientAuthenticatorFactory classes
 *
 * NOTE: The SPI is not finished and method signatures are still subject to change in future versions (for example to support usecase for
 * authentication with client certificate)
 *
 * @see ClientIdAndSecretCredentialsProvider
 * @see JWTClientCredentialsProvider
 *
 * @author <a href="mailto:mposolda@redhat.com">Marek Posolda</a>
 */
public interface ClientCredentialsProvider {

    /**
     * Return the ID of the provider. Use this ID in the keycloak.json configuration as the subelement of the "credentials" element
     *
     * For example if your provider has ID "kerberos-keytab" , use the configuration like this in keycloak.json
     *
     * "credentials": {
     *
     *     "kerberos-keytab": {
     *         "keytab": "/tmp/foo"
     *     }
     * }
     *
     * @return
     */
    String getId();

    /**
     * Called by adapter during deployment of your application. You can for example read configuration and init your authenticator here
     *
     * @param deployment the adapter configuration
     * @param config the configuration of your provider read from keycloak.json . For the kerberos-keytab example above, it will return map with the single key "keytab" with value "/tmp/foo"
     */
    void init(KeycloakDeployment deployment, Object config);

    /**
     * Called every time adapter needs to perform backchannel request
     *
     * @param deployment Fully resolved deployment
     * @param requestHeaders You should put any HTTP request headers you want to use for authentication of client. These headers will be attached to the HTTP request sent to Keycloak server
     * @param formParams You should put any request parameters you want to use for authentication of client. These parameters will be attached to the HTTP request sent to Keycloak server
     */
    void setClientCredentials(KeycloakDeployment deployment, Map<String, String> requestHeaders, Map<String, String> formParams);
}
