import edu.princeton.cs.algs4.Picture;

import java.awt.*;

public class SeamCarver {

    private Picture pic;
    private int width, height;
    private double[][] energy;

    public SeamCarver(Picture picture) {
        pic = new Picture(picture);
        width = picture.width();
        height = picture.height();
        energy = new double[width][height];
        energyHelper();
    }

    /* current picture */
    public Picture picture() {
        Picture new_pic = new Picture(pic);
        return new_pic;
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
                energy[x][y] = deltax + deltay;
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
        double min = M[0][0];
        int index = 0;
        for (int x = 0; x < col-1; x += 1) {
            if (min > M[x][0]) {
                min = M[x][0];
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
        energy = transposeImage(energy);
        int[] horizSeam = findVerticalSeam();
        energy = transposeImage(energy);
        return horizSeam;
    }

    /* sequence of indices for vertical seam */
    public int[] findVerticalSeam() {
        int wid = energy.length;
        int het = energy[0].length;
        int[] vertSeam = new int[het];
        double[][] M = new double[wid][het];
        for (int x = 0; x < wid; x += 1) {
            M[x][0] = energy[x][0];
        }
        for (int y = 1; y < het; y += 1) {
            for (int x = 1; x < wid - 1; x += 1) {
                M[x][y] = energy[x][y] +
                        Math.min(Math.min(M[x-1][y-1], M[x][y-1]), M[x+1][y-1]);
            }
            M[0][y] = energy[0][y] + Math.min(M[0][y-1], M[1][y-1]);
            M[wid-1][y] = energy[wid-1][y] + Math.min(M[wid-2][y-1], M[wid-1][y-1]);
        }
        int index = findMinIndex(M);
        vertSeam[0] = index;
        for (int y = 1; y < het; y += 1) {
            if (index == 0) {
                vertSeam[y] = findMin2(M[index][y], M[index+1][y], index, index + 1);
            } else if (index == wid - 1) {
                vertSeam[y] = findMin2(M[index-1][y], M[index][y], index - 1, index);
            } else {
                vertSeam[y] = findMin3(M[index-1][y], M[index][y], M[index+1][y],
                        index - 1, index, index+1);
            }
            index = vertSeam[y];
        }
        return vertSeam;

    }

    /* transpose an image */
    private double[][] transposeImage(double[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;

        double[][] transposedMatrix = new double[n][m];

        for(int x = 0; x < n; x += 1) {
            for (int y = 0; y < m; y += 1) {
                transposedMatrix[x][y] = matrix[y][x];
            }
        }
        return transposedMatrix;
    }

    /* remove horizontal seam from picture */
    public void removeHorizontalSeam(int[] seam) {
        this.pic = SeamRemover.removeHorizontalSeam(this.pic, seam);
    }

    /* remove vertical seam from picture */
    public void removeVerticalSeam(int[] seam) {
        this.pic = SeamRemover.removeVerticalSeam(this.pic, seam);
    }
}
