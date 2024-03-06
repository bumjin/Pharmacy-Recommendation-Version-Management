package com.example.demo

import org.springframework.boot.test.context.SpringBootTest
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.MariaDBContainer
import org.testcontainers.images.PullPolicy
import spock.lang.Specification

@SpringBootTest
abstract class AbstractractIntegrationContainerBaseTest extends Specification {
    static final MariaDBContainer MY_DATABASE_CONTAINER
    static final GenericContainer MY_REDIS_CONTAINER

    static {
        MY_DATABASE_CONTAINER = new MariaDBContainer("mariadb:10")
                .withDatabaseName("pharmacyRecommendation")
                .withImagePullPolicy(PullPolicy.alwaysPull())// Database 지정하기
        MY_DATABASE_CONTAINER.start()

        MY_REDIS_CONTAINER = new GenericContainer<>("redis:6")
                .withExposedPorts(6379)
        MY_REDIS_CONTAINER.start()
        System.setProperty("spring.data.redis.host", MY_REDIS_CONTAINER.getHost())
        System.setProperty("spring.data.redis.port", MY_REDIS_CONTAINER.getMappedPort(6379).toString())

    }
}
