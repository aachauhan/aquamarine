import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

public class Aquamarine extends Frame implements Runnable {
    Image aquaMarineBgImage, memoryImage;
    Graphics memoryGraphics;
    Image[] lilFishies = new Image[2];
    MediaTracker bg_tracker;
    Thread thread;
    int numberOfFishes = 12;
    int sleepTime = 110;
    //Vector<Fish> fishes = new Vector<Fish>();
    boolean runOK = true;


    Vector fishes = new Vector();

    Aquamarine(){

        setTitle("AMAZING AQUARIUM 1.0");

        bg_tracker = new MediaTracker(this);

        lilFishies[0] = Toolkit.getDefaultToolkit().getImage("fish.png");
        bg_tracker.addImage(lilFishies[0],0);

        lilFishies[1] = Toolkit.getDefaultToolkit().getImage("fish2.svg");
        bg_tracker.addImage(lilFishies[1], 0);

        aquaMarineBgImage = Toolkit.getDefaultToolkit().getImage("aquarium.jpg");
        bg_tracker.addImage(aquaMarineBgImage, 0);

        try{
            bg_tracker.waitForID(0);
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }

        setSize(aquaMarineBgImage.getWidth(this), aquaMarineBgImage.getHeight(this));

        setResizable(false);

        setVisible(true);

        memoryImage = createImage(getSize().width, getSize().height);
        memoryGraphics = memoryImage.getGraphics();

        thread = new Thread(this);
        thread.start();




        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                runOK = false;
                System.exit(0);
                //super.windowClosing(e);

            }
        });
    }

    public void run(){
        Rectangle edges = new Rectangle(0 + getInsets().left, 0
                + getInsets().top, getSize().width - (getInsets().left
                + getInsets().right), getSize().height - (getInsets().top
                + getInsets().bottom));

        for(int loopIndex = 0; loopIndex < numberOfFishes; loopIndex++){
            fishes.add( new Fish(lilFishies[0], lilFishies[1], edges, this));
            try{
                Thread.sleep(20);
            }
            catch (Exception exp){
                System.out.println(exp.getMessage());
            }
        }

        Fish fish;

        while (runOK){
            for (int loopIndex = 0; loopIndex < numberOfFishes; loopIndex++){
                fish = (Fish)fishes.elementAt(loopIndex);
                fish.swim();
            }

            try{
                Thread.sleep(sleepTime);
            }
            catch (Exception exp){
                System.out.println(exp.getMessage());
            }

            repaint();
        }
    }

    public void update(Graphics g){
        memoryGraphics.drawImage(aquaMarineBgImage, 0, 0, this);

        for(int i=0; i < numberOfFishes; i++){
            ((Fish)fishes.elementAt(i)).drawFishImage(memoryGraphics);
        }

        g.drawImage(memoryImage, 0, 0, this);
    }

    public static void main(String[] args){
        new Aquamarine();
    }
}
