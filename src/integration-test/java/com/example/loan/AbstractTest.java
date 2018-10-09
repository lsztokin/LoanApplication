package com.example.loan;

import javax.transaction.Transactional;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql({"/insert-data.sql"})
@Sql(scripts = "/clean-up.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
@ActiveProfiles("test")
public abstract class AbstractTest 
{
}
