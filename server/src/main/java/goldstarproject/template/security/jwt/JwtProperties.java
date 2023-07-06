package goldstarproject.template.security.jwt;

public interface JwtProperties {
    String SECRET = "ADMIN";
    int EXPIRATION_TIME = 3 * 60 * 60 * 1000; //3시간
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
    String HEADER_STRING_USERNAME = "username";
}
