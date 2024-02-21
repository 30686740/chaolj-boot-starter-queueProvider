package com.chaolj.core.queueProvider;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "myproviders.myqueueprovider")
public class MyQueueProviderProperties {
    private String defaultClientToken = "dev";
    private String serverHostUrl = "https://deverp.ztzs.cn/_global";
}
