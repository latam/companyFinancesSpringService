package pl.mlata.configuration.security.authentication.extractor;

/**
 * Implementation should return base-64 encoded representation of JWT Token.
 */
public interface TokenExtractor {
    String extract(String payload);
}
