public class NBody {
    public static double readRadius(String path) {
        In in = new In(path);
        int numberOfPlanets = in.readInt();
        double radius = in.readDouble();
        return  radius;
    }
    public static Planet[] readPlanets(String path) {
        In in = new In(path);
        int n = in.readInt();
        double r = in.readDouble();
        Planet[] arrayPlanet = new Planet[n];
        for(int i=0; i<n; i+=1) {
            double xpos = in.readDouble();
            double ypos = in.readDouble();
            double xvel = in.readDouble();
            double yvel = in.readDouble();
            double m = in.readDouble();
            String imgGif = in.readString();
            Planet p = new Planet(xpos, ypos, xvel, yvel, m, imgGif);
            arrayPlanet[i] = p;
        }
        return arrayPlanet;
    }
    public static void main(String[] args) {

        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double r = readRadius(filename);
        Planet[] planets = readPlanets(filename);

        StdDraw.enableDoubleBuffering();
        /** Sets up the universe so it goes from
         * -100, -100 up to 100, 100 */
        StdDraw.setScale(-r, r);

        /* Clears the drawing window. */
        StdDraw.clear();

        /* Stamps three copies of advice.png in a triangular pattern. */
        StdDraw.picture(0, 0, "images/starfield.jpg");

        for(Planet p : planets) {
            p.draw();
        }
        /* Shows the drawing to the screen, and waits 2000 milliseconds. */
        StdDraw.show();
        StdDraw.pause(10);

        for(int t=0; t<T; t+=dt){
            double[] xForces = new double[planets.length];
            double[] yForces = new double[planets.length];
            for(int i=0; i<planets.length; i+=1) {
                Planet p = planets[i];
                double netFx = p.calcNetForceExertedByX(planets);
                double netFy = p.calcNetForceExertedByY(planets);
                xForces[i] = netFx;
                yForces[i] = netFy;
            }
            for(int i=0; i<planets.length; i+=1) {
                planets[i].update(dt, xForces[i], yForces[i]);
            }
            StdDraw.clear();
            StdDraw.picture(0, 0, "images/starfield.jpg");
            for(Planet p : planets) {
                p.draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
        }
        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", r);:wq
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                    planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
        }
    }
}
