public class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    private static final double G = 6.67e-11;

    public Planet(double xP, double yP, double xV, double yV, double m, String img){
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }
    public Planet(Planet p) {
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;
    }
    public double calcDistance(Planet anotherP) {
        double square_r = Math.pow(xxPos - anotherP.xxPos,2) + Math.pow(yyPos - anotherP.yyPos,2);
        return Math.sqrt(square_r);
    }
    public double calcForceExertedBy(Planet anotherP) {
        double F = (G * mass * anotherP.mass) / Math.pow(this.calcDistance(anotherP),2);
        return F;
    }
    public double calcForceExertedByX(Planet anotherP) {
        double F = calcForceExertedBy(anotherP);
        double dx = anotherP.xxPos - xxPos;
        double d = calcDistance(anotherP);
        double Fx = F * (dx/d);
        return Fx;
    }
    public double calcForceExertedByY(Planet anotherP) {
        double F = calcForceExertedBy(anotherP);
        double dy = anotherP.yyPos - yyPos;
        double d = calcDistance(anotherP);
        double Fy = F * (dy/d);
        return Fy;
    }
    public double calcNetForceExertedByX(Planet[] others) {
        double Fx = 0.0;
        for(Planet s : others) {
            if (!this.equals(s)) {
                Fx += calcForceExertedByX(s);
            }
        }
        return  Fx;
    }
    public double calcNetForceExertedByY(Planet[] others) {
        double Fy = 0.0;
        for(Planet s : others) {
            if (!this.equals(s)) {
                Fy += calcForceExertedByY(s);
            }
        }
        return  Fy;
    }
    public void update(double dt, double fX, double fY) {
        double aX = fX / mass;
        double aY = fY / mass;
        xxVel = xxVel + dt * aX;
        yyVel = yyVel + dt * aY;
        xxPos = xxPos + dt * xxVel;
        yyPos = yyPos + dt * yyVel;
    }
    public void draw() {
        String source = "images/" + imgFileName;
        StdDraw.picture(xxPos, yyPos, source);
    }

}