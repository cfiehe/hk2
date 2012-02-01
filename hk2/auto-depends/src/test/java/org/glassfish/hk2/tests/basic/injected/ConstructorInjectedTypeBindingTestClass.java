/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2011-2012 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * http://glassfish.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */
package org.glassfish.hk2.tests.basic.injected;

import org.glassfish.hk2.tests.basic.annotations.MarkerA;
import org.jvnet.hk2.annotations.Inject;
import org.glassfish.hk2.Factory;
import org.glassfish.hk2.tests.basic.arbitrary.ClassX;
import org.glassfish.hk2.tests.basic.contracts.ContractA;
import org.glassfish.hk2.tests.basic.contracts.ContractB;
import org.glassfish.hk2.tests.basic.contracts.GenericContract;
import org.glassfish.hk2.tests.basic.contracts.GenericContractStringImpl;
import org.glassfish.hk2.tests.basic.contracts.GenericFactoryProvidedContract;
import org.glassfish.hk2.tests.basic.services.ServiceA;
import org.glassfish.hk2.tests.basic.services.ServiceB;
import org.glassfish.hk2.tests.basic.services.ServiceC;
import static org.glassfish.hk2.tests.basic.AssertionUtils.*;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author Marek Potociar (marek.potociar at oracle.com)
 */
public class ConstructorInjectedTypeBindingTestClass {

    final ServiceC sc;
    final ClassX cx;
    final ContractB cb;
    final ContractA ca;
    final GenericContract<String> gc;
    final GenericContract<String> gcm;
    final Factory<ServiceC> scp;
    final Factory<ClassX> cxp;
    final Factory<ContractB> cbp;
    final Factory<ContractA> cap;
    final Factory<GenericContract<String>> gcp;
    final Factory<GenericContract<String>> gcpm;
    final GenericFactoryProvidedContract<String> gfpc;

    public ConstructorInjectedTypeBindingTestClass(@Inject ServiceC sc, @Inject ClassX cx, @Inject ContractB cb, @Inject ContractA ca, @Inject GenericContract<String> gc, @Inject @MarkerA GenericContract<String> gcm, @Inject Factory<ServiceC> scp, @Inject Factory<ClassX> cxp, @Inject Factory<ContractB> cbp, @Inject Factory<ContractA> cap, @Inject Factory<GenericContract<String>> gcp, @Inject @MarkerA Factory<GenericContract<String>> gcpm, @Inject GenericFactoryProvidedContract<String> gfpc) {
        this.sc = sc;
        this.cx = cx;
        this.cb = cb;
        this.ca = ca;
        this.gc = gc;
        this.gcm = gcm;
        this.scp = scp;
        this.cxp = cxp;
        this.cbp = cbp;
        this.cap = cap;
        this.gcp = gcp;
        this.gcpm = gcpm;
        this.gfpc = gfpc;
    }

    public void assertInjection() {
        assertInjectedInstance(ServiceC.class, this.sc);
        assertInjectedInstance(ClassX.class, this.cx);
        assertInjectedInstance(ServiceB.class, this.cb);
        assertInjectedInstance(ClassX.class, this.cb.getX());
        assertInjectedInstance(ServiceA.class, this.ca);
        assertInjectedInstance(ServiceB.class, this.ca.getB());
        assertInjectedInstance(ClassX.class, this.ca.getB().getX());
        assertInjectedInstance(GenericContractStringImpl.class, this.gc);
        assertEquals(MarkerA.class.getName(), gcm.foo());

        assertInjectedFactory(ServiceC.class, this.scp);
        assertInjectedFactory(ClassX.class, this.cxp);
        assertInjectedFactory(ServiceB.class, this.cbp);
        assertInjectedInstance(ClassX.class, this.cbp.get().getX());
        assertInjectedFactory(ServiceA.class, this.cap);
        assertInjectedInstance(ServiceB.class, this.cap.get().getB());
        assertInjectedInstance(ClassX.class, this.cap.get().getB().getX());
        assertInjectedFactory(GenericContractStringImpl.class, this.gcp);
        assertEquals(MarkerA.class.getName(), gcpm.get().foo());
    }
}
