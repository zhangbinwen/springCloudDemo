package com.atguigu.springcloud.util;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
* Description:
* @author: zhangbinwen
* @date: 2019/12/20 10:59
* @param:
* @return:
*/
public class SpringBeanUtils {
    public synchronized static <T> T getBean(String beanName, Class<T> tClass) {
        if (null != WebfluxContext.getInstance()) {
            return WebfluxContext.getInstance().getApplicationContext().getBean(beanName, tClass);
        }
        ApplicationContext app = WebApplicationContextUtils.getWebApplicationContext(RequestHolder.getRequest().getServletContext());
        return app.getBean(beanName, tClass);
    }
}
