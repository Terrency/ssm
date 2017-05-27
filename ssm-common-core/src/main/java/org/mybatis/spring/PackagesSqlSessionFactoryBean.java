package org.mybatis.spring;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PackagesSqlSessionFactoryBean extends SqlSessionFactoryBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(PackagesSqlSessionFactoryBean.class);

    private static final String DEFAULT_RESOURCE_PATTERN = "classpath*:%s/**/*.class";

    @Override
    public void setTypeAliasesPackage(String typeAliasesPackage) {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resolver);
        typeAliasesPackage = String.format(DEFAULT_RESOURCE_PATTERN, ClassUtils.convertClassNameToResourcePath(typeAliasesPackage));
        // 将加载多个绝对匹配的所有Resource, 将首先通过 ClassLoader.getResource("META-INF") 加载非模式路径部分, 然后进行遍历模式匹配.
        try {
            List<String> result = new ArrayList<>();
            Resource[] resources = resolver.getResources(typeAliasesPackage);
            if (resources != null && resources.length > 0) {
                MetadataReader metadataReader;
                for (Resource resource : resources) {
                    if (resource.isReadable()) {
                        metadataReader = metadataReaderFactory.getMetadataReader(resource);
                        try {
                            result.add(Class.forName(metadataReader.getClassMetadata().getClassName()).getPackage().getName());
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            if (result.size() > 0) {
                super.setTypeAliasesPackage(StringUtils.join(result.toArray(), ","));
            } else {
                LOGGER.warn("Parameter typeAliasesPackage:" + typeAliasesPackage + ", no packages found");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
