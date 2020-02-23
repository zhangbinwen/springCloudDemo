package com.atguigu.springcloud.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * Created by dylan on 15-2-7. 需要配置
 * {@link org.springframework.web.context.request.RequestContextListener}
 *
 * @see org.springframework.web.context.request.RequestContextListener
 */
public class RequestHolder {
    private static Logger logger = LoggerFactory.getLogger(RequestHolder.class);

    public static HttpServletRequest getRequest() {
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return req;
    }

    public static HttpServletResponse getResponse() {
        HttpServletResponse resp = ((ServletWebRequest) RequestContextHolder.getRequestAttributes()).getResponse();
        return resp;
    }

    public static HttpSession getSession() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession(true);
    }

    public static HttpSession getSession(boolean create) {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession(create);
    }


    public static String getReq(HttpServletRequest req) throws Exception {
        int contentLength = req.getContentLength();
        ByteBuffer buffer = ByteBuffer.allocate(contentLength);
        ServletInputStream input = req.getInputStream();
        byte[] bbuf = new byte[256];
        for (int len; (len = input.read(bbuf)) > -1; ) {
            buffer.put(bbuf, 0, len);
        }
        buffer.flip();
        String json = Charset.forName("UTF-8").decode(buffer).toString();
        return json;
    }

    public static String getReqJsonContentType(HttpServletRequest req) throws Exception {
        String inputLine;
        String notityJson = "";
        try {
            while ((inputLine = req.getReader().readLine()) != null) {
                notityJson += inputLine;
            }
            req.getReader().close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        logger.debug("接收数据[{}]", notityJson);
        return notityJson;
    }


}
