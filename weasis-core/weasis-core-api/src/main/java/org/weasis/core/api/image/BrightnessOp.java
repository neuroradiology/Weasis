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

public class BrightnessOp extends AbstractOp {
    private static final Logger LOGGER = LoggerFactory.getLogger(BrightnessOp.class);

    public static final String OP_NAME = "rescale";

    public static final String P_BRIGTNESS_VALUE = "rescale.brightness";
    public static final String P_CONTRAST_VALUE = "rescale.contrast";

    public BrightnessOp() {
        setName(OP_NAME);
    }

    public BrightnessOp(BrightnessOp op) {
        super(op);
    }

    @Override
    public BrightnessOp copy() {
        return new BrightnessOp(this);
    }

    @Override
    public void process() throws Exception {
        RenderedImage source = (RenderedImage) params.get(Param.INPUT_IMG);
        RenderedImage result = source;

        Double contrast = (Double) params.get(P_CONTRAST_VALUE);
        Double brigtness = (Double) params.get(P_BRIGTNESS_VALUE);

        if (contrast != null && brigtness != null) {
            double[] constants = { contrast / 100D };
            double[] offsets = { brigtness };

            ParameterBlock pb = new ParameterBlock();
            pb.addSource(source);
            pb.add(constants);
            pb.add(offsets);

            result = JAI.create("rescale", pb, null);
        }

        params.put(Param.OUTPUT_IMG, result);
    }

}
