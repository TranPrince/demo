package com.prince.demo.contraller;


import com.prince.demo.service.IDemoService;
import com.prince.framework.v2.annotation.Autowired;
import com.prince.framework.v2.annotation.Controller;
import com.prince.framework.v2.annotation.RequestMapping;
import com.prince.framework.v2.annotation.RequestParam;
import com.prince.framework.v2.webmvc.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    private IDemoService demoService;

    @RequestMapping("/query")
    public void query(HttpServletRequest req, HttpServletResponse resp,
                      @RequestParam("name") String name){
//        System.out.println("Welcome!" + demoService.get(name));
        try {
            resp.getWriter().write("Welcome!" + demoService.get(name));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/test")
    public ModelAndView test(){
        Map<String,Object> model = new HashMap<String, Object>();

        model.put("name","Prince");
        model.put("data",123);

        return new ModelAndView("index.html" , model);
    }

    @RequestMapping("/add")
    public ModelAndView add(HttpServletRequest req, HttpServletResponse resp,
                            @RequestParam("name") String name){
        Map<String,Object> model = new HashMap<String, Object>();

        model.put("name",name);
        model.put("time",new Date());
        model.put("data",new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss").format(new Date()));


//        ConcurrentHashMap

        return new ModelAndView("index.html" , model);
    }

}
