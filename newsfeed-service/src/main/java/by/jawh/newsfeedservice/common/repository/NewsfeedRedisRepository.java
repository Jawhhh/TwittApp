package by.jawh.newsfeedservice.common.repository;

import by.jawh.newsfeedservice.common.entity.Post;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class NewsfeedRedisRepository implements CrudRepository<Post, Long> {

}
