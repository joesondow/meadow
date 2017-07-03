package sondow.meadow;

import java.util.Random;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

/**
 * The function that AWS Lambda will invoke.
 *
 * @author @JoeSondow
 */
public class LambdaRequestHandler implements RequestHandler<Object, Object> {

    /*
     * (non-Javadoc)
     *
     * @see com.amazonaws.services.lambda.runtime.RequestHandler#handleRequest(java.
     * lang.Object, com.amazonaws.services.lambda.runtime.Context)
     */
    @Override
    public Object handleRequest(Object input, Context context) {
        Configuration config = configure();
        Tweeter tweeter = new Tweeter(config);
        MeadowBuilder builder = new MeadowBuilder(new Random());
        return tweeter.tweet(builder.build());
    }

    /**
     * AWS Lambda only allows underscores in environment variables, not dots, so the default
     * ways twitter4j finds keys aren't possible. Instead, this custom code gets the
     * configuration either from Lambda-friendly environment variables or else allows
     * Twitter4J to look in its default locations like twitter4j.properties file at the
     * project root, or on the classpath, or in WEB-INF.
     *
     * Twitter API keys are sensitive, so they should be encrypted with the AWS KMS console
     * helper tools and decrypted here using KMS. If you choose to use environment variables
     * without encryption, add the env var "encryption=off"
     *
     * @return configuration containing Twitter authentication strings
     */
    private Configuration configure() {

        ConfigurationBuilder cb = new ConfigurationBuilder();

        // If just one set of keys is provided in environment variables, use that key set.
        String consumerKey = Environment.get("twitter4j_oauth_consumerKey");
        String consumerSecret = Environment.get("twitter4j_oauth_consumerSecret");
        String accessToken = Environment.get("twitter4j_oauth_accessToken");
        String accessTokenSecret = Environment.get("twitter4j_oauth_accessTokenSecret");

        // Override with a specific account if available. This mechanism allows us to provide
        // multiple key sets in the AWS Lambda configuration, and switch which Twitter account
        // to target by retyping just the target Twitter account name in the configuration.
        String account = Environment.get("twitter_account");
        if (account != null) {
            String specificConsumerKey = Environment.get(account + "_twitter4j_oauth_consumerKey");
            consumerKey = (specificConsumerKey != null) ? specificConsumerKey : consumerKey;
            String specificConSec = Environment.get(account + "_twitter4j_oauth_consumerSecret");
            consumerSecret = (specificConSec != null) ? specificConSec : consumerSecret;
            String specificAccessToken = Environment.get(account + "_twitter4j_oauth_accessToken");
            accessToken = (specificAccessToken != null) ? specificAccessToken : accessToken;
            String specificAts = Environment.get(account + "_twitter4j_oauth_accessTokenSecret");
            accessTokenSecret = (specificAts != null) ? specificAts : accessTokenSecret;
        }
        if (consumerKey != null) {
            cb.setOAuthConsumerKey(consumerKey);
        }
        if (consumerSecret != null) {
            cb.setOAuthConsumerSecret(consumerSecret);
        }
        if (accessToken != null) {
            cb.setOAuthAccessToken(accessToken);
        }
        if (accessTokenSecret != null) {
            cb.setOAuthAccessTokenSecret(accessTokenSecret);
        }
        Configuration config = cb.setTrimUserEnabled(true).build();

        return config;
    }

    /**
     * Local manual testing. This requires setting up all the necessary environment variables
     * in your local dev environment for this execution. Note that Eclipse runtime
     * configuration is separate from command line env var setup.
     */
    public static void main(String[] args) {
        LambdaRequestHandler handler = new LambdaRequestHandler();
        handler.handleRequest(null, null);
    }
}
