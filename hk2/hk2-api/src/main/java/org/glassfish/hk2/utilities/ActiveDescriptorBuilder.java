/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2012 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
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
package org.glassfish.hk2.utilities;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;

import org.glassfish.hk2.api.FactoryDescriptors;
import org.glassfish.hk2.api.HK2Loader;

/**
 * The active descriptor build is for building up a
 * non-reified ActiveDescriptor.  Non-reified active descriptors
 * are useful because upon bind the system will not do further
 * analysis of the associated class file, assuming instead that
 * all the information from the ActiveDescriptor is what the
 * user intended.  This can be used to supply qualifiers that are
 * not marked with {@link Qualifier} or contracts that are not
 * marked with {@link Contract}.  An unreified ActiveDescriptor given
 * to a bind method need not implement the create or destroy method,
 * as they are automatically created and destroyed by the system.
 * 
 * @author jwells
 *
 */
public interface ActiveDescriptorBuilder {
    /**
     * The name for this descriptor object.  Note that at the current time a descriptor
     * can only have one name, hence this method will throw an IllegalArgumentException
     * if named is called more than once.
     * 
     * @param name The name to be associated with this Descriptor
     * @return A DescriptorBuilder with the given name
     * @throws IllegalArgumentException if there is more than one name on the predicate
     */
    public ActiveDescriptorBuilder named(String name) throws IllegalArgumentException;
    
    /**
     * A contract to be associated with this descriptor object.
     * 
     * @param contract A class that is annotated with Contract to
     *   be associated with this Descriptor
     * @return A DescriptorBuilder with the given name
     * @throws IllegalArgumentException on failures
     */
    public ActiveDescriptorBuilder to(Type contract) throws IllegalArgumentException;
    
    /**
     * A scope to be associated with this descriptor object. Note that at the current time a
     * descriptor can only have one scope, hence this method will throw an IllegalArgumentException
     * if in is called more than once.
     * 
     * @param scope The class of the scope this descriptor is to have.
     * @return A DescriptorBuilder with the given scope
     * @throws IllegalArgumentException If in is called more than once
     */
    public ActiveDescriptorBuilder in(Class<? extends Annotation> scope) throws IllegalArgumentException;
    
    /**
     * A qualifier to be associated with this descriptor object
     * 
     * @param annotation The annotation to be associated with this descriptor
     * @return A DescriptorBuilder with the given annotation
     * @throws IllegalArgumentException on failures
     */
    public ActiveDescriptorBuilder qualifiedBy(Annotation annotation) throws IllegalArgumentException;
    
    /**
     * An instance of data to be associated with this descriptor
     * 
     * @param key The key for the data to be associated with this descriptor
     * @param value The value this key should take (single value metadata)
     * @return A DescriptorBuilder with the given metadata
     * @throws IllegalArgumentException
     */
    public ActiveDescriptorBuilder has(String key, String value) throws IllegalArgumentException;
    
    /**
     * An instance of data to be associated with this descriptor
     * 
     * @param key The key for the data to be associated with this descriptor
     * @param values The values this key should take (single value metadata)
     * @return A DescriptorBuilder with the given metadata
     * @throws IllegalArgumentException
     */
    public ActiveDescriptorBuilder has(String key, List<String> values) throws IllegalArgumentException;
    
    /**
     * The rank to be associated with this descriptor.  The last rank
     * bound wins
     * 
     * @param rank The rank to be associated with this descriptor.
     * @return A DescriptorBuilder with the given rank
     */
    public ActiveDescriptorBuilder ofRank(int rank);
    
    /**
     * Call this if this descriptor should be loaded with the given HK2Loader
     * 
     * @param loader The loader to use with this descriptor
     * @return A DescriptorBuilder with the given HK2Loader
     * @throws IllegalArgumentException if the HK2Loader is set non-null more than once
     */
    public ActiveDescriptorBuilder andLoadWith(HK2Loader loader) throws IllegalArgumentException;
    
    /**
     * Generates a descriptor that can be used in binding operations
     * 
     * @return The descriptor that has been built up
     * @throws IllegalArgumentException if the built descriptor is invalid
     */
    public AbstractActiveDescriptor<?> build() throws IllegalArgumentException;
    
    /**
     * Generates a descriptor that can be used in binding operations that
     * describes a factory
     * 
     * @return The descriptor that has been built up, of type FACTORY 
     * @throws IllegalArgumentException if the built descriptor is invalid
     */
    public AbstractActiveDescriptor<?> buildFactory() throws IllegalArgumentException;
}