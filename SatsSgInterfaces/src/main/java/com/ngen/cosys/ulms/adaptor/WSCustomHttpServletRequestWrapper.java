package com.ngen.cosys.ulms.adaptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class WSCustomHttpServletRequestWrapper extends HttpServletRequestWrapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(WSCustomHttpServletRequestWrapper.class);

    private  String body=null;

    private String getRequestBody(HttpServletRequest httpServletRequest) {
        if (httpServletRequest.getMethod().equals("POST")) {
            StringBuffer sb = new StringBuffer();
            BufferedReader bufferedReader = null;
            String content = "";

            try {
                bufferedReader = httpServletRequest.getReader();
                char[] charBuffer = new char[128];
                int bytesRead;
                while ((bytesRead = bufferedReader.read(charBuffer)) != -1) {
                    sb.append(charBuffer, 0, bytesRead);
                }
            } catch (IOException ex) {
            } finally {
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException ex) {
                    }
                }
            }

            return sb.toString();
        }
        return null;
    }

    public WSCustomHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        body = this.getRequestBody(request);
    }

    public String getBody() {
        return body;
    }


    @Override
    public ServletInputStream getInputStream()  {
        final ByteArrayInputStream bais = new ByteArrayInputStream(body.getBytes());
        return new ServletInputStream() {

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read(){
                return bais.read();
            }
        };
    }

    @Override
    public BufferedReader getReader(){
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }


}
