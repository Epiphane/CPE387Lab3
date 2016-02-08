import greenfoot.*; 

import java.awt.Color;
/**
 * player for my custom made platform game
 * 
 * @author softwhizjx
 * @version 1.0
 */
public class player extends Actor
{
    /**
     * Player's Movements Variables
     */
    
    /** main world's variable */
    private static PlatformWorld platformer;
    
    /**player's positioning*/
    float x = 160;
    float y = 100;
    /**set to 160 or 100 is for in the screen at first anywhere, when setLevel() runs
       these numbers are not much of an effect*/
    
    /**player's movement speed*/
    float speedX = 0;
    float speedY = 0;
    
    /** MAX speed horizonally */
    float maxSpeed = 3;  
    
    /** check can jump or not */
    boolean canJump = false;
    
    /** Offsets of the main sprite images
     * (uses for prevent the player looks like passing the floor or wall while touching the mask)
     * [offset = distance how far from the very center of the image]
     */
    int X_offset = 10;
    int Y_offset = 27;
    
    /** screen length*/
    static float minX = 14;
    static float maxX = platformer.sWidth-14;
    static float minY = 20;
    static float maxY = platformer.sHeight-20;
    
    /**
     * Player's Life Variables
     */
    
    /** check still alive or not */
    static boolean dead = false;
    boolean fall = false;
    
    /** health value, if it's 0, die*/
    int HP = 3;
    int maxHP = 3;
    
    /**
     * Player's Sprites Variables
     */
    
    /** array of sprite images*/
    static GreenfootImage[] frames = new GreenfootImage[24];
    
    float current_frame = 0; //current image value (from the number of arrays "[i]")
    float anim_speed = 0.1f; //how fast the image change
    float start_frame = 0; //first image of the animation
    float end_frame = 0; //last image of the animation
    int anim_no = 0; //the number of each animations
    int dir = 0; //value for mirrored sprites (half of the total images)
    int anim_delay = 0;
    int hurt_delay = 0;
    
    /**
     *initialize sprites
     */
    public player(){
        if(frames[0] == null){ //put this on so we dont reload images every time we make a player
            /**load basic right facing frames*/
            frames[0] = new GreenfootImage("player_stand.gif");
            frames[1] = new GreenfootImage("player_walking_a.gif");
            frames[2] = new GreenfootImage("player_walking_b.gif");
            frames[3] = new GreenfootImage("player_walking_c.gif");
            frames[4] = new GreenfootImage("player_walking_d.gif");
            frames[5] = new GreenfootImage("player_walking_e.gif");
            frames[6] = new GreenfootImage("player_walking_f.gif");
            frames[7] = new GreenfootImage("player_jump_a.gif");
            frames[8] = new GreenfootImage("player_jump_b.gif");
            frames[9] = new GreenfootImage("player_jump_c.gif");
            frames[10] = new GreenfootImage("player_jump_d.gif");
            frames[11] = new GreenfootImage("player_land.gif");
        
        
            /**load basic right facing frames then mirror them into left facing frames*/
            frames[0+12] = new GreenfootImage("player_stand_dir.gif");
            frames[1+12] = new GreenfootImage("player_walking_a_dir.gif");
            frames[2+12] = new GreenfootImage("player_walking_b_dir.gif");
            frames[3+12] = new GreenfootImage("player_walking_c_dir.gif");
            frames[4+12] = new GreenfootImage("player_walking_d_dir.gif");
            frames[5+12] = new GreenfootImage("player_walking_e_dir.gif");
            frames[6+12] = new GreenfootImage("player_walking_f_dir.gif");
            frames[7+12] = new GreenfootImage("player_jump_a_dir.gif");
            frames[8+12] = new GreenfootImage("player_jump_b_dir.gif");
            frames[9+12] = new GreenfootImage("player_jump_c_dir.gif");
            frames[10+12] = new GreenfootImage("player_jump_d_dir.gif");
            frames[11+12] = new GreenfootImage("player_land_dir.gif");
        
            /** mirror them up!!*/
            /*
             * for (int i = 1; i < 2; i++){
             *     frames[i].mirrorHorizontally();
             *  }
                */
               /**but the .mirrorHorizontally made the image more red, so I canceled this loop*/
       
               /**Initialize*/
               setAnim_stand();
            }
    }
    
    /**
     * set player's animation functional
     */
    private void animate(){
        /**sprite frame delay*/
        if (anim_delay > 0) {
            anim_delay--;
        }
        if (hurt_delay > 0) {
            hurt_delay--;
        }
        
        /**sprite frame loop*/
        current_frame += anim_speed;
        if (current_frame > end_frame){
            current_frame = start_frame;
        }
        
        /**set a new image*/
        this.setImage(frames[(int)current_frame + dir]);
    }
    
    /**
     * Anim no.0 --Standing
     */
    private void setAnim_stand(){
        if (anim_no == 0||canJump == false||anim_delay > 0){
            return; //cancel if using is animation, or still in the air
        }
        anim_no = 0; //this is animation no.0
        
        anim_speed = 0.05f; //how fast the image change
        current_frame = 1; //current image shot
        start_frame = 1; //first image of the animation
        end_frame = 6; //last image of the animation
    }
    
    /**
     * Anim no.1 --Running
     */
    private void setAnim_run(){
        if (anim_no == 1||canJump == false||anim_delay > 0){
            return; //cancel if using is animation, or still in the air, or animation delaying
        }
        anim_no = 1; //this is animation no.1
        
        anim_speed = 0.2f; //how fast the image change
        current_frame = 1; //current image shot
        start_frame = 1; //first image of the animation
        end_frame = 6; //last image of the animation
    }
    
    /**
     * Anim no.2 --Jumping
     */
    private void setAnim_jump(){
        if (anim_no == 2||anim_delay > 0){
            return; //cancel if using is animation, or animation delaying
        }
        anim_no = 2; //this is animation no.2
        
        anim_speed = 1; //how fast the image change
        current_frame = 8; //current image shot
        start_frame = 8; //first image of the animation
        end_frame = 8; //last image of the animation
    }
    
    /**
     * Anim no.3 --Falling
     */
    private void setAnim_fall(){
        if (anim_no == 3||canJump == true||anim_delay > 0){
            return; //cancel if using is animation,or in the land, or animation delaying
        }
        anim_no = 3; //this is animation no.3
        
        anim_speed = 0; //how fast the image change
        current_frame = 10; //current image shot
        start_frame = 10; //first image of the animation
        end_frame = 10; //last image of the animation
    }
    
    /**
     * Anim no.4 --Landing
     */
    private void setAnim_land(){
        if (anim_no == 4||canJump == false||anim_delay > 0){
            return; //cancel if using is animation,or in the air, or animation delaying
        }
        anim_no = 4; //this is animation no.4
        
        anim_speed = 0; //how fast the image change
        current_frame = 11; //current image shot
        start_frame = 11; //first image of the animation
        end_frame = 11; //last image of the animation
    }
    
    /**
     * Anim no.5 --Taking Damage
     */
    private void setAnim_hurt(){
        if (anim_no == 5||anim_delay > 0){
            return;
        }
        anim_no = 5; //this is animation no.5
        
        anim_speed = 0; //how fast the image change
        current_frame = 9; //current image shot
        start_frame = 9; //first image of the animation
        end_frame = 9; //last image of the animation
    }
    
    /**
     * set coordinate from the world (if not using this, the player disappear.)
     */
    public void addedToWorld(World world) {
        platformer = (PlatformWorld) world;
        x = getX();
        y = getY();
    }
    
  
    /**
     * Gears up the player!
     */
    public void act() 
    {
        /**move coding*/
        moveControl();
       
        /**auto detect masks*/
        maskTouch();
 
        /**animate the player, make them more alive!*/
        animate();
        
        /**animation delay trigger*/
        anim_delay--;
        
        /**enable jumping attack, stomp on enemies to do damage*/
        jumpAttack();
        
        /**disallow HP go over maxHP*/
        if (HP > maxHP){
            HP = maxHP;
        }
        
        /**enable taking damage*/
        getHurt();
        
        /**death case*/
        if (HP <= 0 && !fall){
            dead = true;
            platformer.loseLife();
            platformer.addObject(new player_death(), getX(), getY());
            platformer.removeObject(this);
        }
        /**disappear and die if fall of the screen*/
        if (HP <= 0 && fall){
            dead = true;
            platformer.loseLife();
            platformer.removeObject(this);
        }
    }    
    
    /** 
     * Control to walk around the world.
     */
    private void moveControl(){
        /**core of the movement function*/
        setLocation((int)x,(int)y-4);
        
        /**left key trigger*/
        if(Greenfoot.isKeyDown("left") && speedX > -maxSpeed && anim_delay <= 0 && !Greenfoot.isKeyDown("down")){
             speedX -= 0.75;
             //set animation into left facing view
             setAnim_run();
             if (anim_delay <= 0){
                 dir = 12; //[i+dir] in the mirror images
                }
          }
        /**right key trigger*/
        if(Greenfoot.isKeyDown("right") && speedX < maxSpeed && anim_delay <= 0 &&!Greenfoot.isKeyDown("down")){
            speedX += 0.75;
            //set animation into right facing view
            setAnim_run();
             if (anim_delay <= 0){
                 dir = 0;
                }
         }
         /**set the stand still animation while stopping x axis movement*/
        if (Math.abs(speedX) < 0.3) {
            setAnim_stand();
        }
                
        /** nullifies speed horizontally */
        speedX *= 0.8;
        
        /** speed changer*/
        x += speedX;
        y += speedY;
        
        /**jump up key trigger*/
        if (Greenfoot.isKeyDown("space") && canJump == true && anim_delay <= 0){
            /**speedY goes negative (up) and disable jump to prevent multi-jump*/
            speedY = -9;
            canJump = false;
            //set jumping animation
            setAnim_jump();
        }
        
        /**duck key trigger*/
        if (Greenfoot.isKeyDown("down") && canJump == true && anim_delay <= 0){
            setAnim_land();
            if (Greenfoot.isKeyDown("left")){dir = 12;} //face left
            if (Greenfoot.isKeyDown("right")){dir = 0;} //face right
        }
        
        /**set when falling, also disable jump in case of falling without jumping*/
        if (speedY > 2 ) {
            /*in some games, it still can jump,
             * but in this case, the animation will glitched if changed*/
            canJump = false;
            //set falling animation
            setAnim_fall();
        }
        
        /** jumping speed, includes gravity*/
        if (speedY < 5) {
            speedY += 0.3;//gravity
        } 
        
         /**prevent going further than the top of the screen*/
        if (y < minY) {
            y = minY;
            speedY = 0;
        }
        
        /**prevent going further than the screen in X axis*/
        if (x > maxX) {
             x = maxX; 
        }
        if (x < minX) {
            x = minX;
        }
        
        /**fall into the bottom of the screen to instantly die*/
        if (y > maxY){
            HP = 0;
            fall = true;
        }
        
        
        /**send the info of player's current position to the world (get famous in a spot)*/
        platformer.setPlayerCoords(getX(),getY());  
       
    }
    
    /**
     * events when player detect masks
     */
    private void maskTouch(){
        /**
         * BLACK MASK
         */
        
        /**landing strike to the ground in a positive Y-offset diatance plus a few more*/
      
        if (platformer.isSolid(getX(), getY()+(Y_offset+2) )){
            /**nullifies falling speed, (landing) allow player to jump again*/
            canJump = true;
            
            if (!Greenfoot.isKeyDown("space")){
                
              speedY = 0;
           }
        }
      
        /**when stomping into the black ground mask in a positive Y-offset diatance,
         * prevent going further downwards*/
        if (platformer.isSolid(getX(), getY()+Y_offset)){  
            /**y coorninate go upwards to prevent going downwards*/
            y -= 1; 
            if (speedY > 0){
               setAnim_land(); //this can have a sprite problem on slopes
            }
        } 
        
        /**
         * IMPORTANT NOTE!!!!
         * the "if" condition at the top should always has Y-offset's value
         * more than the bottom one.
         * Or else the player will shake on the floor.
         * 
         * Find the Y-offset value that fit's the player sprite's height the most!!
         * 
         * Hope any readers can get this.
         */
        
        /**when head butted into the black ceiling in a negative Y-offset diatance,
         * prevent going further upwards*/
         if (platformer.isSolid(getX(), getY()-Y_offset) ){
            /**y coorninate go downwards to prevent going upwards*/
            y += 9; 
            if (speedY < 0){
                /**if speed is currently going up, nullifies this speed*/
                speedY = 0;
            }
        }
        
        /**when running into the black right wall mask in a positive X-offset diatance,
         * prevent going further rightwards*/
        if (platformer.isSolid(getX()+X_offset, getY()) ){ 
            /**x coorninate go leftwards to prevent going rightwards*/
           x -= 2;
          
          if (speedX > 0) {
              /**if speed is currently going right, nullifies this speed*/
              speedX = 0;
            }
        } 
        
        /**when running into the black left wall mask for a negative X-offset diatance,
         * prevent going further leftwards*/
         if (platformer.isSolid(getX()-X_offset, getY()) ){ 
            /**x coorninate go rightwards to prevent going leftwards*/
            x += 2;

          if (speedX < 0) {
              /**if speed is currently going left, nullifies this speed*/
              speedX = 0;
            }
        } 
        
        /**
         * RED MASK
         */
        
        if (platformer.isDeath(getX(), getY()+Y_offset)){  
            killPlayer();
        } 
        
         if (platformer.isDeath(getX(), getY()-Y_offset) ){
            killPlayer();
        }
        
        if (platformer.isDeath(getX()+X_offset, getY()) ){ 
            killPlayer();
        } 
        
         if (platformer.isDeath(getX()-X_offset, getY()) ){ 
            killPlayer();
        } 
        
        /**
         * YELLOW MASK
         */
        
        if (platformer.isHurt(getX(), getY()+Y_offset)){  
            hurtPlayer();
        } 
        
        if (platformer.isHurt(getX(), getY()-Y_offset) ){
            hurtPlayer();
        }
        
        if (platformer.isHurt(getX()+X_offset, getY()) ){ 
            hurtPlayer();
        } 
        
        if (platformer.isHurt(getX()-X_offset, getY()) ){ 
            hurtPlayer();
        } 
        
        /**
         * BLUE MASK
         */
        
        /**same as the BLACK, but only for landing*/
      
        if (platformer.isUpPlatform(getX(), getY()+(Y_offset+2)) && speedY > -1){
            /**nullifies falling speed, (landing) allow player to jump again*/
            canJump = true;
            
            if (!Greenfoot.isKeyDown("up")){
                
              speedY = 0;
           }
        }
      
        if (platformer.isUpPlatform(getX(), getY()+Y_offset) && speedY > -1){  
            /**y coorninate go upwards to prevent going downwards*/
            y -= 1;
            if (speedY > 0){
                 setAnim_land();
            }
        } 
        
        /**
         * PURPLE MASK
         */
        if (platformer.isBounce(getX(), getY()+Y_offset)){  
            speedY = -12; //Bounce up high
            canJump = false;
            setAnim_jump();
        } 
        
    }
    
    /**
     * Jump attack to do the damage to enemy, like what Mario can do!
     */
    private void jumpAttack(){
        /**set enemy variable everytime the player meets the enemy*/
        enemy Enemy = (enemy) getOneIntersectingObject(enemy.class);
        
        //Conditions Requirements
        if (Enemy != null  //The enemy needs to meet the player on contact.
        && speedY > 0  //Player must be falling.
        && getY()+34 < Enemy.getY() //Player must be above of the enemy.
        && Enemy.canBeAttackedFromAbove == true //only bounce when it's alive
        ){
            speedY = -5; //Bounce off
            Enemy.damage(1); //do damage
        }
    }
   //in getY()+34, 34 is the difference between getY() of player and getY() of enemy on the same floor
    /**
     * Get hurt.
     */
    private void getHurt(){
        hurt_delay--;
        
        /**set enemy variable everytime the player meets the enemy*/
        enemy Enemy = (enemy) getOneIntersectingObject(enemy.class);
        
        //Conditions Requirements
        if (Enemy != null  //The enemy needs to meet the player on contact.
        && Enemy.canHurtPlayer == true //The enemy is still alive, so can do the damage.
        && hurt_delay <= 0 //The delay of getting hurt should be over
        && anim_delay <= 0 //The delay of hurt pose should be over
        && getY()+34 >= Enemy.getY()
        ){
            if (getX() < Enemy.getX()) {speedX = -5; dir = 0;}
            if (getX() > Enemy.getX()) {speedX = 5; dir = 12;}
            if (getX() == Enemy.getX()) {speedX = 0;} //bounce reactions from the enemy
            speedY = 0; //stop falling for a while
            setAnim_hurt(); //set hurt pose
            HP--; //do damage to the player
            hurt_delay = 60; //reset hurt delay
            anim_delay = 30; //freeze the hurt pose for a while
        }
    }
    
    /**
     * Get hurt, for this one, even the developer can hurt the player
     */
    public void hurtPlayer(){
        if (hurt_delay <= 0 && anim_delay <= 0){
            speedY = -5;
            setAnim_hurt();
            HP--;
            hurt_delay = 60;
            anim_delay = 30;
        }
    }
    
    /**
     * Now hurt in any value.
     */
    public void damagePlayer(int dmg){
        if (hurt_delay <= 0 && anim_delay <= 0){
            speedY = -5;
            setAnim_hurt();
            HP -= dmg;
            hurt_delay = 60;
            anim_delay = 30;
        }
    }
    
    /**
     * This one is just mean...
     */
    public void killPlayer(){
        HP = 0;
    }
    
    /**
     * ...and a friendly one also.
     */
    public void healPlayer(int heal){
        if (HP >= maxHP){
            HP = maxHP;
            return;
        }
        HP += heal;
    }
    
    /**
     * regrow
     */
    public void regenPlayer(){
        if (HP >= maxHP){
            HP = maxHP;
            return;
        }
        HP++;
    }
    
}