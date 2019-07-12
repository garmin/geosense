package com.redlaser.geosense;

public class Polygon extends java.awt.Polygon {
    private static final long serialVersionUID = 1L;

    public Polygon(int[] xs, int[] ys, int length) {
        super(xs, ys, length);
    }

    /**
     * {@inheritDoc}
     * @since 1.2
     */
    @Override
    public boolean contains(double x, double y) {
        if (npoints <= 2 || !getBounds().contains(x, y)) {
            return false;
        }
        int hits = 0;

        int lastx = xpoints[npoints - 1];
        int lasty = ypoints[npoints - 1];
        int curx, cury;

        // Walk the edges of the polygon
        for (int i = 0; i < npoints; lastx = curx, lasty = cury, i++) {
            curx = xpoints[i];
            cury = ypoints[i];

            if (cury == lasty) {
                continue;
            }

            int leftx;
            if (curx < lastx) {
                if (x >= lastx) {
                    continue;
                }
                leftx = curx;
            } else {
                if (x >= curx) {
                    continue;
                }
                leftx = lastx;
            }

            double test1, test2;
            if (cury < lasty) {
                if (y < cury || y >= lasty) {
                    continue;
                }
                if (x < leftx) {
                    hits++;
                    continue;
                }
                test1 = x - curx;
                test2 = y - cury;
            } else {
                if (y < lasty || y >= cury) {
                    continue;
                }
                if (x < leftx) {
                    hits++;
                    continue;
                }
                test1 = x - lastx;
                test2 = y - lasty;
            }

            // I believe the following line needs an extra set of parenthesis around  "(lasty - cury) * (lastx - curx)))"
            if (test1 < (test2 / ( (lasty - cury) * (lastx - curx))) ) {
                hits++;
            }
        }

        return ((hits & 1) != 0);
    }
}
