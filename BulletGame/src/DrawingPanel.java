import javax.swing.JPanel;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.util.ArrayList;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;

public class DrawingPanel extends JPanel
{
	private static final long serialVersionUID = 1L;//I don't know what this means
	private ArrayList<RectObj> obstructions;
	private ArrayList<Enemy> enemies;
    private ArrayList<Proj> playerProjs;
    private ArrayList<Proj> enemyProjs;
    private Graphic[] graphics;
    private Graphic[] tempGraphics;
    private Powerup[] powerups= {null,null};
    private Graphic[] selectableUI;
    private long coolUI=0;
    private int selected=0;
    private Boss boss=null;
    private int[] which= {0,0};
    private  Graphic[] background= new Graphic[2];
    private Graphic splashes=new Graphic(1,1);
    private long[] screenDark= {0,0};//0 is counter, 1 is fade time.
    private ArrayList<Integer> equipped=new ArrayList<Integer>(); 
    private boolean isShift=false;
    private int isLeft=0;
    private int isRight=0;
    private int isUp=0;
    private static boolean isShooting=false;
    private int isDown=0;
    private int currentLevel=0;//Change this to start elsewhere
    private long levelStart=0;
    public static Player player=new Player(new Point2D.Double(100,400));
    public int points=0;
    public static long tickCounter=0;
    public static int UPPER_BOUNDS=0;
    public static int LOWER_BOUNDS=800;
    public static int LEFT_BOUNDS=0;
    public static int RIGHT_BOUNDS=725;
    public static final int SCREEN_WIDTH=725;//?
    public static double WIND_EFFECT=.5;
    public boolean[] isPaused= {false,false};
    
        
    public DrawingPanel()
    {        
    	
        obstructions=new ArrayList<RectObj>();
        enemies=new ArrayList<Enemy>();
        playerProjs=new ArrayList<Proj>();
        enemyProjs=new ArrayList<Proj>();
        graphics=new Graphic[5];
        tempGraphics=new Graphic[0];
        setBackground(Color.WHITE);       
        //addMouseListener(new ClickListener());
        //addMouseMotionListener(new MovementListener());
        setFocusable(true);
        addKeyListener(new KeysListener());
        Proj.setPlayer(player);
        graphics[0]=new Graphic(0,0);
        graphics[1]=new Graphic(0,0);
        graphics[2]=new Graphic(0,0);
        graphics[3]=new Graphic(1,0);
        graphics[4]=new Graphic("Points: ",false);
        graphics[4].setPlace(0, LOWER_BOUNDS-125);
        graphics[4].setVisible(false);
        loadLevel();
        equipped.add(0);
        //equipped.add(2);
    }
    
    public Color getColor()
    {return Color.BLACK;}
    
    public Dimension getPreferredSize()
    {
        Dimension d=new Dimension(350,300);
        return d;
    }
    
    /**
     * Handles the 'ending level' animation.
     *
     * @pre     Player is touching the finish line.
     * @post    plays the animation and calls nextLevel()
       */
    public void finishLevel(boolean gameOver)
    {           
    	coolUI=0;
    	graphics[0].setVisible(false);
        graphics[1].setVisible(false);
        graphics[2].setVisible(false);
    	if(!gameOver) {
	        if(currentLevel>0)
	        {
		        try {
		            Thread.sleep(30);//I took this bit here from stackoverflow - just the pause stuff
		        } catch(InterruptedException ex) {
		            Thread.currentThread().interrupt();
		        }
		        tempGraphics=new Graphic[2];
		        tempGraphics[0]=new Graphic("yay u did it",true);
		        tempGraphics[1]=new Graphic("Current Score: "+tickCounter,false);
		        tempGraphics[0].setPlace(400,300);
		        tempGraphics[1].setPlace(0,200);
		        int frames=0;
		        while (frames<3)
		        {
		            frames++;
		            repaint();
		            try {
		                Thread.sleep(1000);                 //same
		            } catch(InterruptedException ex) {
		                Thread.currentThread().interrupt();
		            }
		        }        
		        //graphics.remove(graphics.length-1);
		        //graphics.remove(graphics.length-1);
		        
		        currentLevel++;
		        currentLevel*=-1;
		        player.goTo(400, 500);
		        loadLevel();
	        }
	        else if(currentLevel<-1)
	        {
	        	currentLevel*=-1;
	        	screenDark[0]=tickCounter+80;
		        screenDark[1]=80;
        		splashes=new Graphic(1,2);
        		tempGraphics=new Graphic[1];
        		tempGraphics[0]= new Graphic("Level "+currentLevel,false);
        		tempGraphics[0].setPlace(LEFT_BOUNDS+300, 300);
	        	repaint();
	        	try {
	                Thread.sleep(900);                 //same
	            } catch(InterruptedException ex) {
	                Thread.currentThread().interrupt();
	            }
		        loadLevel();
	        }
	        else
	        {
		        player.refresh();
		        tempGraphics=new Graphic[1];
		        if (currentLevel==0)
		        {
			        screenDark[0]=tickCounter+80;
			        screenDark[1]=80;
	        		splashes=new Graphic(1,2);		        	
	        		tempGraphics[0]= new Graphic("prepare","your...face",1,Color.WHITE);//TODO
	        		tempGraphics[0].setPlace(LEFT_BOUNDS+300, 300);
		        	int frames=0;
		        	repaint();
		        	while (frames<3)
			        {
			            frames++;
			            repaint();
			            try {
			                Thread.sleep(900);                 //same
			            } catch(InterruptedException ex) {
			                Thread.currentThread().interrupt();
			            }
			        }    
		        	//graphics.remove(graphics.length-1);
		        	
		        }
		        else {repaint();
		        System.out.println("Eyyo----------------------------");
		        try {
	                Thread.sleep(300);                 //same
	            } catch(InterruptedException ex) {
	                Thread.currentThread().interrupt();
	            }}
	        	currentLevel++;
		        player.goTo(400, 500);
		        background[0]=null;
		        background[1]=null;
		        loadLevel();
	        }
	        isPaused[0]=false;//Test if this should be below too
    	} else {
    		graphics[0].setVisible(false);
	        graphics[1].setVisible(false);
	        graphics[2].setVisible(false);
	        tempGraphics=new Graphic[2];
	        tempGraphics[0]=new Graphic("WELCOME","TO DIE",1,Color.BLACK);
	        tempGraphics[0].setPlace(300, 300);
	        tempGraphics[1]=new Graphic("score: "+tickCounter,false);
	        tempGraphics[1].setPlace(0,200);
	        
	        int frames=0;
        	repaint();
        	while (frames<3)
	        {
	            frames++;
	            repaint();
	            try {
	                Thread.sleep(900);                 //same
	            } catch(InterruptedException ex) {
	                Thread.currentThread().interrupt();
	            }
	        }    
	        //graphics.remove(graphics.length-1);
        	currentLevel=-1;
	        player.goTo(400, 500);
	        background[0]=null;
	        background[1]=null;
	        loadLevel();
    	}
    }
   
        /**
     * An example of a method - replace this comment with your own
     *  that describes the operation of the method
     *   
     * @post    draws all the onscreen objects and calculates new locations
     * @param  g Graphics object
     */
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(4));
        g2.draw(new Rectangle(LEFT_BOUNDS,UPPER_BOUNDS,RIGHT_BOUNDS,LOWER_BOUNDS));
        //System.out.println(background[0].getY());
        if(background[0]!=null)
        {
        	background[0].draw(g2);
 	        background[1].draw(g2); 
        }
        if(currentLevel==-1||currentLevel==0)
        {	       
        	drawGraphics(g2,false);
        }
        else if (currentLevel>0)
        {
	        if(powerups[0]!=null) powerups[0].draw(g2);
	        if(powerups[1]!=null) powerups[1].draw(g2);
	        for (int i=0;i<playerProjs.size();i++)
	        {   
	            if(playerProjs.get(i).isAlive()==false)
	            {
	                playerProjs.remove(i);
	            }
	            else
	            {
	                playerProjs.get(i).draw(g2);
	            }
	        }
	        for(int i=0;i<enemies.size();i++)
	        {            
	            if(enemies.get(i).isAlive())
	            {
	                enemies.get(i).draw(g2);
	            }
	            else
	            {
	            	points+=enemies.get(i).getPoints();
	            	enemies.remove(i);
	            	i--;
	            }
	        }
	        
	        for (RectObj entity:obstructions)
	        {entity.draw(g2);}
	        if (playerProjs.size()>100)
	        {
	            playerProjs.remove(0);
	        }
	        player.draw(g2);
	        if(boss!=null)
	        	boss.draw(g2);
	        for (int i=0;i<enemyProjs.size();i++)
	        {   
	            if(enemyProjs.get(i).isAlive()==false)
	            {
	                enemyProjs.remove(i);
	            }
	            else
	            {
	                enemyProjs.get(i).draw(g2);
	            }
	        }
	        drawGraphics(g2,true);
        }
        else
        {
        	drawGraphics(g2,false);
        }
        if(tickCounter<250)
        {
        	if(tickCounter>150)
        	{
        		Float alpha = 1f - ((float) (tickCounter-150) / (float) 100);
        		//System.out.println(alpha);
        		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            	splashes.draw(g2);
            	g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        	}
        	else
        	{
        		splashes.draw(g2);
        	}
        	splashes.setPlace(LEFT_BOUNDS,UPPER_BOUNDS,RIGHT_BOUNDS,LOWER_BOUNDS);

        	
        }
        if((isPaused[0]||isPaused[1])&&currentLevel>0) {
        	splashes.setPlace(LEFT_BOUNDS,UPPER_BOUNDS,RIGHT_BOUNDS,LOWER_BOUNDS);
        	splashes.draw(g2);
        }
    }
    
    private void drawGraphics(Graphics2D g2, boolean drawLvlElements)
    {
    	if(selectableUI!=null)
	    {
	    	for(int i=0;i<selectableUI.length;i++)
	    	{
	    		if(selected==i)
	    			selectableUI[i].draw(g2,1);
	    		else
	    			selectableUI[i].draw(g2);
	    	}
	    }
    	if(screenDark[0]>tickCounter)
		{
		       	g2.setColor(Color.BLACK);
		       	Float alpha = 1f - (((float) (screenDark[1]-(screenDark[0]-tickCounter)) / (float) screenDark[1]));
	    		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		        g2.fill(new Rectangle(LEFT_BOUNDS,UPPER_BOUNDS,RIGHT_BOUNDS,LOWER_BOUNDS));       
	        	g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		}
     	for (int i=0;i<graphics.length;i++)
	    {
	       	if(i==4)
	       	{
	       		if(drawLvlElements)
	       		{
	    			if(graphics[4].getNum()<points)
	    				graphics[4].setNum(graphics[4].getNum()+1);
	    			if(graphics[4].getNum()>points)
	    				graphics[4].setNum(points);
	    			graphics[4].drawWithNum(g2);
	       		}
	       	}
    		else
    			graphics[i].draw(g2);
	    }
     	for (int i=0;i<tempGraphics.length;i++)
	    {
     		if(tempGraphics[i]!=null)	
     			tempGraphics[i].draw(g2);
	    }
    }
    
    /**
     * Is called every frame to calculate collision and player interactions
     * @post    had detected and acted upon any collisions and stuff
     */
    public void nextFrame(Rectangle r)
    {
    	graphics[3].setPlace(1000+RIGHT_BOUNDS-214, LOWER_BOUNDS-252, 200, 216.4);
    	LOWER_BOUNDS=(int)r.getHeight();
        RIGHT_BOUNDS=(int)(r.getWidth());///2-(SCREEN_WIDTH/2));
    	
        //LEFT_BOUNDS=0;//(int)(r.getWidth());2-(SCREEN_WIDTH/2));
    	if(currentLevel==0||currentLevel==-1)
    	{
    		tickCounter++;
    		if(background[0]!=null)
    		{
    			background[0].calcXY();
    			background[1].calcXY();
    		}
            if(Math.abs(background[0].getY()-background[1].getY())<5)
            {
            	background[1].tileUp();
            }
    		if(selectableUI!=null)
        	{
        		if(coolUI<tickCounter) {
	        		if((isUp+isDown)>0)
				 	{
	        			coolUI=tickCounter+20;
				 		selected++;
				 		if(selected>selectableUI.length-1)
				 		{
				 			selected=0;
				 		}
				 	}
				 	else if ((isUp+isDown)<0)
				 	{
	        			coolUI=tickCounter+20;
				 		selected--;
				 		if(selected<0)
				 		{
				 			selected=selectableUI.length-1;
				 		}
				 	}
			 	}
        		if(isShooting)
        		{
        			if(selectableUI[selected].getActionID()==0)
        				System.exit(0);
        			else if(selectableUI[selected].getActionID()==2)
        			{
        				finishLevel(false);
        			}
        			else if(selectableUI[selected].getActionID()==1)
        			{
        				finishLevel(false);
        			}
        		}
        	}
    		levelProgress();
    	}
    	else if(currentLevel<-1)
    	{
    		tickCounter++;
       		if(coolUI<tickCounter) {
        		if((isUp+isDown)>0)
			 	{
        			coolUI=tickCounter+20;
			 		selected++;
			 		if(selected>selectableUI.length-1)
		 		{
		 			selected=0;
			 		}
			 	}
			 	else if ((isUp+isDown)<0)
			 	{
	       			coolUI=tickCounter+20;
			 		selected--;
			 		if(selected<0)
			 		{
			 			selected=selectableUI.length-1;
			 		}
			 	}
		 	}
       		if(isShooting)
       		{
       			if(points-selectableUI[selected].getNum()>0)
       			{
       				//if(selectableUI[selected].getActionID()==0)
       				selectableUI=null;
       				finishLevel(false);
       			}
       		}
    	}
    	else
    	{
    		if(!isPaused[0]&&!isPaused[1]) {
    			tickCounter++;
    	    	background[0].calcXY();
    	        background[1].calcXY();
    	        
    	        for (int i=0;i<2;i++)
    	        {
    	        	if(powerups[i]!=null)
    	        	{
    	        		powerups[i].calcXY();
    	        		if (equipped.size()<=AttackInfo.NUMBER_AVALIABLE_PROJS && player.isHit(powerups[i]))
    	        		{
    	        			boolean add=true;
    	        			for(Integer ingr:equipped)
    	        			{
    	        				if(ingr==powerups[i].getAttackPower())
    	        					powerups[i].recalculateType(powerups[i].getAttackPower());
    	        				if(ingr==powerups[i].getAttackPower())
    	        					add=false;
    	        			}
    	        			if(add)
    	        				equipped.add(powerups[i].getAttackPower());
    	        			powerups[i]=null;
    	        		}
    	        		else if(powerups[i].getY()>LOWER_BOUNDS+30)
		    	    	{
		    	    		powerups[i]=null;
		    	    	}
    	        	}
    	        }
    	        if(Math.abs(background[0].getY()-background[1].getY())<5)
    	        {
    	        	background[1].tileUp();
    	        }
		    	graphics[0].calcXY();
		    	graphics[1].calcXY();
		    	graphics[2].calcXY();
		    		if(boss!=null)
		    		{
		    			boss.calcXY();
		    			if(player.isHit(boss))
	                    {
	                        player.takeDamage(boss.getAttackPower());                                              
	                    }
		    		}
		    		if(boss!=null&&boss.getFireFrequency()!=0 && tickCounter % boss.getFireFrequency()==0)//Might be unnessesary
		        	{
		        		Proj[] projs=AttackInfo.getEAttack(boss, boss.getAttackType());
		        		if(projs!=null) {
			    		for (int i=0;i<projs.length;i++)
			    			enemyProjs.add(projs[i]);
		        		}
		        	}
		    		for (int i=0;i<playerProjs.size();i++)
		            {   
		                playerProjs.get(i).calcXY();
		                if(boss!=null&&boss.isTouching(playerProjs.get(i)))
		                {
		                    boss.getHit(playerProjs.get(i),false);
		                    playerProjs.get(i).hitEnemy(1);
		                }
		            }
		    		for(int i=0;i<enemies.size();i++)
		            {                            
		                if(enemies.get(i).isAlive())
		                {
		                    enemies.get(i).calcXY();
		                    if(player.isHit(enemies.get(i)))
		                    {
		                        player.takeDamage(enemies.get(i).getAttackPower());                                              
		                    }
		                	if(enemies.get(i).getFireFrequency()!=0 && tickCounter % enemies.get(i).getFireFrequency()==0)//Might be unnessesary
		                	{
		                		Proj[] projs=AttackInfo.getEAttack(enemies.get(i), enemies.get(i).getAttackType());
		        	    		for (int j=0;j<projs.length;j++)
		        	    			enemyProjs.add(projs[j]);
		                	}
		                    for (Proj p :playerProjs)
		                    {
		                        if(p.isTouching(enemies.get(i)))
		                        {
		                            enemies.get(i).getHit(p,false);
		                            if(!enemies.get(i).isAlive())
		                            {
		                        		makeExplosion(enemies.get(i),1);
		                        		makePowerup(enemies.get(i),3);
		                            }
		                            p.hitEnemy(1);
		                        }
		                    }
		                }      
		            }
		            for (RectObj entity:obstructions)
		            {            
		            	entity.calcXY();// if gets smooshed check here
		                if(player.isHitNextFrame(entity))
		                {
		                    player.hitWall(entity.getX(),entity.getY(),entity.getXL(),entity.getYL(),entity);
		                  
		                }
		                for (Proj p :playerProjs)
		                {
		                    if(entity.isInside(p.getX(), p.getY()))
		                    {
		                        //entity.getHit(p,false);   
		                        p.hitEnemy(0);
		                    }
		                }
		                for(Proj p:enemyProjs)
		                {
		                	if(entity.isInside(p.getX(), p.getY()))
		                    {
		                        //entity.getHit(p,false);   
		                        p.hitEnemy(0);
		                    }
		                }
		                boolean b=player.isOnTopOfNextFrame(entity);
		                int top=(int)(entity.getY()-entity.getYL());
		                player.whenTouchingGround(b,top);
		            }         
		            if(isShooting) {makeProjectile();}
		            player.calcMove(isShooting); 
		            player.moveX((isRight+isLeft),(isUp+isDown),isShift);
		            if(boss!=null&&!boss.isAlive())
		            {
		            	if(boss.die())
		            	{
		            		points+=boss.getPoints();
		            		makeExplosion(boss,1);
		            		boss=null;
		            	}
		            }
		            for (int i=0;i<enemyProjs.size();i++)
		            {   
			            if(enemyProjs.get(i).isTouching(player))
			            {
			                if (player.takeDamage(enemyProjs.get(i).getAttackPower()))
			                {enemyProjs.get(i).hitEnemy(1);}//takeDamage() return if attack lands.
			            }
		                enemyProjs.get(i).calcXY();
		            }
		            if(player.isDead())
		            { 
		            	if(coolUI<tickCounter)
		            	{
		            		coolUI=tickCounter+1000;
		            		makeExplosion(player,5);
		            	}
		                if(graphics[0].isAvailable()&&graphics[1].isAvailable()&&graphics[2].isAvailable())
		                {
		                	equipped=new ArrayList<Integer>();
		                	equipped.add(1);
		                	finishLevel(true);
		                }
		            }
		            levelProgress();
	    		}
	    	}
    	
		    repaint();
		    requestFocusInWindow();    
        }
   
    
        /**
     * Makes projectiles and handles which to make.
     *
     */
     public void makeProjectile()
    {
    	if(!player.isDead())
    	{
    		for (int j=0;j<equipped.size();j++)
    		{
    			if (tickCounter%AttackInfo.getReloadTime(equipped.get(j))==0)
    			{
    				Proj[] projs=AttackInfo.getPAttack(equipped.get(j));
    				for (int i=0;i<projs.length;i++)
    					playerProjs.add(projs[i]);
    			}
    		}
    	}

    }
    
     public <Type extends Collidable> void makeExplosion(Type obj, double sizeMultiplier)//if issues with explosion size, look here
     {
    	graphics[which[0]].setVisible(true);
    	graphics[which[0]].setPlace(obj.getX()- sizeMultiplier*(obj.getWidth()), obj.getY()- sizeMultiplier*(obj.getWidth()),
    			sizeMultiplier*(obj.getWidth()*2.3), sizeMultiplier*(obj.getWidth()*2.3));
    	//Decide if want all square
     	which[0]++;
     	if(which[0]>2)
     		which[0]=0;
     }
     public <Type extends Collidable> void makePowerup(Type obj, double freq)
     {
    	 if(powerups[which[1]]==null)
    	 {
			 if((int)(Math.random()*freq)==0)
				 powerups[which[1]]=new Powerup(obj);
		 	which[1]++;
		 	if(which[1]>1)
		 		which[1]=0;
    	 }
     }
    /**
     * Loads a level.
     *
     * @post    all the objects in a level have been initalized to their proper places.
     * @param   which    which type of level should be loaded
     */
    public void loadLevel()
    {
    	levelStart=tickCounter;
        obstructions=new ArrayList<RectObj>();
        enemies=new ArrayList<Enemy>();
        playerProjs=new ArrayList<Proj>();
        enemyProjs=new ArrayList<Proj>();
        boss=null;
        powerups[0]=null;
        powerups[1]=null;
        selectableUI=null;
        background[0]=null;
        background[1]=null;
       	System.out.println(currentLevel);
        if(currentLevel==-1)
        {
        	selectableUI=new Graphic[2];
        	selectableUI[0]=new Graphic(0,"return to title screen",0,2);
        	selectableUI[0].setPlace(LEFT_BOUNDS+300, 400);
        	selectableUI[1]=new Graphic(0,"exit game",0,0 );
        	selectableUI[1].setPlace(LEFT_BOUNDS+300, 500);
        	background[0]=new Graphic(2,0);
        	background[1]=new Graphic(2,0);
        } else if(currentLevel==0) {
        	tempGraphics=new Graphic[1];
        	tempGraphics[0]=new Graphic("hey it's","my game",0,Color.BLACK);
        	tempGraphics[0].setPlace(LEFT_BOUNDS+300, 300);
        	selectableUI=new Graphic[2];
        	selectableUI[0]=new Graphic(0,"start",0,1);
        	selectableUI[0].setPlace(LEFT_BOUNDS+300, 400);
        	selectableUI[1]=new Graphic(0,"exit",0,0);
        	selectableUI[1].setPlace(LEFT_BOUNDS+300, 500);
        	background[0]=new Graphic(2,0);
        	background[1]=new Graphic(2,0);
        }	else if (currentLevel==1) {
        	tempGraphics=new Graphic[0];
        	background[0]=new Graphic(2,1);
        	background[1]=new Graphic(2,1);
        }   else if (currentLevel==2) {
        	tempGraphics=new Graphic[0];
        	background[0]=new Graphic(2,2);
        	background[1]=new Graphic(2,2);
        	obstructions.add(new RectObj(new Point2D.Double(500, 200),70,70,Color.BLACK,0)); 
        	enemies.add(new MatedEnemy(randomColor(),obstructions.get(0),30,.01));
        }   else if (currentLevel==3) {
        	tempGraphics=new Graphic[0];
        	background[0]=new Graphic(2,2);
        	background[1]=new Graphic(2,2);
        	enemies.add(new MobileEnemy(new Point2D.Double(200, -50),30,1));
        	//enemies.add(new MobileEnemy(new Point2D.Double(200, -100),150,3));	
        }   else if(currentLevel<-1) {
        	selectableUI=new Graphic[4];
        	tempGraphics=new Graphic[5];
        	tempGraphics[0]=new Graphic("Select upgrade.",false);
        	tempGraphics[0].setPlace(LEFT_BOUNDS+200, 75);
        	
           	selectableUI[3]=new Graphic(0,"Point cost: 500",0,3);
        	selectableUI[3].setPlace(LEFT_BOUNDS+50, 200);
        	selectableUI[3].setNum(500);
        	tempGraphics[1]=new Graphic("Half primary weapon fire rate",false);
        	tempGraphics[1].setPlace(LEFT_BOUNDS+300, 200);
        	
        	selectableUI[2]=new Graphic(0,"Point cost: 750",0,4);
        	selectableUI[2].setPlace(LEFT_BOUNDS+50, 300);
        	selectableUI[2].setNum(750);
        	tempGraphics[2]=new Graphic("Set secondary to tri-shot",false);
        	tempGraphics[2].setPlace(LEFT_BOUNDS+300, 300);
        	
        	selectableUI[1]=new Graphic(0,"Point cost: 1000",0,5);
        	selectableUI[1].setPlace(LEFT_BOUNDS+50, 400);
        	selectableUI[1].setNum(1000);
        	tempGraphics[3]=new Graphic("Set primary to curved shot",false);
        	tempGraphics[3].setPlace(LEFT_BOUNDS+300, 400);
        	
        	selectableUI[0]=new Graphic(0,"Point cost: 1500",0,6);
        	selectableUI[0].setPlace(LEFT_BOUNDS+50, 500);
        	selectableUI[0].setNum(1000);
        	tempGraphics[4]=new Graphic("Set primary to wave shot",false);
        	tempGraphics[4].setPlace(LEFT_BOUNDS+300, 500);
        }
        if(background[0]!=null)
        {
        	background[0].setPlace(0,0);
        	background[1].setPlace(0,0);
        }
        selected=0;//selectableUI.length-1;
    }
    public void levelProgress()
    {
    	switch(currentLevel) {
    	case 0:
    		if((int)(Math.random()*300)==0)
    		{
    			graphics[which[0]].setVisible(true);
    			graphics[which[0]].setPlace(LEFT_BOUNDS+(Math.random()*(SCREEN_WIDTH-30)), Math.random()*(LOWER_BOUNDS-30), 100, 100);
    	     	which[0]++;
    	     	if(which[0]>2)
    	     		which[0]=0;
    		}
    		break;
    	case 1:
    		if(tickCounter-levelStart>4200&&enemies.size()==0)
    		{
    			finishLevel(false);
    		}
    		else if(levelStart+1400==tickCounter)
    		{
    			enemies.add(new MobileEnemy(new Point2D.Double((Math.random()*(LEFT_BOUNDS-RIGHT_BOUNDS))+RIGHT_BOUNDS, 0),30,2));
    		}
    		else if(levelStart+600==tickCounter)
    		{
    			enemies.add(new MobileEnemy(new Point2D.Double((Math.random()*(LEFT_BOUNDS-RIGHT_BOUNDS))+RIGHT_BOUNDS, -50),60,0));
    		}
    		else if(levelStart+400==tickCounter)
    		{
    			boss=new Boss(new Point2D.Double(100,100),100,0);
    		}
    		else if(levelStart+3200<tickCounter&&levelStart+3600>tickCounter&&(tickCounter-levelStart)%100==0)
    		{
    			enemies.add(new MobileEnemy(new Point2D.Double((Math.random()*(LEFT_BOUNDS-RIGHT_BOUNDS))+RIGHT_BOUNDS, -50),60,0));
    		}
    		else if(levelStart+3600==tickCounter)
    		{
    			enemies.add(new MobileEnemy(new Point2D.Double(((int)(Math.random()*2))*RIGHT_BOUNDS, -100),120,0));
    		}
    		else if((tickCounter+1-levelStart)%400==0)
    		{
    			enemies.add(new MobileEnemy(new Point2D.Double((Math.random()*(LEFT_BOUNDS-RIGHT_BOUNDS))+RIGHT_BOUNDS, -50),30,1));
    		}
    		
    		break;
    	case 2:
    		if(levelStart+300==tickCounter)
    		{
    			boss=new Boss(new Point2D.Double(100,100),100,2);
    		}
    		else if(boss==null&&tickCounter>=levelStart+1000)
    		{
    			//System.out.println(levelStart);
    			finishLevel(false);
    		}
    		break;
    	case 3:
       		if(levelStart+200<tickCounter&&levelStart+3600>tickCounter&&(tickCounter-levelStart)%100==0)
    		{
    			enemies.add(new MobileEnemy(new Point2D.Double((Math.random()*(LEFT_BOUNDS-RIGHT_BOUNDS))+RIGHT_BOUNDS, -50),30,0));
    		}
    		break;
    	}
    }
    public Color randomColor()
    {
        return new Color((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255));
    }
    public class KeysListener implements KeyListener
    { 
        public void keyPressed(KeyEvent e)
        {              
            if (e.getKeyCode()==KeyEvent.VK_SPACE||e.getKeyCode()==KeyEvent.VK_ENTER)
            {
            	isShooting=true;
            }
            else if (e.getKeyCode()==16)
            {
                if(isShift==false)
                {
                    //System.out.println(tickCounter);
                }
                isShift=true;
            }
            else if (e.getKeyCode()==KeyEvent.VK_A)
            {
                
                 isLeft=-1;
            }
            else if (e.getKeyCode()==KeyEvent.VK_D)
            {
                 isRight=1;
            }            
            else if (e.getKeyCode()==KeyEvent.VK_S)
            {
                 isDown=-1;
            }       
            else if (e.getKeyCode()==KeyEvent.VK_W)
            {
            	isUp=1;
            }
            else if (e.getKeyCode()==KeyEvent.VK_ESCAPE)
            {
            	isPaused[1]=true;
            }
            //repaint();
            requestFocusInWindow();           
        }
        public void keyReleased(KeyEvent e)
        {
            if (e.getKeyCode()==16)
            {
                isShift=false;
            }
            else if (e.getKeyCode()==KeyEvent.VK_A)
            {
                 isLeft=0;
            }
            else if (e.getKeyCode()==KeyEvent.VK_D)
            {
                 isRight=0;
            }          
            else if (e.getKeyCode()==KeyEvent.VK_SPACE||e.getKeyCode()==KeyEvent.VK_ENTER)
            {
                isShooting=false;
            }
            else if (e.getKeyCode()==KeyEvent.VK_S)
            {
                 isDown=0;
            }
            else if (e.getKeyCode()==KeyEvent.VK_W)
            {
            	 isUp=0;
            }
            else if (e.getKeyCode()==KeyEvent.VK_RIGHT)
            {
            	 equipped.set(0,equipped.get(0)+1);
            }
            else if (e.getKeyCode()==KeyEvent.VK_ESCAPE)
            {
            	if(isPaused[0]==true)
            	{
            		isPaused[0]=false;
            	}
            	else
            	{
            		isPaused[0]=true;
            	}
            	isPaused[1]=false;
            }
        }
        public void keyTyped(KeyEvent e)
        {
        	
        }
    }
}
