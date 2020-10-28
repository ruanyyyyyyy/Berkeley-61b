import edu.princeton.cs.algs4.Picture;

import java.awt.*;

public class SeamCarver {

    private Picture pic;
    private int width, height;
    private double[][] energy;

    public SeamCarver(Picture picture) {
        pic = picture;
        width = picture.width();
        height = picture.height();
        energy = new double[width][height];
        energyHelper();
    }

    /* current picture */
    public Picture picture() {
        return pic;
    }

    /* width of current picture */
    public int width() {
        return this.width;
    }

    /* height of current picture */
    public int height() {
        return this.height;
    }

    /* calculate energy for all pixels and store them*/
    private void energyHelper() {
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                Color c1 = pic.get((x + 1) % width, y);
                Color c2 = pic.get((x - 1 + width) % width, y);
                int deltax = (c1.getRed() - c2.getRed()) * (c1.getRed() - c2.getRed()) +
                        (c1.getGreen() - c2.getGreen()) * (c1.getGreen() - c2.getGreen()) +
                        (c1.getBlue() - c2.getBlue()) * (c1.getBlue() - c2.getBlue());
                Color c3 = pic.get(x, (y + 1) % height);
                Color c4 = pic.get(x, (y - 1 + height) % height);
                int deltay = (c3.getRed() - c4.getRed()) * (c3.getRed() - c4.getRed()) +
                        (c3.getGreen() - c4.getGreen()) * (c3.getGreen() - c4.getGreen()) +
                        (c3.getBlue() - c4.getBlue()) * (c3.getBlue() - c4.getBlue());
                energy[x][y] = deltax * deltax + deltay * deltay;
            }
        }
    }

    /* energy of pixel at column x and row y */
    public  double energy(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        return energy[x][y];
    }

    private int findMinIndex(double[][] M) {
        int col = M.length; //x
        int row = M[0].length; //y
        double min = M[0][row-1];
        int index = 0;
        for (int x = 0; x < col-1; x += 1) {
            if (min > M[x][row-1]) {
                min = M[x][row-1];
                index = x;
            }
        }
        return index;
    }

    private int findMin2(double a, double b, int aI, int bI) {
        if (a < b) {
            return aI;
        } else {
            return bI;
        }
    }

    private int findMin3(double a, double b, double c, int aI, int bI, int cI) {
        if (a < b && a < c) {
            return aI;
        }
        if (b < a && b < c) {
            return bI;
        }
        if (c < a && c < b) {
            return cI;
        }
        if (a == b || a == c) {
            return aI;
        } else {
            return bI;
        }
    }

    /* sequence of indices for horizontal seam*/
    public int[] findHorizontalSeam() {
        int[] horizSeam = new int[height];
        double[][] M = new double[width][height];
        for (int x = 0; x < width; x += 1) {
            M[x][0] = energy[x][0];
        }
        for (int y = 1; y < height; y += 1) {
            for (int x = 1; x < width - 1; x += 1) {
                M[x][y] = energy[x][y] +
                        Math.min(Math.min(M[x-1][y-1], M[x][y-1]), M[x+1][y-1]);
            }
            M[0][y] = energy[0][y] + Math.min(M[0][y-1], M[1][y-1]);
            M[width-1][y] = energy[width-1][y] + Math.min(M[width-2][y-1], M[width-1][y-1]);
        }
        int index = findMinIndex(M);
        horizSeam[height-1] = index;
        for (int y = height - 2; y >= 0; y -= 1) {
            if (index == 0) {
                horizSeam[y] = findMin2(M[index][y], M[index+1][y], index, index + 1);
            } else if (index == width - 1) {
                horizSeam[y] = findMin2(M[index-1][y], M[index][y], index - 1, index);
            } else {
                horizSeam[y] = findMin3(M[index-1][y], M[index][y], M[index+1][y],
                        index - 1, index, index+1);
            }
            index = horizSeam[y];
        }
        return horizSeam;
    }

    /* sequence of indices for vertical seam */
    public int[] findVerticalSeam() {
        int[] vertSeam = new int[width];
        return vertSeam;

    }

    /* remove horizontal seam from picture */
    public void removeHorizontalSeam(int[] seam) {
        pic = SeamRemover.removeHorizontalSeam(this.pic, seam);
    }

    /* remove vertical seam from picture */
    public void removeVerticalSeam(int[] seam) {

    }
}
