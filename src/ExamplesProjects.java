import java.util.ArrayList;

import geometry.*;
import tester.*;

class FrogExamples{
    
    //data examples for the Frog class
    Frog frog1 = new Frog(new Posn(300, 189), 152);
    Frog frog2 = new Frog(new Posn(0, 189), 152);
    Frog frog3 = new Frog(new Posn(300, 0), 152);
    Frog frog4 = new Frog(new Posn(25, 160), 152);
    
    //data examples for the GameObjects class
    GameObjects truck1 = new GameObjects("trucka.png", new Posn (300, 189), "left", 5);
    GameObjects truck2 = new GameObjects("truckb.png", new Posn (0, 189), "left", 5);
    GameObjects log1 = new GameObjects("log.png", new Posn (300, 150), "left", 5);
    GameObjects log2 = new GameObjects("log.png", new Posn(25,160), "right", 2);
    GameObjects log3 = new GameObjects("log.png", new Posn(225,160), "right", 2);
    GameObjects log4 = new GameObjects("log.png", new Posn(425,160), "right", 2);
    
    // list of GameObjects for testing
    ArrayList<GameObjects> lane = new ArrayList<GameObjects>();
    ArrayList<ArrayList<GameObjects>> lanes = new ArrayList<ArrayList<GameObjects>>();
    
    // Frog Worlds
    FrogWorld fworld = new FrogWorld(this.frog3);
    FrogWorld fworld2 = new FrogWorld(this.frog4);
    
    
    /** populates the lane */
    void initGameObjects(){
        lane.add(log1);
        lane.add(log2);
        lane.add(log3);
        lane.add(log4);
        lane.add(truck1);
        
        lanes.add(lane);
     }
    
    /** reset data */
    void reset(){
        this.frog1 = new Frog(new Posn(300, 189), 152);
        this.frog2 = new Frog(new Posn(0, 189), 152);
        this.frog3 = new Frog(new Posn(300, 0), 152);
        this.frog4 = new Frog(new Posn(25, 160), 152);
        
        this.truck1 = new GameObjects("trucka.png", new Posn (300, 189), "left", 5);
        this.truck2 = new GameObjects("truckb.png", new Posn (0, 189), "left", 5);
        this.log1 = new GameObjects("log.png", new Posn (300, 150), "left", 5);
        this.log2 = new GameObjects("log.png", new Posn(25,160), "right", 2);
        this.log3 = new GameObjects("log.png", new Posn(225,160), "right", 2);
        this.log4 = new GameObjects("log.png", new Posn(425,160), "right", 2);
        
        this.fworld = new FrogWorld(this.frog3);
        this.fworld2 = new FrogWorld(this.frog4);
    }
    
    
    
    /** test the wall collision methods in the Frog class */
    void testCollision(Tester t){
        t.checkExpect(frog1.leftWallCollision(), false);
        t.checkExpect(frog1.rightWallCollision(), false);
        t.checkExpect(frog1.topWallCollision(), false);
        t.checkExpect(frog1.lowerWallCollision(), false);
        
        t.checkExpect(frog2.leftWallCollision(), true);
        t.checkExpect(frog2.rightWallCollision(), false);
        t.checkExpect(frog2.topWallCollision(), false);
        t.checkExpect(frog2.lowerWallCollision(), false);
        
        t.checkExpect(frog3.leftWallCollision(), false);
        t.checkExpect(frog3.rightWallCollision(), false);
        t.checkExpect(frog3.topWallCollision(), true);
        t.checkExpect(frog3.lowerWallCollision(), false);
        
        t.checkExpect(frog4.leftWallCollision(), true);
        t.checkExpect(frog4.rightWallCollision(), false);
        t.checkExpect(frog4.topWallCollision(), false);
        t.checkExpect(frog4.lowerWallCollision(), false);
     }
    
    /** test the GameObjects and Log collision methods in the Frog class */
    void testGoCollision(Tester t){
        t.checkExpect(frog1.GameObjectsCollision(truck1), true);
        t.checkExpect(frog2.GameObjectsCollision(truck1), false);
        t.checkExpect(frog3.GameObjectsCollision(truck1), false);
        
        t.checkExpect(frog1.GameObjectsCollision(truck2), false);
        t.checkExpect(frog2.GameObjectsCollision(truck2), true);
        t.checkExpect(frog3.GameObjectsCollision(truck2), false);
        
        t.checkExpect(frog4.GameObjectsCollision(truck1), false);
        t.checkExpect(frog4.GameObjectsCollision(truck2), false);
        t.checkExpect(frog4.GameObjectsCollision(truck1), false);
        
        t.checkExpect(frog1.logCollision(log1), false);
        t.checkExpect(frog2.logCollision(log1), false);
        t.checkExpect(frog3.logCollision(log1), false);
        t.checkExpect(frog4.logCollision(log1), false);
    }
    
    /** test the onLog method of the Frog class */
    void testOnLog(Tester t){
        t.checkExpect(frog1.onLog(lanes), false);
        t.checkExpect(frog2.onLog(lanes), false);
        t.checkExpect(frog3.onLog(lanes), false);
        t.checkExpect(frog4.onLog(lanes), false);
    }
    
    /** test the move method of the Frog and GameObjects class */
    void testMove(Tester t){
        
        // move the frog
        this.frog1.moveFrog("right");
        this.frog2.moveFrog("left");
        this.frog3.moveFrog("up");
        this.frog4.moveFrog("down");
        
        // move GameObjects
        this.truck1.move();
        this.truck2.move();
        this.log1.move();
        this.log2.move();
       
        // verify effects
        t.checkExpect(frog1.center.x == 300, false);
        t.checkExpect(frog1.center.x, 323);
        t.checkExpect(frog2.center.x == 0, true);
        t.checkExpect(frog2.center.x, 0);
        t.checkExpect(frog3.center.y == 0, true);
        t.checkExpect(frog3.center.y, 0);
        t.checkExpect(frog4.center.y == 160, false);
        t.checkExpect(frog4.center.y, 177);
        
        t.checkExpect(truck1.center.x == 300, false);
        t.checkExpect(truck1.center.x, 295);
        t.checkExpect(truck2.center.x == 0, false);
        t.checkExpect(truck2.center.x, -5);
        t.checkExpect(log1.center.x == 300, false);
        t.checkExpect(log1.center.x, 295);
        t.checkExpect(log2.center.x == 25, false);
        t.checkExpect(log2.center.x, 27);
        
        this.reset();
     }
    
    /** test the In River and Winning area method of Frog*/
    void testInRiverAndWinning(Tester t){
        t.checkExpect(frog1.frogInRiver(), false);
        t.checkExpect(frog2.frogInRiver(), false);
        t.checkExpect(frog4.frogInRiver(), true);
        
        t.checkExpect(frog1.frogWin(), false);
        t.checkExpect(frog2.frogWin(), false);
        t.checkExpect(frog3.frogWin(), true);
    }
    
    /** test the In River and Winning area method of Frog*/
    void testAccumulateWinners(Tester t){
        this.fworld.accumulateWinners();
        
        t.checkExpect(fworld.winners.get(0), this.frog3);
        
        this.reset();
    }
    
   
    /** test the FrogOnWater method of Frog*/
    void testFrogOnWater(Tester t){
        // Apply the method
        this.fworld2.frogOnWater();
        
        // Verify the effect
        t.checkExpect(fworld2.lives, 3);
        
        this.reset();
    }
    
    
    

    
    /** main: to run the game */
    public static void main(String[] argv){

        // run the tests - showing only the failed test results
        FrogExamples fe = new FrogExamples();
        Tester.runReport(fe, false, false);
       
        
        /** run the game*/
        FrogWorld w = new FrogWorld(new Frog(new Posn(300, 189), 152));
        w.bigBang(600, 400, 0.1);
    
       
    }
    
}