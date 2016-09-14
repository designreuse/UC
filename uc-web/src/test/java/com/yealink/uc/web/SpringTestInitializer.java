package com.yealink.uc.web;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppContextInitializer.class, BasicDataInitializer.class})
@TransactionConfiguration
@ActiveProfiles("test")
@WebAppConfiguration
public abstract class SpringTestInitializer {

}
