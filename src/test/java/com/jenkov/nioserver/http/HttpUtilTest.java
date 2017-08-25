package com.jenkov.nioserver.http;

import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;

/**
 * Created by jjenkov on 19-10-2015.
 */
public class HttpUtilTest {

    @Test
    public void testResolveHttpMethod() throws UnsupportedEncodingException {
        assertHttpMethod("GET / HTTP/1.1\r\n" , HttpHeaders.HTTP_METHOD_GET);
        assertHttpMethod("POST / HTTP/1.1\r\n", HttpHeaders.HTTP_METHOD_POST);
        assertHttpMethod("PUT / HTTP/1.1\r\n", HttpHeaders.HTTP_METHOD_PUT);
        assertHttpMethod("HEAD / HTTP/1.1\r\n", HttpHeaders.HTTP_METHOD_HEAD);
        assertHttpMethod("DELETE / HTTP/1.1\r\n", HttpHeaders.HTTP_METHOD_DELETE);
    }

    private void assertHttpMethod(String httpRequest, int httpMethod) throws UnsupportedEncodingException {
        byte[] source = httpRequest.getBytes("UTF-8");
        HttpHeaders httpHeaders = new HttpHeaders();

        HttpUtil.resolveHttpMethod(source, 0, httpHeaders);
        assertEquals(httpMethod, httpHeaders.httpMethod);
    }



    @Test
    public void testParseHttpRequest() throws UnsupportedEncodingException {
        String httpRequest =
                "GET / HTTP/1.1\r\n\r\n";

        byte[] source = httpRequest.getBytes("UTF-8");
        HttpHeaders httpHeaders = new HttpHeaders();

        HttpUtil.parseHttpRequest(source, 0, source.length, httpHeaders);

        assertEquals(0, httpHeaders.contentLength);

        httpRequest =
                "GET / HTTP/1.1\r\n" +
                "Content-Length: 5\r\n" +
                "\r\n1234";
        source = httpRequest.getBytes("UTF-8");

        assertEquals(-1, HttpUtil.parseHttpRequest(source, 0, source.length, httpHeaders));
        assertEquals(5, httpHeaders.contentLength);


        httpRequest =
                "GET / HTTP/1.1\r\n" +
                "Content-Length: 5\r\n" +
                "\r\n12345";
        source = httpRequest.getBytes("UTF-8");

        assertEquals(42, HttpUtil.parseHttpRequest(source, 0, source.length, httpHeaders));
        assertEquals(5, httpHeaders.contentLength);


        httpRequest =
                "GET / HTTP/1.1\r\n" +
                "Content-Length: 5\r\n" +
                "\r\n12345" +
                "GET / HTTP/1.1\r\n" +
                "Content-Length: 5\r\n" +
                "\r\n12345";

        source = httpRequest.getBytes("UTF-8");

        assertEquals(42, HttpUtil.parseHttpRequest(source, 0, source.length, httpHeaders));
        assertEquals(5, httpHeaders.contentLength);
        assertEquals(37, httpHeaders.bodyStartIndex);
        assertEquals(42, httpHeaders.bodyEndIndex);
    }



}
