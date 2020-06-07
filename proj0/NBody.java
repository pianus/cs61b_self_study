public class NBody {

	public static double readRadius(String f) {
		In in = new In(f);
		int numberOfPlanet = in.readInt();
		double universeRadius = in.readDouble();
		return universeRadius;
	}

	public static Planet[] readPlanets(String f) {
		In in = new In(f);
		int numberOfPlanet = in.readInt();
		double universeRadius = in.readDouble();

		
		Planet[] planets = new Planet[numberOfPlanet];
		int planetIndex = 0;

		while(planetIndex < numberOfPlanet) {
			double posX = in.readDouble();
			double posY = in.readDouble();
			double velX = in.readDouble();
			double velY = in.readDouble();
			double m = in.readDouble();
			String planetName = in.readString();

			planets[planetIndex] = new Planet(posX, posY, velX, velY, m, planetName);
			planetIndex += 1;
		}
		return planets;
	}

	public static void main(String[] args) {
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];
		Planet[] planets = readPlanets(filename);
		double universeRadius = readRadius(filename);

		StdDraw.setScale(-universeRadius, universeRadius);
		StdDraw.picture(0, 0, "./images/starfield.jpg");

		for (Planet p : planets) {
			p.draw();
		}
		 
		StdDraw.enableDoubleBuffering();
		StdAudio.play("./audio/2001.mid");
		//update the position and velocity of planets until time ends
		double time = 0;
		while (time < T) {
			double[] xForces = new double[planets.length];
			double[] yForces = new double[planets.length];

			for (int i = 0; i < planets.length; i++) {
				xForces[i] = planets[i].calcNetForceExertedByX(planets);
				yForces[i] = planets[i].calcNetForceExertedByY(planets);		
			}
			for (int i = 0; i < planets.length; i++) {
				planets[i].update(dt, xForces[i], yForces[i]);		
			}
			
			StdDraw.picture(0, 0, "./images/starfield.jpg");

			for (Planet p : planets) {
				p.draw();
			}

			StdDraw.show();
			StdDraw.pause(10);

			time += dt;
		}



		//print out the final position and velocity data for 
		StdOut.printf("%d\n", planets.length);
		StdOut.printf("%.2e\n", universeRadius);
		for (int i = 0; i < planets.length; i++) {
			StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
				planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
				planets[i].yyVel, planets[i].mass, planets[i].imgFileName);   
		}
	}
}