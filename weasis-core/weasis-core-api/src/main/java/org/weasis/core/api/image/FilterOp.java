/*******************************************************************************
 * Copyright (c) 2016 Weasis Team and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Nicolas Roduit - initial API and implementation
 *******************************************************************************/
package org.weasis.core.api.image;

import java.awt.image.RenderedImage;
import java.awt.image.renderable.ParameterBlock;

import javax.media.jai.JAI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.weasis.core.api.Messages;
import org.weasis.core.api.image.util.KernelData;

public class FilterOp extends AbstractOp {
    private static final Logger LOGGER = LoggerFactory.getLogger(FilterOp.class);

    public static final String OP_NAME = Messages.getString("FilterOperation.title"); //$NON-NLS-1$

    /**
     * Set the filter kernel (Required parameter).
     *
     * org.weasis.core.api.image.util.KernelData value.
     */
    public static final String P_KERNEL_DATA = "kernel"; //$NON-NLS-1$

    public FilterOp() {
        setName(OP_NAME);
    }

    public FilterOp(FilterOp op) {
        super(op);
    }

    @Override
    public FilterOp copy() {
        return new FilterOp(this);
    }

    @Override
    public void process() throws Exception {
        RenderedImage source = (RenderedImage) params.get(Param.INPUT_IMG);
        RenderedImage result = source;
        KernelData kernel = (KernelData) params.get(P_KERNEL_DATA);
        if (kernel != null && !kernel.equals(KernelData.NONE)) {
            ParameterBlock paramBlock = new ParameterBlock();
            paramBlock.addSource(source);
            paramBlock.add(kernel.getKernelJAI());
            result = JAI.create("convolve", paramBlock, null); //$NON-NLS-1$
        }
        params.put(Param.OUTPUT_IMG, result);
    }

}
