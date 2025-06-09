package cn.iocoder.yudao.module.monitor.framework.web.config;

import cn.iocoder.yudao.framework.swagger.config.YudaoSwaggerAutoConfiguration;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * monitor 模块的 web 组件的 Configuration
 *
 * @author 芋道源码
 */
@Configuration(proxyBeanMethods = false)
public class MonitorWebConfiguration {

    /**
     * monitor 模块的 API 分组
     */
    @Bean
    public GroupedOpenApi monitorGroupedOpenApi() {
        return YudaoSwaggerAutoConfiguration.buildGroupedOpenApi("monitor");
    }

}
