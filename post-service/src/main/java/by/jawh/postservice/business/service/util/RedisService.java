package by.jawh.postservice.business.service.util;

import by.jawh.postservice.common.entity.PostEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final JedisPool jedisPool;
    private final ObjectMapper objectMapper;

    public void saveAll(String cacheKey, List<PostEntity> entities, long ttl) {
        try (Jedis jedis = jedisPool.getResource()) {

            entities.forEach(entity -> {
                try {

                    jedis.hset(cacheKey, entity.getId().toString(), objectMapper.writeValueAsString(entity));
                    jedis.expire(cacheKey, ttl);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (Exception e) {
            throw new RuntimeException("Error while accessing Redis", e);
        }
    }

    public List<PostEntity> findAllPostByProfileId(String cacheKey) {

        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.hgetAll(cacheKey)
                    .values()
                    .stream()
                    .map(json -> objectMapper.convertValue(json, PostEntity.class))
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException("Error while accessing Redis", e);
        }
    }

    public PostEntity findByPostId(String cacheKey, Long fieldId) {
        try (Jedis jedis = jedisPool.getResource()) {
            String json = jedis.hget(cacheKey, fieldId.toString());
            return  objectMapper.convertValue(json, PostEntity.class);
        }catch (Exception e) {
            throw new RuntimeException("Error while accessing Redis", e);
        }
    }

    public void save(String cacheKey, Long fieldId, PostEntity entity, long ttl) {
        try (Jedis jedis = jedisPool.getResource()) {
            String json = objectMapper.writeValueAsString(entity);
            jedis.hset(
                    cacheKey,
                    fieldId.toString(),
                    json
            );
            jedis.expire(cacheKey, ttl);
        } catch (Exception e) {
            throw new  RuntimeException("Error while accessing Redis", e);
        }
    }

    public void delete(String cacheKey, Long fieldId) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.hdel(cacheKey, fieldId.toString());
        } catch (Exception e) {
            throw new RuntimeException("Error while accessing Redis", e);
        }
    }
}
