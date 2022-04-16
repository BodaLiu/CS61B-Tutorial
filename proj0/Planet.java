public class Planet{
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;
	private static double G = 6.67e-11;
    public Planet(double xP, double yP, double xV,double yV, double m, String img){
    	xxPos=xP;
    	yyPos=yP;
    	xxVel=xV;
    	yyVel=yV;
    	mass=m;
    	imgFileName=img;
    }
    public Planet(Planet p){
    xxPos = p.xxPos;
    yyPos = p.yyPos;
    xxVel = p.xxVel;
    yyVel = p.yyVel;
    mass = p.mass;
    imgFileName = p.imgFileName;
    }
    private boolean equals(Planet p){         //if the two planets are equal to each other 
    	return(this.xxPos==p.xxPos&&this.yyPos==p.yyPos);
    }
    public double calcDistance(Planet p){
    	double distance=Math.sqrt((xxPos-p.xxPos)*(xxPos-p.xxPos)+(yyPos-p.yyPos)*(yyPos-p.yyPos));
    	return distance;
    }
    public double calcForceExertedBy(Planet p){
    double force=G*mass*p.mass/(this.calcDistance(p)*this.calcDistance(p));
    return force;
    }
    public double calcForceExertedByX(Planet p){
    	double forceX=this.calcForceExertedBy(p)*(p.xxPos-this.xxPos)/this.calcDistance(p);
    	return forceX;
    }
    public double calcForceExertedByY(Planet p){
    	double forceY=this.calcForceExertedBy(p)*(p.yyPos-this.yyPos)/this.calcDistance(p);
    	return forceY;
    }
    public double calcNetForceExertedByX(Planet[] allPlanet){
    	double netForceX=0;
    	for(int i=0;i<allPlanet.length;i=i+1)
    	{
            if(this.equals(allPlanet[i])==false)
            {
               netForceX=netForceX+this.calcForceExertedByX(allPlanet[i]);
            }
    	}
    	return netForceX;

    }
    public double calcNetForceExertedByY(Planet[] allPlanet){
    	double netForceY=0;
    	for(int i=0;i<allPlanet.length;i=i+1)
    	{
            if(this.equals(allPlanet[i])==false)
            {
               netForceY=netForceY+this.calcForceExertedByY(allPlanet[i]);
            }
    	}
    	return netForceY;
    }
    public void update(double dt,double fX,double fY){
    	double ax=fX/mass;
    	double ay=fY/mass;
    	xxVel=xxVel+ax*dt;
    	yyVel=yyVel+ay*dt;
    	xxPos=xxPos+xxVel*dt;
    	yyPos=yyPos+yyVel*dt;
    }   //update the velocity and position
    public void draw(){
    	StdDraw.picture(xxPos,yyPos,"images/"+imgFileName);
    }

}






























