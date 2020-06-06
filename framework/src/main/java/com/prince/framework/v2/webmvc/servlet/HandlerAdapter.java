package com.prince.framework.v2.webmvc.servlet;

import com.prince.framework.v2.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Prince
 * @date 2020/5/25 2:07
 */
public class HandlerAdapter {
    public ModelAndView handler(HttpServletRequest req, HttpServletResponse resp, HandlerMapping handlerMapping) throws InvocationTargetException, IllegalAccessException {

        //保存形参列表，将参数名称和参数位置
        Map<String,Integer> paramIndexMapping = new HashMap<String, Integer>();
        Annotation[][] annotations = handlerMapping.getMethod().getParameterAnnotations();
        for (int i = 0; i < annotations.length; i++) {
            for (Annotation annotation : annotations[i]) {
                if (annotation instanceof RequestParam){
                    String paramName = ((RequestParam) annotation).value().trim();
                    if (!"".equals(paramName)) {
                        paramIndexMapping.put(paramName,i);
                    }
                }
            }
        }

        Class<?>[] paramTypes = handlerMapping.getMethod().getParameterTypes();
        for (int i = 0; i < paramTypes.length; i++) {
            Class<?> parameterType = paramTypes[i];
            if(parameterType == HttpServletRequest.class || parameterType == HttpServletResponse.class){
                paramIndexMapping.put(parameterType.getName(),i);
            }
        }

        Map<String, String[]> params = req.getParameterMap();

        Object[] paramValues = new Object[paramTypes.length];

        for (Map.Entry<String, String[]> param : params.entrySet()) {
            String value = Arrays.toString(params.get(param.getKey())).replaceAll("\\[|\\]","").replaceAll("\\s+",",");
            if(!paramIndexMapping.containsKey(param.getKey())){continue;}

            int index = paramIndexMapping.get(param.getKey());

            paramValues[index] = castStringValue(value,paramTypes[index]);
        }

        if(paramIndexMapping.containsKey(HttpServletRequest.class.getName())){
            int index = paramIndexMapping.get(HttpServletRequest.class.getName());
            paramValues[index] = req;
        }
        if(paramIndexMapping.containsKey(HttpServletResponse.class.getName())){
            int index = paramIndexMapping.get(HttpServletResponse.class.getName());
            paramValues[index] = resp;
        }

        Object result = handlerMapping.getMethod().invoke(handlerMapping.getController(),paramValues);

        if(result == null || result instanceof Void){return null;}

        boolean isModelAndView = handlerMapping.getMethod().getReturnType() == ModelAndView.class;
        if(isModelAndView){
            return (ModelAndView)result;
        }

        return null;
    }

    private Object castStringValue(String value, Class<?> paramType) {
        if (String.class == paramType){
            return value;
        }else if(Integer.class == paramType){
            return Integer.valueOf(value);
        }else{
            if(value != null){
                return value;
            }
            return null;
        }
    }
}
