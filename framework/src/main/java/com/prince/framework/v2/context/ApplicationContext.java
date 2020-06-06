package com.prince.framework.v2.context;

import com.prince.framework.v2.annotation.Autowired;
import com.prince.framework.v2.annotation.Controller;
import com.prince.framework.v2.annotation.Service;
import com.prince.framework.v2.aop.DefaultAopProxyFactory;
import com.prince.framework.v2.aop.JdkDynamicAopProxy;
import com.prince.framework.v2.aop.config.AopConfig;
import com.prince.framework.v2.aop.support.AdviceSupport;
import com.prince.framework.v2.beans.BeanWrapper;
import com.prince.framework.v2.beans.config.BeanDefinition;
import com.prince.framework.v2.beans.support.BeanDefinitionReader;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 完成Bean的创建和DI
 * @author Prince
 * @date 2020/5/24 16:13
 */
public class ApplicationContext {
    private Map<String, BeanDefinition> beanDefinitionMap = new HashMap<String, BeanDefinition>();
    private BeanDefinitionReader beanDefinitionReader;
    private Map<String,BeanWrapper> factoryBeanInstanceCache = new HashMap<String, BeanWrapper>();
    private Map<String,Object> factoryBeanObjectCache = new HashMap<String, Object>();
    private DefaultAopProxyFactory proxyFactory = new DefaultAopProxyFactory();

    public ApplicationContext(String... configLocations) {
        //加载配置文件
        beanDefinitionReader = new BeanDefinitionReader(configLocations);

        try {
            //解析配置文件，包装成BeanDefinition
            List<BeanDefinition> beanDefinitions = beanDefinitionReader.loadBeanDefinitions();

            //把BeanDefinition缓存起来
            doRegisterBeanDefinition(beanDefinitions);

            //注入
            doAutowired();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void doAutowired() {
        //调用getBean()
        for (Map.Entry<String,BeanDefinition> beanDefinitionEntry : beanDefinitionMap.entrySet()) {
            String beanName = beanDefinitionEntry.getKey();
            getBean(beanName);
        }
    }

    private void doRegisterBeanDefinition(List<BeanDefinition> beanDefinitions) throws Exception {
        for (BeanDefinition beanDefinition : beanDefinitions) {
            if(this.beanDefinitionMap.containsKey(beanDefinition.getFactoryBeanName())){
                throw new Exception("The " + beanDefinition.getFactoryBeanName() + " is exists!");
            }
            beanDefinitionMap.put(beanDefinition.getFactoryBeanName(),beanDefinition);
            beanDefinitionMap.put(beanDefinition.getBeanClassName(),beanDefinition);
        }
    }

    public Object getBean(String beanName){
        //1.拿到BeanDefinition配置信息
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        //2.反射实例化newInstance()
        Object instance = instanceBean(beanName,beanDefinition);
        //3.封装成BeanWrapper对象
        BeanWrapper beanWrapper = new BeanWrapper(instance);
        //4.保存到IOC容器
        factoryBeanInstanceCache.put(beanName,beanWrapper);
        //5.执行依赖注入
        populateBean(beanName,beanDefinition,beanWrapper);
        return beanWrapper.getWrapperInstance();
    }

    public Object getBean(Class beanClass){
        return getBean(beanClass.getName());
    }

    private void populateBean(String beanName, BeanDefinition beanDefinition, BeanWrapper beanWrapper) {

        Object instance = beanWrapper.getWrapperInstance();
        Class<?> clazz = beanWrapper.getWrapperClass();
        if(!(clazz.isAnnotationPresent(Controller.class) || clazz.isAnnotationPresent(Service.class))){return;}
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Autowired.class)){
                Autowired autowired = field.getAnnotation(Autowired.class);
                String autowiredBeanName = autowired.value().trim();
                if ("".equals(autowiredBeanName)){
                    autowiredBeanName = field.getType().getName();
                }
                //如果字段是private
                field.setAccessible(true);
                try {
                    if(this.factoryBeanInstanceCache.get(autowiredBeanName) == null){continue;}
                    field.set(instance,this.factoryBeanInstanceCache.get(autowiredBeanName).getWrapperInstance());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    continue;
                }
            }
        }
    }

    //创建实列对象
    private Object instanceBean(String beanName, BeanDefinition beanDefinition) {
        String className = beanDefinition.getBeanClassName();
        Object instance = null;
        try {
            if(this.factoryBeanObjectCache.containsKey(beanName)){
                instance = this.factoryBeanObjectCache.get(beanName);
            }else {
                Class<?> clazz = Class.forName(className);

                instance = clazz.newInstance();

                //加载AOP配置文件
                AdviceSupport config = instanceAopConfig(beanDefinition);
                config.setTargetClass(clazz);
                config.setTarget(instance);

                //如果需要织入切面就返回Proxy对象
                if(config.pointCutMath()){
                    instance = proxyFactory.createAopProxy(config).getProxy();
                }

                this.factoryBeanObjectCache.put(beanName, instance);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return instance;
    }

    private AdviceSupport instanceAopConfig(BeanDefinition beanDefinition) {
        AopConfig aopConfig = new AopConfig();
        aopConfig.setPointCut(this.beanDefinitionReader.getConfig().getProperty("pointCut"));
        aopConfig.setAspectClass(this.beanDefinitionReader.getConfig().getProperty("aspectClass"));
        aopConfig.setAspectBefore(this.beanDefinitionReader.getConfig().getProperty("aspectBefore"));
        aopConfig.setAspectAfter(this.beanDefinitionReader.getConfig().getProperty("aspectAfter"));
        aopConfig.setAspectAfterThrow(this.beanDefinitionReader.getConfig().getProperty("aspectAfterTrow"));
        aopConfig.setAspectAfterThrowingName(this.beanDefinitionReader.getConfig().getProperty("aspectAfterThrowingName"));

        return new AdviceSupport(aopConfig);
    }

    public int getBeanDefinitionCount() {
        return this.beanDefinitionMap.size();
    }

    public String[] getBeanDefinitionNames() {
        return this.beanDefinitionMap.keySet().toArray(new String[this.beanDefinitionMap.size()]);
    }

    public Properties getConfig() {
        return this.beanDefinitionReader.getConfig();
    }
}
