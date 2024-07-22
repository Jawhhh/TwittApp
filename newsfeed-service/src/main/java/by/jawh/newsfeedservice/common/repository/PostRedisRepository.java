package by.jawh.newsfeedservice.common.repository;

import by.jawh.newsfeedservice.common.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

@RequiredArgsConstructor
public class PostRedisRepository implements CrudRepository<Post, Long> {


    private static final String KEY = "post";
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public <S extends Post> S save(S entity) {

        // * при обновлении поста объект со старыми данными удаляется и вставляется объект с новыми данными
        if (redisTemplate.opsForHash().get(KEY, entity.getId()) != null) {
            redisTemplate.opsForHash().delete(KEY, entity.getId());
        }
        redisTemplate.opsForHash().put(KEY, entity.getId(), entity);
        return entity;
    }

    @Override
    public <S extends Post> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Post> findById(Long aLong) {
        return (Optional<Post>) redisTemplate.opsForHash().get(KEY, aLong);
    }

    @Override
    public boolean existsById(Long aLong) {
        return redisTemplate.opsForHash().get(KEY, aLong) != null;
    }

    @Override
    public Iterable<Post> findAll() {
        return redisTemplate.opsForHash().values(KEY).stream()
                .map(obj -> (Post) obj)
                .toList();
    }

    @Override
    public Iterable<Post> findAllById(Iterable<Long> longs) {
        return redisTemplate.opsForHash().values(KEY).stream()
                .map(obj -> (Post) obj)
                .filter(post -> longs.equals(post.getId()))
                .toList();
    }

    @Override
    public long count() {
        return redisTemplate.opsForHash().values(KEY).size();
    }

    @Override
    public void deleteById(Long aLong) {
        redisTemplate.opsForHash().delete(KEY, aLong);
    }

    @Override
    public void delete(Post entity) {
        redisTemplate.opsForHash().delete(KEY, entity.getId());
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {
        redisTemplate.opsForHash().values(KEY).stream()
                .map(obj -> (Post) obj)
                .filter(post -> longs.equals(post.getId()))
                .map(post -> redisTemplate.opsForHash().delete(KEY, post.getId()));
    }

    @Override
    public void deleteAll(Iterable<? extends Post> entities) {
        redisTemplate.opsForHash().values(KEY).stream()
                .map(obj -> (Post) obj)
                .map(post -> redisTemplate.opsForHash().delete(KEY, post.getId()));
    }

    @Override
    public void deleteAll() {

    }
}
