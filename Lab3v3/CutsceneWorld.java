import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class CutsceneWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CutsceneWorld extends World
{
    public static final GreenfootSound backgroundMusic = new GreenfootSound("The Lake.wav");

    /**
     * Constructor for objects of class CutsceneWorld.
     * 
     */
    public CutsceneWorld()
    {     
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(PlatformWorld.sWidth, PlatformWorld.sHeight, 1); 
        prepare();
    }
    
    public void stopped() {
         backgroundMusic.pause(); 
    }
     
    public void started() {
        backgroundMusic.playLoop();
    }

    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {
        CutscenePlayer cutsceneplayer = new CutscenePlayer();
        addObject(cutsceneplayer,262,313);
        DialogBox dialogbox = new DialogBox(cutsceneplayer);
        addObject(dialogbox,617,472);
        cutsceneplayer.setLocation(124,479);
        cutsceneplayer.setLocation(527,182);
        dialogbox.setLocation(540,421);

        DialogManager dialogmanager = new DialogManager(dialogbox, cutsceneplayer);
        addObject(dialogmanager,836,97);
        
        dialogmanager.addLine("Hello there!");
        dialogmanager.addLine("My name is Wilbert!");
        dialogmanager.addLine("Welcome to my home!");
        dialogmanager.addLine("I have lots of friends");
        dialogmanager.addLine("here but it gets a little");
        dialogmanager.addLine("lonely sometimes.");
        dialogmanager.addLine("Hey!");
        dialogmanager.addLine("I have an idea!");
        dialogmanager.addLine("Lets go on a trip!");
        dialogmanager.addLine("See the rest of this");
        dialogmanager.addLine("beautiful world!");
        dialogmanager.addLine("Come on! Lets go!");
        dialogmanager.addLine("...");
        dialogmanager.addLine("what do you mean?");
        dialogmanager.addLine("be careful?");
        dialogmanager.addLine("...");
        dialogmanager.addLine("Oh theres nothing");
        dialogmanager.addLine("to worry about!");
        dialogmanager.addLine("Everyone here is friendly!");
        dialogmanager.addLine("Here goes!");
        dialogmanager.addLine("");
        dialogmanager.addLine("By the way...");
        dialogmanager.addLine("Were in beta so some things");
        dialogmanager.addLine("are a little...funky.");
        dialogmanager.addLine("Alright come on now!");
        dialogmanager.nextWorld = new PlatformWorld();
        
        dialogmanager.start();
        
        backgroundMusic.playLoop();
         backgroundMusic.pause(); 
    }
}
