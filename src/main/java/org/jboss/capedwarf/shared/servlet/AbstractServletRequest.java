package org.jboss.capedwarf.shared.servlet;

import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * Abstract servlet request.
 *
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
public abstract class AbstractServletRequest implements ServletRequest {
    private ServletContext context;
    private Map<String, Set<String>> parameters = new HashMap<>();
    private Map<String, Object> attributes = new HashMap<>();
    private String charEncoding = "UTF-8";

    protected AbstractServletRequest(ServletContext context) {
        if (context == null)
            throw new IllegalArgumentException("Null context");
        this.context = context;
    }

    public void setParameters(String key, Set<String> values) {
        parameters.put(key, values);
    }

    public void addParameters(Map<String, Set<String>> map) {
        parameters.putAll(map);
    }

    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    public Enumeration<String> getAttributeNames() {
        return Collections.enumeration(attributes.keySet());
    }

    public String getCharacterEncoding() {
        return charEncoding;
    }

    public void setCharacterEncoding(String env) throws UnsupportedEncodingException {
        charEncoding = env;
    }

    public int getContentLength() {
        return -1;
    }

    public String getContentType() {
        return null; // TODO
    }

    public ServletInputStream getInputStream() throws IOException {
        return NoopServletInputStream.INSTANCE;
    }

    public String getParameter(String name) {
        final Set<String> strings = parameters.get(name);
        return strings != null && strings.size() > 0 ? strings.iterator().next() : null;
    }

    public Enumeration<String> getParameterNames() {
        return Collections.enumeration(parameters.keySet());
    }

    public String[] getParameterValues(String name) {
        Set<String> set = parameters.get(name);
        return (set != null) ? set.toArray(new String[set.size()]) : null;
    }

    public Map<String, String[]> getParameterMap() {
        final Map<String, String[]> map = new HashMap<>();
        for (Map.Entry<String, Set<String>> entry : parameters.entrySet()) {
            map.put(entry.getKey(), entry.getValue().toArray(new String[entry.getValue().size()]));
        }
        return map;
    }

    public String getProtocol() {
        return null; // TODO
    }

    public String getScheme() {
        return "http";  // TODO
    }

    public String getServerName() {
        return null;  // TODO
    }

    public int getServerPort() {
        return 0;  // TODO
    }

    public BufferedReader getReader() throws IOException {
        return null;  // TODO
    }

    public String getRemoteAddr() {
        return null;  // TODO
    }

    public String getRemoteHost() {
        return null;  // TODO
    }

    public void setAttribute(String name, Object o) {
        attributes.put(name, o);
    }

    public void removeAttribute(String name) {
        attributes.remove(name);
    }

    public Locale getLocale() {
        return Locale.getDefault();
    }

    public Enumeration<Locale> getLocales() {
        return Collections.enumeration(Collections.singleton(getLocale()));
    }

    public boolean isSecure() {
        return false; // TODO
    }

    public RequestDispatcher getRequestDispatcher(String path) {
        return context.getRequestDispatcher(path);
    }

    public String getRealPath(String path) {
        return null;  // TODO
    }

    public int getRemotePort() {
        return 0;  // TODO
    }

    public String getLocalName() {
        return null;  // TODO
    }

    public String getLocalAddr() {
        return null;  // TODO
    }

    public int getLocalPort() {
        return 0;  // TODO
    }

    public ServletContext getServletContext() {
        return context;
    }

    public AsyncContext startAsync() throws IllegalStateException {
        return null; // TODO
    }

    public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) throws IllegalStateException {
        return null; // TODO
    }

    public boolean isAsyncStarted() {
        return false;
    }

    public boolean isAsyncSupported() {
        return false;
    }

    public AsyncContext getAsyncContext() {
        return null; // TODO
    }

    public DispatcherType getDispatcherType() {
        return DispatcherType.REQUEST;
    }
}
