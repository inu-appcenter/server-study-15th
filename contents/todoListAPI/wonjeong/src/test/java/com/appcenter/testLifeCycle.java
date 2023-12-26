package com.appcenter;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

@Slf4j
public class testLifeCycle {
    @BeforeAll
    static void beforeAll() {
        log.info("## BeforeAll Annotation 호출");
    }

    @AfterAll
    static void afterAll() {
        log.info("## AfterAll Annotation 호출");
    }

    @BeforeEach
    void beforeEach() {
        log.info("## BeforeEach Annotation 호출");
    }

    @AfterAll
    static void afterEach() {
        log.info("## AfterEach Annotation 호출");
    }

    @Test
    void test1() {
        log.info("Test1 시작");
    }

    @Test
    @DisplayName("TestCase2")
    void test2() {
        log.info("## test2 시작");
    }

    @Test
    @Disabled
    void test3() {
        log.info("## test3 시작");
    }

}
