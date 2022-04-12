public class NBody{
	public static double readRadius(String s){
		In in=new In(s);
		int num=in.readInt();
		double radius=in.readDouble();
		return radius;
	}
	/**public static Planet[] readPlanets(String s){
		In in=new In(s);
		int num=in.readInt();
		double radius=in.readDouble();
		Planet []Planets=new Planet[num];
		int j=0;
		whlie(j<num){
			Planets[j]=new Planet(0,0,0,0,0,"a");
			Planets[j].xxPos=in.readDouble();
			Planets[j].yyPos=in.readDouble();
			Planets[j].xxVel=in.readDouble();
			Planets[j].yyVel=in.readDouble();
			Planets[j].mass=in.readDouble();
			Planets[j].imgFileName=in.readString();
            j=j+1;
		}
		return Planets;
	}*/
	public static Planet[] readPlanets(String s){
    In in = new In(s);
    int number = in.readInt();
    double radius = in.readDouble();
    Planet[] all = new Planet[number];
    int i = 0;
    while(i < number){
      double xP = in.readDouble();
      double yP = in.readDouble();
      double xV = in.readDouble();
      double yV = in.readDouble();
      double m = in.readDouble();
      String img = in.readString();
      all[i] = new Planet(xP, yP, xV, yV, m, img);
      i = i + 1;
    };
    return all;
    } 
	public static void main(String[] args){
		double T=Double.parseDouble(args[0]);
		double dt=Double.parseDouble(args[1]);
        String filename=args[2];
        double radius=readRadius(filename);
        Planet[] planets=readPlanets(filename);
        StdDraw.setScale(-radius, radius);
        /*StdDraw.picture(0,0,"images/starfield.jpg");
        StdDraw.show();
        for(int i=0;i<planets.length;i=i+1){
        	planets[i].draw();
        }**/
        StdDraw.enableDoubleBuffering();//防止图像卡顿
        for(double t=0;t<T;t=t+10){
        	double []xForces=new double[planets.length];
        	double []yForces=new double[planets.length];        	
        	for(int i = 0; i < planets.length; i += 1){
            xForces[i] = planets[i].calcNetForceExertedByX(planets);
            yForces[i] = planets[i].calcNetForceExertedByY(planets);
        	}
        	for(int i=0;i<planets.length;i=i+1){
        	planets[i].update(dt,xForces[i],yForces[i]);
            }
            StdDraw.clear();
        	StdDraw.picture(0,0,"images/starfield.jpg");
            for(int i=0;i<planets.length;i=i+1){
        	planets[i].draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
        }
        StdOut.printf("%d\n", planets.length);
StdOut.printf("%.2e\n", radius);
for (int i = 0; i < planets.length; i++) {
    StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                  planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                  planets[i].yyVel, planets[i].mass, planets[i].imgFileName);   
}


	}
}	 





































