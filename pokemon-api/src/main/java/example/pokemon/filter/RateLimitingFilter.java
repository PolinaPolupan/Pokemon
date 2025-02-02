package example.pokemon.filter;

import example.pokemon.exception.RateLimitExceededException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


@Component
public class RateLimitingFilter extends OncePerRequestFilter {

    private static final int MAX_REQUESTS_PER_MINUTE = 5;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    @Autowired
    private JedisPool jedisPool;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {

        String clientIp = request.getRemoteAddr(); // Identify client by IP
        String key = "rate_limit:" + clientIp;

        try (Jedis jedis = jedisPool.getResource()) {

            String requestCount = jedis.get(key);

            if (requestCount == null) {
                // First request, set count to 1 and set expiration for 1 minute
                jedis.setex(key, 60, "1");
            } else {
                int currentCount = Integer.parseInt(requestCount);

                if (currentCount < MAX_REQUESTS_PER_MINUTE) {
                    // Increment the request count
                    jedis.incr(key);
                } else {
                    response.setStatus(429);
                    throw new RateLimitExceededException("Too many requests");
                }
            }
            filterChain.doFilter(request, response); // Continue the request
        } catch (Exception e) {
            resolver.resolveException(request, response, null, e);
        }
    }
}