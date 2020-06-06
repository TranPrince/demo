package com.prince.framework.v2.webmvc.servlet;

import com.prince.framework.v2.annotation.*;
import com.prince.framework.v2.context.ApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DispatcherServlet extends HttpServlet {


    private List<HandlerMapping> handlerMappings = new ArrayList<HandlerMapping>();
    private ApplicationContext applicationContext;
    private Map<HandlerMapping,HandlerAdapter> handlerAdapterMap = new HashMap<HandlerMapping, HandlerAdapter>();
    private List<ViewResolver> viewResolvers = new ArrayList<ViewResolver>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            doDispatch(req,resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().write("500 Exception Detail!");
        }
    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //完成了对HandlerMapping的封装
        //完成了对方法返回值的封装ModelAndView

        //1、通过URL获得一个HandlerMapping
        HandlerMapping handlerMapping = getHandler(req);

        if(handlerMapping == null){
            processDispatchResult(req,resp,new ModelAndView("404"));
            return;
        }

        //2、根据一个HandlerMapping获得一个HandlerAdapter
        HandlerAdapter handlerAdapter = getHandlerAdapter(handlerMapping);

        //3、解析某一个方法的形参和返回值之后，统一封装为ModelAndView对象
        ModelAndView modelAndView = handlerAdapter.handler(req,resp,handlerMapping);

        processDispatchResult(req,resp,modelAndView);

    }

    private HandlerAdapter getHandlerAdapter(HandlerMapping handlerMapping) {
        if(this.handlerAdapterMap.isEmpty()){return null;}
        return this.handlerAdapterMap.get(handlerMapping);
    }

    private void processDispatchResult(HttpServletRequest req, HttpServletResponse resp, ModelAndView modelAndView) throws Exception {
        if(modelAndView == null){return;}
        if(this.viewResolvers.isEmpty()){return;}
        for (ViewResolver viewResolver : this.viewResolvers) {
            View view = viewResolver.resolveViewName(modelAndView.getViewName());
            view.render(modelAndView.getModel(),req,resp);
        }

    }

    private HandlerMapping getHandler(HttpServletRequest req) {
        if(this.handlerMappings.isEmpty()){return null;}
        String url = req.getRequestURI();
        String contextPath = req.getContextPath();
        url = url.replace(contextPath,"").replaceAll("/+","/");
        for (HandlerMapping mapping : handlerMappings) {
            Matcher matcher = mapping.getPattern().matcher(url);
            if(!matcher.matches()){continue;}
            return mapping;
        }
        return null;
    }

    @Override
    public void init(ServletConfig config) {

        applicationContext = new ApplicationContext(config.getInitParameter("contextConfigLocation"));

        initStrategies(applicationContext);

        System.out.println("Prince's SpringMVC is init.");

    }

    private void initStrategies(ApplicationContext context) {

        //多文件上传的组件
//        initMultipartResolver(context);
//        //初始化本地语言环境
//        initLocaleResolver(context);
//        //初始化模板处理器
//        initThemeResolver(context);
        //handlerMapping
        initHandlerMappings(context);
        //初始化参数适配器
        initHandlerAdapters(context);
//        //初始化异常拦截器
//        initHandlerExceptionResolvers(context);
//        //初始化视图预处理器
//        initRequestToViewNameTranslator(context);
        //初始化视图转换器
        initViewResolvers(context);
//        //FlashMap管理器
//        initFlashMapManager(context);
    }

    private void initViewResolvers(ApplicationContext context) {
        String templateRoot = context.getConfig().getProperty("templateRoot");
        String templateRootPath = this.getClass().getClassLoader().getResource(templateRoot).getFile();

        File templateRootDir = new File(templateRootPath);

        for (File file : templateRootDir.listFiles()) {
            this.viewResolvers.add(new ViewResolver(templateRoot));
        }


    }

    private void initHandlerAdapters(ApplicationContext context) {
        for (HandlerMapping handlerMapping : handlerMappings) {
            this.handlerAdapterMap.put(handlerMapping,new HandlerAdapter());
        }
    }

    private void initHandlerMappings(ApplicationContext context) {
        if(context.getBeanDefinitionCount() == 0){return;}
        for (String beanName : context.getBeanDefinitionNames()) {
            Object instance = context.getBean(beanName);
            Class<?> clazz = instance.getClass();
            if (clazz.isAnnotationPresent(Controller.class)){
                String baseUrl = "";
                if (clazz.isAnnotationPresent(RequestMapping.class)){
                    RequestMapping requestMapping = clazz.getAnnotation(RequestMapping.class);
                    baseUrl = requestMapping.value();
                }

                Method[] methods = clazz.getMethods();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(RequestMapping.class)){
                        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                        String regex = ("/" + baseUrl + "/" + requestMapping.value().replace("\\*",".*")).replaceAll("/+","/");
                        Pattern pattern = Pattern.compile(regex);
                        handlerMappings.add(new HandlerMapping(pattern,instance,method));
                        System.out.println("Mapped " + pattern + " into " + method);
                    }
                }
            }
        }
    }
}
