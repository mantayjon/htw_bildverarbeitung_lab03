// BV Ue3 SS2023 Vorgabe
//
// Copyright (C) 2023 by Klaus Jung
// All rights reserved.
// Date: 2023-03-23


package bv_ss23;

import java.util.Arrays;

public class MorphologicFilter {

    // filter implementations go here:

    public void copy(RasterImage src, RasterImage dst) {
        System.arraycopy(src.argb, 0, dst.argb, 0, src.argb.length);
    }


    public void dilation(RasterImage src, RasterImage dst, boolean[][] kernel) {
        // kernel's first dimension: y (row), second dimension: x (column)
        Arrays.fill(dst.argb, 0xFFFFFFFF);

        int w = src.width;
        int h = src.height;
        int hotspot = kernel[0].length / 2;
        int kernelSize = kernel[0].length;
        int black = 0xFF000000;

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {

                int pos = y * w + x;

                if (src.argb[pos] == black) {

                    for (int yKernel = 0; yKernel < kernelSize; yKernel++) {
                        for (int xKernel = 0; xKernel < kernelSize; xKernel++) {
                            if (kernel[yKernel][xKernel]) {
                                int posX = x + xKernel - hotspot;
                                int posY = y + yKernel - hotspot;

                                // randbehandlung
                                if (posY >= 0 && posY < h && posX >= 0 && posX < w) {
                                    dst.argb[posY * w + posX] = black;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void erosion(RasterImage src, RasterImage dst, boolean[][] kernel) {
        // This is already implemented. Nothing to do.
        // It will function once you implemented dilation and RasterImage invert()
        src.invert();
        dilation(src, dst, kernel);
        dst.invert();
        src.invert();
    }

    public void opening(RasterImage src, RasterImage dst, boolean[][] kernel) {
        RasterImage temp = new RasterImage(src.width, src.height);
        erosion(src, temp, kernel);
        dilation(temp, dst, kernel);
    }

    public void closing(RasterImage src, RasterImage dst, boolean[][] kernel) {
        RasterImage temp = new RasterImage(src.width, src.height);
        dilation(src, temp, kernel);
        erosion(temp, dst, kernel);
    }


}





