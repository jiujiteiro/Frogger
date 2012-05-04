import java.awt.Color;
import java.awt.Rectangle;

import geometry.*;
import impsoundworld.*;
import tunes.*;
import java.util.*;



// Assignment 13 Final Project
// Eduardo Romeiro
// eromeiro
// Ben Lyaunzon
// lyaunzbe
// 18 April 2012



    
 
/***********************************************************
 *                      The Frog                           *
 *                                                         *
 ***********************************************************/ 

/** Class that represents a frog that moves around a game */
class Frog {
    
    Posn center;
    int radius;
    
    /** The direction of the Frog */
    String direction;
    
    
    // the image of the frog
    ImageMaker image = new ImageMaker("frog5.png");
    String frogimage = "frog5.png";
    
    /** The constructor 
     * 
     * @param center a Posn representing the center of the Frog
     * @param radius an int representing the radius of the Frog
     */
    Frog(Posn center, int radius) {
        this.center = center;
        this.radius = image.width;
    }
    
    
    /** produce the image of this frog at its current location and color */
    WorldImage FrogImage(){
        return new FromFileImage(this.center, this.frogimage);
    }
    
    
    /** Effect: move this frog 20 pixels in the direction given by the key
     * 
     * @param ke a String representing the user input key
    */
   public void moveFrog(String ke){
       if(!this.rightWallCollision()){
           if (ke.equals("right")){
               this.frogimage = "frog1.png";
               this.center.x = this.center.x + this.image.width;
           }
       }
       if(!this.leftWallCollision()){
           if (ke.equals("left")){
               this.frogimage = "frog3.png";
               this.center.x = this.center.x - this.image.width;
           }
       }
       if(!this.topWallCollision()){
           if (ke.equals("up")){
               this.frogimage = "frog5.png";
               this.center.y = this.center.y - (this.image.height+15);
           }
       }
       if(!this.lowerWallCollision()){
           if (ke.equals("down")){
               this.frogimage = "frog7.png";
               this.center.y = this.center.y + this.image.height;
           }
       }
   }
   
   /** does this Frog collide with the left wall 
    * 
    * @return boolean
    */
   boolean leftWallCollision(){
       int y = this.center.x - this.image.width;
       if(y - this.radius <= 0){
           return true;
       }
       return false;
   }
   
   
   /** does this Frog collide with the right wall 
    * 
    * @return boolean
    */
   boolean rightWallCollision(){
       int y =  this.center.x + this.image.width; 
       if(y + this.radius >=600){
           return true;
       }
       return false;
   }
   
   /** does this Frog collide with the top wall 
    * 
    * @return boolean
    */
   boolean topWallCollision(){
       int y = this.center.y - this.image.height;
       if(y <= 0){
           return true;
       }
       return false;
   }
   
   /** does this Frog collide with the lower wall 
    * 
    * @return boolean
    */
   boolean lowerWallCollision(){
       int y = this.center.y + this.image.height;
       if(y + this.radius >= 400){
           return true;
       }
       return false;
   }
   
   /** is the frog outside the given bounds
    * 
    * @param width an int representing the width bound
    * @param height an int representing the height bound
    * @return boolean
    */
   boolean outsideBounds(int width, int height) {
       return this.center.x < 0
       || this.center.x > width
       || this.center.y < 0 
       || this.center.y > height;  
   }
   
   /** does this frog collide with a GameObjects
    * 
    * @param g a GameObject
    * @return boolean
    */
   boolean GameObjectsCollision(GameObjects g){
       Rectangle r1 = new Rectangle(this.center.x, this.center.y, this.image.width, this.image.height);
       Rectangle r2 = new Rectangle(g.center.x, g.center.y, g.image.width, g.image.height);
       
       return r1.intersects(r2);
   }
   
  
   /** does this frog collide with a GameObjects(logs)
    * 
    * @param g a GameObject
    * @return boolean
    */
   boolean logCollision(GameObjects g){
       Rectangle r1 = new Rectangle(this.center.x, this.center.y, this.image.width, this.image.height);
       Rectangle r2 = new Rectangle(g.center.x, g.center.y, g.image.width, g.image.height);
       
       return r2.intersects(r1);
    
   } 
   
   
   /** Is this Frog on any of the GameObjects in the River
    * Effect: The Frog direction equals the GameObject direction
    * @param loglanes the list of all GameObjects in the river
    * @return boolean
    */
   boolean onLog(ArrayList<ArrayList<GameObjects>> loglanes){
	   for(ArrayList<GameObjects> a : loglanes){
		   for(GameObjects b : a){
			   if(this.logCollision(b)){
				   this.direction = b.direction;
				   return true;
			   }
		   }
	   }
	   return false;
	}
   
   /** Is this frog in the river area */
   boolean frogInRiver(){
       return this.center.y < 189 && this.center.y > 25;
   }
   
   /** Is this frog in the winning area */
   boolean frogWin(){
   return this.center.y < 25;
   }
}

/***********************************************************
 *                      Game Objects                       *
 *                                                         *
 ***********************************************************/ 


 /**  Class that represents game objects that move around World */
class GameObjects{
    
   
    String filename;
    Posn center;
    /** The direction of the GameObject */
    String direction;
    /** The speed of the GameObject */
    int speed;
    
    /** The image of the GameObject and its starting X position */
    ImageMaker image;
    int startXRight = 25;
    int startXLeft = 575;
    
    
    /** the constructor
     * 
     * @param filename The String name of the GameObject
     * @param center The Posn center of the GameObject 
     * @param direction The String representing direction
     * @param speed An int representing speed
     */
    GameObjects(String filename, Posn center,  String direction, int speed){
        this.center = center;
        this.filename = filename;
        this.direction = direction;
        this.speed = speed;
        this.image = new ImageMaker(filename);
    }
    
    /** produce the image of this game object at its current location
     * 
     * @return an image of the GameObject
     */
    
    WorldImage objectImage(){
        return new FromFileImage(this.center, this.filename);
    }
    
    /** Effect: Moves this GameObject */
    void move(){
        if(this.direction.equals("right")){
            if(this.center.x < 600 + this.image.width){
                this.center.x = this.center.x + speed; 
             }  else{
                this.center.x = startXRight;
             }
        }else{
            if(this.center.x > 0 - this.image.width){
                this.center.x = this.center.x - speed; 
            } else{
                this.center.x = startXLeft;
            }
        }
    }

} 



/***********************************************************
 *                      Game World                         *
 *                                                         *
 ***********************************************************/ 


/** class to represent a World of a frog */
 class FrogWorld extends World {
       
     /** the width of the World */
     int width = 600;
     /** the width of the World */
     int height = 400;
     /** the Frog in the World */
     Frog frog;
     /** the lives of the World */
     int lives;
     
     /** the MusicBox of the World */
     MusicBox m = new MusicBox();
     
     /** the length of each tune in the theme */
     int tunelength = 28 * 4;
     
     /** the tick count of the theme */
     int tickCount = 0;
   
     
     /** The constructor
      * 
      * @param frog the initial Frog
      */
     
     public FrogWorld(Frog frog) {
         super();
         this.frog = frog;
         this.lives = 3;
         
         // Effect: Adds the GameObjects to the World
         initGameObjects();
         
         // Effect: Adds the 
         m.initChannels();
     }
       
     
                   // OBJECTS OF THE WORLD
     
       // List of Frogs that have made it across the game
       ArrayList<Frog> winners = new ArrayList<Frog>();
       
       // The Lists of GameObjects found on the road of the game
       ArrayList<GameObjects> lane1 = new ArrayList<GameObjects>();
       ArrayList<GameObjects> lane2 = new ArrayList<GameObjects>();
       ArrayList<GameObjects> lane3 = new ArrayList<GameObjects>();
       ArrayList<ArrayList<GameObjects>> lanes = new ArrayList<ArrayList<GameObjects>>();
       
       // The Lists of GameObjects found on the river of the game
       ArrayList<GameObjects> lane1log = new ArrayList<GameObjects>();
       ArrayList<GameObjects> lane2log = new ArrayList<GameObjects>();
       ArrayList<GameObjects> lane3log = new ArrayList<GameObjects>();
       ArrayList<GameObjects> lane4log = new ArrayList<GameObjects>();
       ArrayList<GameObjects> lane5log = new ArrayList<GameObjects>();
       ArrayList<ArrayList<GameObjects>> loglanes = new ArrayList<ArrayList<GameObjects>>();
       
       
       /** The background image for this world */
       public WorldImage background = new FromFileImage(new Posn(300,200), "bg.png");
       
       /** the Array of ints representing the theme */ 
       public static int  froggertheme[] = {
           0,0,0,0,
           noteB,0,0,0,noteG,0,0,0,noteG,0,0,0,noteG,0,0,0,
           noteB,0,0,0,noteG,0,0,0,noteG,0,0,0,noteG,0,0,0,
           noteUpC,0,0,0,noteUpC,0,0,0,noteB,0,0,0,noteB,0,0,0,noteA,0,0,0,
           0,0,0,0,
           noteUpC,0,0,0,noteUpC,0,0,0,noteB,0,0,0,noteB,0,0,0,noteA,0,0,0,noteA,0,0,0,noteUpD,0,0,0,noteUpD,0,0,0,
           noteUpC,0,0,0,noteUpC,0,0,0,noteB,0,0,0,noteA,0,0,0,noteG,0,0,0
       };
       
        
       /**
        * Effect: Initializes all the GameObjects
        */
       public void initGameObjects(){
           lane1.add(new GameObjects("truckb.png", new Posn(25,220), "right", 5));
           lane1.add(new GameObjects("truckb.png", new Posn(225,220), "right", 5));
           lane1.add(new GameObjects("truckb.png", new Posn(425,220) , "right", 5));
           
           lane2.add(new GameObjects("trucka.png", new Posn(25,270), "left", 3));
           lane2.add(new GameObjects("trucka.png", new Posn(225,270), "left", 3));
           lane2.add(new GameObjects("trucka.png", new Posn(425,270), "left", 3));
           
           lane3.add(new GameObjects("car1b.png", new Posn(25,320), "right", 2));
           lane3.add(new GameObjects("car1b.png", new Posn(225,320), "right", 2));
           lane3.add(new GameObjects("car1b.png", new Posn(425,320), "right", 2));
           
           lane1log.add(new GameObjects("log.png", new Posn(25,160), "right", 2));
           lane1log.add(new GameObjects("log.png", new Posn(225,160), "right", 2));
           lane1log.add(new GameObjects("log.png", new Posn(425,160), "right", 2));
           
           lane2log.add(new GameObjects("lily.png", new Posn(25,130), "left", 2));
           lane2log.add(new GameObjects("lily.png", new Posn(225,130), "left", 2));
           lane2log.add(new GameObjects("lily.png", new Posn(425,130), "left", 2));
           
           lane3log.add(new GameObjects("log.png", new Posn(25,100), "right", 2));
           lane3log.add(new GameObjects("log.png", new Posn(225,100), "right", 2));
           lane3log.add(new GameObjects("log.png", new Posn(425,100), "right", 2));
           
           lane4log.add(new GameObjects("lily.png", new Posn(25,70), "left", 2));
           lane4log.add(new GameObjects("lily.png", new Posn(225,70), "left", 2));
           lane4log.add(new GameObjects("lily.png", new Posn(425,70), "left", 2));
           
           lane5log.add(new GameObjects("log.png", new Posn(25,40), "right", 2));
           lane5log.add(new GameObjects("log.png", new Posn(225,40), "right", 2));
           lane5log.add(new GameObjects("log.png", new Posn(425,40), "right", 2));
           
           
           lanes.add(lane1);
           lanes.add(lane2);
           lanes.add(lane3);
           
           loglanes.add(lane1log);
           loglanes.add(lane2log);
           loglanes.add(lane3log);
           loglanes.add(lane4log);
           loglanes.add(lane5log);
   
           
       }
       
       
       /** Effect: Move the Frog in response to user input
        * 
        * @param ke a String representing user input key
        */
       public void onKeyEvent(String ke) {
         if (ke.equals("q")){
           this.endOfWorld("Goodbye");
         }else{
           this.keyTunes.addNote(this.VIOLIN, noteC);
           this.frog.moveFrog(ke);
         }
       }
       
       /** Effect: Produces a new World very tick 
        *  Moves the Frogs
        *  Moves the GameObjects
        */
       public void onTick() {
         
           
          this.accumulateWinners();
          
          this.frogOnWater();
          
          this.moveObjectsRiver();
          
          this.moveObjectsRoad();
          
          
          this.tickTunes.addNote(CHOIR, froggertheme[this.tickCount]);
          this.tickCount = (this.tickCount + 1) % this.tunelength;
        }
       
       
       /** Accumulates winning frogs */
       void accumulateWinners(){
           if(this.frog.frogWin()){
               this.winners.add(this.frog);
               this.frog = new Frog(new Posn(300, 390), 152);
              }
       }
       
       
       /** Effect: If Frog touches water, reduces lives
        *  otherwise, Move frog with GameObject
        */
       void frogOnWater(){
           
           if(this.frog.frogInRiver()){
               // decrements lives if the Frog is touching the water
               if(!this.frog.onLog(loglanes)){
                this.frog.center = new Posn(300, 390);
                this.frog.frogimage = "frog5.png"; 
                this.lives--;
            }else{
                // moves the Frog with the moving GameObject
                if(this.frog.direction.equals("right"))
                    this.frog.center.x += 2;
                else
                    this.frog.center.x -= 2;
            }
           }
           
       }
       
       /**Effect: Moves the GameObjects on the river */
       void moveObjectsRiver(){
           for(ArrayList<GameObjects> a : loglanes){
               for(GameObjects b : a){
                  b.move();
               }
           }
       }
       
       /**Effect: Moves the GameObjects on the road */
       void moveObjectsRoad(){
           for(ArrayList<GameObjects> a : lanes){
               for(GameObjects b : a){         
                   if(this.frog.GameObjectsCollision(b)){       
                       this.tickTunes.addNote(TUBA, noteG);
                       this.frog.center = new Posn(300, 390);
                       this.frog.frogimage = "frog5.png"; 
                       this.lives--;
                   }
                   b.move();
               }
            }
       }
       
       
        /** Produce the image of this world
        *  Displays all the Frogs and GameObjects in the World
        *  @return gameImage the image of the World
        */
       public WorldImage makeImage(){
 
           /** the image of the World */
           WorldImage gameImage;
           gameImage = this.background;
           
           
           // Adds the GameObects on the road to the World
           for(ArrayList<GameObjects> a : lanes){
               for(GameObjects b : a){
                  gameImage = gameImage.overlayImages(b.objectImage());
                  }
              }
           
            // Adds the winning Frogs to the World
            for(Frog a : winners){
            	  gameImage = gameImage.overlayImages(a.FrogImage());	
              }
            // Adds the GameObects on the river to the World  
            for(ArrayList<GameObjects> a : loglanes){
                  for(GameObjects b : a){
                      gameImage = gameImage.overlayImages(b.objectImage());
                  }
              }
            
            // Adds the current Frog to the World
            gameImage = gameImage.overlayImages(this.frog.FrogImage());
              
          return gameImage;
       }
     
       
       /** Ends the World given the conditions
        * 
        *  @return WorldEnd the end of the World
        */
       public WorldEnd worldEnds(){
    	   if(winners.size() == 4){
    		   return new WorldEnd(true, this.lastImage("Congratulations! You Win."));
    	   }else if(this.lives < 1){
    		   return new WorldEnd(true, this.lastImage("Game Over."));
    	   }
    	   return new WorldEnd(false, this.makeImage());
       }
       
        
       /** The last image of the World to be displayed
        * 
        *  @param s A String to be displayed
        *  @return WorldImage an image of the end of the World
        */
       public WorldImage lastImage(String s){
    	   WorldImage x = new FrameImage(this.background.pinhole,600,400, Color.black);
    	   return x.overlayImages(new TextImage(x.pinhole,s, Color.white));
    	   } 
       
   }

       
      
   
   
   
    
    
    


