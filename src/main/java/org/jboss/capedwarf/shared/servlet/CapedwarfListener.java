/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2013, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jboss.capedwarf.shared.servlet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

import org.infinispan.manager.EmbeddedCacheManager;

/**
 * Capedwarf listener.
 * * registers servlet context -- tasks API
 * * holds classloaders -- so we know which apps are CD apps from TCCL
 *
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
public class CapedwarfListener implements ServletContextListener, ServletRequestListener {
    private static final String JNDI_NAME = "java:jboss/infinispan/container/capedwarf";

    public void contextInitialized(ServletContextEvent sce) {
        final ServletContext context = sce.getServletContext();
        final String appId = (String) context.getAttribute("org.jboss.capedwarf.appId");
        final String module = (String) context.getAttribute("org.jboss.capedwarf.module");

        CapedwarfApiProxy.initialize(appId, module, context);
    }

    public void contextDestroyed(ServletContextEvent sce) {
        final ServletContext context = sce.getServletContext();
        final String appId = (String) context.getAttribute("org.jboss.capedwarf.appId");
        final String module = (String) context.getAttribute("org.jboss.capedwarf.module");

        CapedwarfApiProxy.destroy(appId, module, context);
    }

    public void requestInitialized(ServletRequestEvent event) {
        CapedwarfApiProxy.setRequest(event.getServletRequest());
    }

    public void requestDestroyed(ServletRequestEvent event) {
        CapedwarfApiProxy.removeRequest();
    }

    protected EmbeddedCacheManager getManager() {
        try {
            final Context context = new InitialContext();
            try {
                return (EmbeddedCacheManager) context.lookup(JNDI_NAME);
            } finally {
                context.close();
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("No such cache container: " + JNDI_NAME, e);
        }
    }
}
