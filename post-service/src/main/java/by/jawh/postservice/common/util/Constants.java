package by.jawh.postservice.common.util;

import org.springframework.stereotype.Component;

@Component
public class Constants {

    public static final long TTL_FOR_POST = 30 * 60;

    public static final String PREFIX_CACHE_KEY_FOR_POST = "posts_by_profile_";

    public static final String JWT_EXTRACT_ID = "http://auth:8080/auth/jwt/getId";

}
