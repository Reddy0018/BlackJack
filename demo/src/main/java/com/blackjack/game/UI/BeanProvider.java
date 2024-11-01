package com.blackjack.game.UI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class BeanProvider {
    private static ApplicationContext applicationContext;

    public static void autowire(Object object) {
        if (applicationContext == null) {
            throw new IllegalStateException("ApplicationContext not initialized");
        }
        applicationContext.getAutowireCapableBeanFactory().autowireBean(object);
    }

    @Autowired
    private void setApplicationContext(ApplicationContext applicationContext) {
        BeanProvider.applicationContext = applicationContext;
    }
}
