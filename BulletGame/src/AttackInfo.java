public abstract class AttackInfo {
	static Player player;
	static final int NUMBER_AVALIABLE_PROJS=6;//last case+1
		 
	public static Proj[] getPAttack(int which)
	{
		switch(which) {
        case 0: //Single projectile
        	return (new Proj[] {new Proj(0,0,3,0,0,0,0)});
        case 1: //3 splitting projectiles
        	return (new Proj[] {new Proj(0,0,3,0,0,0,0),//triple shot
        			new Proj(0,2,3,0,0,0,0),
        			new Proj(0,-2,3,0,0,0,0)});
        case 2:
        	return((new Proj[] {new Proj(1,0,3,0,0,-1,0),//Have curve be opposity sign as angle for inwards curve
        			new Proj(1,4,3,-.1,0,0,0),
        			new Proj(1,-4,3,.1,0,0,0)}));
        case 3: 
        	return((new Proj[] {new Proj(2,0,3,0,-40,80,-.1),//double vine
        			new Proj(2,0,3,0,40,80,-.1)}));
        case 4: 
        	return (new Proj[] {new Proj(3,0,3,0,0,100,1.5)}); //wave
        case 5: //5 splitting projectiles
        	return(new Proj[] {new Proj(1,0,3,0,0,-1,0),//crazy 5 prong thing
        			new Proj(1,3,3,-.1,0,0,0),
        			new Proj(1,-3,3,.1,0,0,0),
        			new Proj(1,5,3,-.08,0,0,0),
        			new Proj(1,-5,3,.08,0,0,0)});
		}
		return (new Proj[] {new Proj(0,0,3,0,0,0       ,-.1)});
	}
	
	public static Proj[] getEAttack(Enemy e, int which)
	{
		switch(which) {
        case 0: //Single projectile
        	return (new Proj[] {new Proj(e,2,0,0,-2,0,0,0,0)});//default single shot
        case 1:
        	return (new Proj[] {new Proj(e,2,0,0,-2,0,0,0,0),//five prong spread
        			new Proj(e,2,0,.6,-1.2,0,0,0,0),
        			new Proj(e,0,2,-.6,-1.2,0,0,0,0),//If it's slow, can change e,0,2 to e,0,0 and manually set up speeds.
        			new Proj(e,0,2,1,-.5,0,0,0,0),
        			new Proj(e,0,2,-1,-.5,0,0,0,0)});
        case 2:
        	return (new Proj[] {new Proj(e,0,2,.4,-1.5,0,0,0,0),//four prong spread
        			new Proj(e,0,2,-.4,-1.5,0,0,0,0),
        			new Proj(e,0,2,.8,-.7,0,0,0,0),
        			new Proj(e,0,2,-.8,-.7,0,0,0,0)});
        case 3:
        	return (new Proj[] {new Proj(e,0,2,1.5,-.4,0,0,0,0),//four prong spread
        			new Proj(e,0,2,-1.5,-.4,0,0,0,0),
        			new Proj(e,1,2,-2,.5,0,0,0,.1),
        			new Proj(e,1,2,2,.5,0,0,0,.1)});
        case 4:
        	return (new Proj[] {new Proj(e,0,2,.8,-1.5,0,0,0,0),//four prong spread
        			new Proj(e,0,2,-.8,-1.5,0,0,0,0),
        			new Proj(e,0,2,.8,-.7,0,0,0,0),
        			new Proj(e,0,2,-.8,-.7,0,0,0,0)});
        case 5:
        	return (new Proj[] {new Proj(e,104,2,2,0,0,0,0,0),//four prong spread
        			new Proj(e,104,2,-2,0,0,0,0,0)});
        case 6:
        	if((int)(Math.random()*3)==1)
        	{
        		return (new Proj[] {new Proj(e,0,2,0,-2,0,0,0,0)});
        	}
        	else
        	{
	        	double x3=1;
	        	double y3=1;
	        	if(e.getX()>DrawingPanel.player.getX())
	        	{x3=-1;}
	        	if(e.getY()<DrawingPanel.player.getY())
	        	{y3=-1;}
	        	return (new Proj[] {new Proj(e,0,2,x3,y3,0,0,0,0)});
        	}
        case 7:
        	double ang=(DrawingPanel.player.getY()-e.getY())/175*2;
        	return (new Proj[] {new Proj(e,0,ang,1,-1.5,-.01,0,0,0),
        			new Proj(e,0,ang,-1,-1.5,.01,0,0,0)});
        case 8: //Single projectile
        	if(e.getX()>DrawingPanel.player.getX())
        	{return (new Proj[] {new Proj(e,5,0,-1,0,0,0,0,0)});}
        	else
        		return (new Proj[] {new Proj(e,5,0,1,0,0,0,0,0)});
        	
        case 101:
        	if((int)(Math.random()*2)==1)
        	{
        		return (new Proj[] {new Proj(e,101,2,0,-.5,.01,0,1000,-.1)});
        	}
        	else			
        		return null;
        case 102:	
        	if((int)(Math.random()*2)==1)
        	{
        		return (new Proj[] {new Proj(e,102,2,0,-.5,.01,0,1000,-.1)});
        	}
        	else			
        		return null;
        case 103:	
        	return (new Proj[] {new Proj(e,103,2,0,-.5,.01,0,1000,-.1)});
        case 104:	
        	return (new Proj[] {new Proj(e,104,2,0,-.5,.01,0,1000,-.1)});
        case 105:	
        	if((int)(Math.random()*3)<=1)
        	{
        		return (new Proj[] {new Proj(e,105,2,0,-1.5,0,0,0,0)});
        	}
        	else			
        		return null;
		}
		
		return null;
	}
        	
	public static double getReloadTime(int which)
	{
		switch(which) {
		case 0: return 60;
		case 1: return 110;
		case 2: return 120;
		case 3: return 20;
		case 4: return 60;
		case 5: return 200;
		}
		return 10;
	}
	
    public static void setPlayer(Player p)
    {
        player=p;
    }
}
