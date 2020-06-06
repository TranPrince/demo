package com.prince.framework.v2.webmvc.servlet;

import java.io.File;

/**
 * @author Prince
 * @date 2020/5/25 3:14
 */
public class ViewResolver {
    private String DEFAULT_TEMPLATE_SUFFIX = ".html";
    private File templateRootDir;
    public ViewResolver(String templateRoot) {
        String templateRootPath = this.getClass().getClassLoader().getResource(templateRoot).getFile();
        templateRootDir = new File(templateRootPath);
    }

    public View resolveViewName(String viewName){
        if(null == viewName || "".equals(viewName.trim())){return null;}
        viewName = viewName.endsWith(DEFAULT_TEMPLATE_SUFFIX) ? viewName : (viewName + DEFAULT_TEMPLATE_SUFFIX);
        File templateFile = new File((templateRootDir.getPath() + "/" + viewName).replaceAll("/+","/"));
        return new View(templateFile);
    }
}
