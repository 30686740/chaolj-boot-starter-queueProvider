package com.chaolj.core.queueProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.chaolj.core.commonUtils.myServer.Interface.IQueueServer;

@Configuration
@EnableConfigurationProperties(MyQueueProviderProperties.class)
public class MyQueueProviderConfig {
    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    MyQueueProviderProperties myQueueProviderProperties;

    @Bean(name = "myQueueProvider")
    public IQueueServer MyQueueProvider(){
        return new MyQueueProvider(this.applicationContext, this.myQueueProviderProperties);
    }
}
