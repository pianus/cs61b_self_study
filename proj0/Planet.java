public class Planet {
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;

	public Planet(double xP, double yP, double xV, double yV, double m, String img) {
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

	public double calcDistance(Planet p) {
		double dx = Math.abs(this.xxPos - p.xxPos);
		double dy = Math.abs(this.yyPos - p.yyPos);
		return Math.sqrt(dx*dx + dy*dy);
	}

	public double calcForceExertedBy(Planet p) {
		double dist = this.calcDistance(p);
		double G = 6.67 * Math.pow(10, -11);
		return G * this.mass * p.mass / (dist * dist);
	}

	public double calcForceExertedByX(Planet p) {
		double dx = p.xxPos - this.xxPos;
		double F = this.calcForceExertedBy(p);
		double r = this.calcDistance(p);
		return F * dx / r;
	}

	public double calcForceExertedByY(Planet p) {
		double dy = p.yyPos - this.yyPos;
		double F = this.calcForceExertedBy(p);
		double r = this.calcDistance(p);
		return F * dy / r;
	}

	public double calcNetForceExertedByX(Planet[] p) {
		double NetX = 0;
		for (Planet i : p) {
			if (i == this) {
				continue;
			}
			NetX += this.calcForceExertedByX(i);
		}
		return NetX;
	}

	public double calcNetForceExertedByY(Planet[] p) {
		double NetY = 0;
		for (Planet i : p) {
			if (i == this) {
				continue;
			}
			NetY += this.calcForceExertedByY(i);
		}
		return NetY;
	}

	public void update(double dt, double fX, double fY) {
		double ax = fX / this.mass;
		double ay = fY / this.mass;
		xxVel = xxVel + dt * ax;
		yyVel = yyVel + dt * ay;
		xxPos = xxPos + dt * xxVel;
		yyPos = yyPos + dt * yyVel;
	}

	public void draw() {
		String imgLocation = "./images/" + imgFileName;
		StdDraw.picture(xxPos, yyPos, imgLocation);

	}
}