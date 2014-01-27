/*
 *
 *  * JBoss, Home of Professional Open Source.
 *  * Copyright 2011, Red Hat, Inc., and individual contributors
 *  * as indicated by the @author tags. See the copyright.txt file in the
 *  * distribution for a full listing of individual contributors.
 *  *
 *  * This is free software; you can redistribute it and/or modify it
 *  * under the terms of the GNU Lesser General Public License as
 *  * published by the Free Software Foundation; either version 2.1 of
 *  * the License, or (at your option) any later version.
 *  *
 *  * This software is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *  * Lesser General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU Lesser General Public
 *  * License along with this software; if not, write to the Free
 *  * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 *  * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 *
 */

package org.jboss.test.capedwarf.shared.config.test;


import java.util.Collections;
import java.util.Set;

import com.google.common.collect.Sets;
import org.jboss.capedwarf.shared.config.CapedwarfConfiguration;
import org.jboss.capedwarf.shared.config.InboundMailAccount;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author <a href="mailto:marko.luksa@gmail.com">Marko Luksa</a>
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
public class CapedwarfConfigurationTest {

    @Test
    public void isAdminReturnsTrueForAllAddedAdmins() throws Exception {
        Set<String> set = Sets.newHashSet("admin@email.com", "admin2@email.com");
        TestConfig config = new TestConfig(set);
        assertTrue(config.isAdmin("admin@email.com"));
        assertTrue(config.isAdmin("admin2@email.com"));
    }

    @Test
    public void letterCaseIsIgnored() throws Exception {
        TestConfig config = new TestConfig(Sets.newHashSet("ADMIN@email.com"));
        assertTrue(config.isAdmin("admin@email.com"));
        assertTrue(config.isAdmin("admin@EMAIL.COM"));
        assertTrue(config.isAdmin("AdMiN@EmAiL.CoM"));
    }

    @Test
    public void isAdminReturnsFalseForNotAddedAdmins() throws Exception {
        TestConfig config = new TestConfig(Sets.newHashSet("admin@email.com"));
        assertFalse(config.isAdmin("notadmin@email.com"));
    }

    @Test
    public void getAdminsReturnsAllAddedAdmins() throws Exception {
        Set<String> set = Sets.newHashSet("admin@email.com", "ADMIN@email.com", "admin2@email.com");
        TestConfig config = new TestConfig(set);
        Assert.assertEquals(2, config.getAdmins().size());
        assertTrue(config.getAdmins().contains("admin@email.com"));
        assertTrue(config.getAdmins().contains("admin2@email.com"));
    }

    private static class TestConfig extends CapedwarfConfiguration {
        private TestConfig(Set<String> admins) {
            super(admins, Collections.<InboundMailAccount>emptyList());
        }
    }
}
