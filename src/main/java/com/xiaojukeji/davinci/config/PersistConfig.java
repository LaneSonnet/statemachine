package com.xiaojukeji.davinci.config;

import com.xiaojukeji.davinci.Package.PackageEvents;
import com.xiaojukeji.davinci.Package.PackageStates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.persist.RepositoryStateMachinePersist;
import org.springframework.statemachine.redis.RedisStateMachineContextRepository;
import org.springframework.statemachine.redis.RedisStateMachinePersister;

/**
 * @description 状态机持久化配置
 * @auther duenpu
 * @date 2021/9/26 16:34
 */
@Configuration
public class PersistConfig {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    /**
     * 注入RedisStateMachinePersister对象
     *
     * @return
     */
    @Bean(name = "packageRedisPersister")
    public RedisStateMachinePersister<PackageStates, PackageEvents> redisPersister() {
        return new RedisStateMachinePersister<>(redisPersist());
    }

    /**
     * 通过redisConnectionFactory创建StateMachinePersist
     *
     * @return
     */
    public StateMachinePersist<PackageStates, PackageEvents, String> redisPersist() {
        RedisStateMachineContextRepository<PackageStates, PackageEvents> repository =
                new RedisStateMachineContextRepository<>(redisConnectionFactory);
        return new RepositoryStateMachinePersist<>(repository);
    }

}
