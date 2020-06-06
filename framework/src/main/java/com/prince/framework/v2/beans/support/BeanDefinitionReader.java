package com.prince.framework.v2.beans.support;

import com.prince.framework.v2.annotation.Controller;
import com.prince.framework.v2.annotation.Service;
import com.prince.framework.v2.beans.config.BeanDefinition;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 解析配置文件
 *
 * @author Prince
 * @date 2020/5/24 16:29
 */
public class BeanDefinitionReader {

    private Properties contextConfig = new Properties();
    private List<String> registryBeanClasses = new ArrayList<String>();

    public BeanDefinitionReader(String... configLocations) {
        //加载配置文件
        doLoadConfig(configLocations[0]);
        //扫描
        doScanner(contextConfig.getProperty("scanPackage"));
    }

    public List<BeanDefinition> loadBeanDefinitions() {
        List<BeanDefinition> result = new ArrayList<BeanDefinition>();
        try {
            for (String registryBeanClass : registryBeanClasses) {
                Class<?> clazz = Class.forName(registryBeanClass);
                if(clazz.isInterface()){continue;}
                //保存类对应的全类名ClassName
                //保存beanName
//                if(!clazz.isAnnotationPresent(Controller.class) || !clazz.isAnnotationPresent(Service.class)){continue;}
//                if("" != clazz.getAnnotation(Controller.class).value().trim()){
//                    result.add(doCreatBeanDefinition(clazz.getAnnotation(Controller.class).value().trim(), clazz.getName()));
//                }else if("" != clazz.getAnnotation(Service.class).value().trim()){
//                    result.add(doCreatBeanDefinition(clazz.getAnnotation(Service.class).value().trim(), clazz.getName()));
//                }else{
                //默认类名首字母小写
                result.add(doCreatBeanDefinition(lowerCaseFirst(clazz.getSimpleName()), clazz.getName()));
//                }
                //自定义名

                //接口注入
                for (Class<?> i : clazz.getInterfaces()) {
                    result.add(doCreatBeanDefinition(i.getName(),clazz.getName()));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }

    private BeanDefinition doCreatBeanDefinition(String beanName, String beanClassName) {
        BeanDefinition beanDefinition = new BeanDefinition();
        beanDefinition.setBeanClassName(beanClassName);
        beanDefinition.setFactoryBeanName(beanName);
        return beanDefinition;
    }

    private void doLoadConfig(String contextConfigLocation) {
        //拿到spring配置文件的路径，读取配置文件内容
        InputStream ins = this.getClass().getClassLoader().getResourceAsStream(contextConfigLocation.replaceAll("classpath:",""));
        try {
            contextConfig.load(ins);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (ins != null){
                try {
                    ins.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void doScanner(String scanPackage) {
        URL url = this.getClass().getClassLoader().getResource("/" + scanPackage.replaceAll("\\.", "/"));
        File classDir = new File(url.getFile());
        for (File file : classDir.listFiles()) {
            if(file.isDirectory()){
                doScanner(scanPackage + "." + file.getName());
            }else {
                if(!file.getName().endsWith(".class")){continue;}
                String className = (scanPackage + "." + file.getName()).replace(".class","");
                registryBeanClasses.add(className);
            }
        }
    }

    public Properties getConfig(){
        return this.contextConfig;
    }

    private String lowerCaseFirst(String simpleName) {
        char[] chars = simpleName.toCharArray();
        //大写字母和小写字母的ASCII码相差32
        chars[0] += 32;
        return String.valueOf(chars);
    }
}
