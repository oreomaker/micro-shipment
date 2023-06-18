package com.wgzmb._02_gateway.util;

import cn.hutool.core.util.StrUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author RuKunHe(jom4ker @ aliyun.com)
 * @version com.whms.framework.security.utils 0.0.1
 */
@Component
public class PermitResource {
    @SneakyThrows
    public List<String> getPermitList() {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("classpath*:auth.yml");
        String key = "auth.ignore_urls";

        return getPropertiesList(key, resources);
    }

    private List<String> getPropertiesList(String key, Resource... resources) {
        List<String> list = new ArrayList<>();

        // 解析资源文件
        for (Resource resource : resources) {
            Properties properties = loadYamlProperties(resource);

            for (Map.Entry<Object, Object> entry : properties.entrySet()) {
                String tmpKey = StrUtil.subBefore(entry.getKey().toString(), "[", true);
                if (tmpKey.equalsIgnoreCase(key)) {
                    list.add(entry.getValue().toString());
                }
            }
        }

        return list;
    }

    private Properties loadYamlProperties(Resource... resources) {
        YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
        factory.setResources(resources);
        factory.afterPropertiesSet();

        return factory.getObject();
    }

    public boolean isPermit(List<String> whitelist, String uri) {
        for (String pattern : whitelist) {
            if (uri.matches(pattern)) {
                return true;
            }
        }
        return false;
    }
}
