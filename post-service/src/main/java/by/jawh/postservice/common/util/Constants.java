package by.jawh.postservice.common.util;

import org.springframework.stereotype.Component;

@Component
public class Constants {

    public static final long TTL_FOR_POST = 30 * 60;

    public static final String PREFIX_CACHE_KEY_FOR_POST = "posts_by_profile_";

}
