/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint_overhaul.threads;

import java.io.File;
import javafx.application.Platform;
import paint_overhaul.other.Main;

/**
 *
 * @author dylan
 */
public class AutoSaveThread {
    private final Thread thread;
    private final String autoSaveLocation = "src/paint_overhaul/autosave/autosave.png";
    private final File file;
    
    public AutoSaveThread(){
        System.out.println("Auto save thread created");
        file = new File(autoSaveLocation);
        thread = new Thread(() -> {
            while(true){
                handleAutoSave();
            }
        });
        thread.setDaemon(true);
        
    }
    public void startAutoSaveThread(){
        thread.start();
    }
    private void handleAutoSave(){
        try{
            for(int i=60; i>0; i--){
                int currentCount=i;
                Platform.runLater(() -> {
                    Main.paintController.setAutoSaveLabel("Saving in: " +  currentCount);
                });
                Thread.sleep(1000);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        try{
            Platform.runLater(() -> {
                Main.paintController.getPaintCanvas().autoSaveCanvasToFile(file);
            });  
        }
        catch(IllegalArgumentException e){
            System.out.println("No canvas");
        }
        
    }
    /*
    //private final PaintCanvas paintCanvas;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    
    private TimerTask autoSaveTask;
    private Timer autoSaveTimer;
    
    private Timer labelTimer;
    private TimerTask labelTask;
    
    private final long delayInSeconds = 30;
    private final long delayInMilliseconds = delayInSeconds*1000;
    private final long periodInSeconds = 30;
    private final long periodInMilliseconds = periodInSeconds*1000;
    private final String autoSaveLocation = "src/paint_overhaul/autosave/autosave.png";
    private File file = new File(autoSaveLocation);
    //long i = periodInSeconds;
    private int count;
    public AutoSaveThread(){
        this.count = 30;
        System.out.println("auto save thread created");
        createAutoSaveTask();
        createLabelTask();
        createScheduledTimer();
        createLabelTimer();
        
    }
        //beepForAnHour();
    private void createAutoSaveTask(){
        autoSaveTask = new TimerTask(){
            @Override
            public void run(){
                System.out.println("BEEP");
                
                //System.out.println(Main.paintController.getPaintCanvas().getCurrentZoom());
                Platform.runLater(new Runnable(){
                    @Override
                    public void run(){
                        
                        try{
                            if(Main.paintController.getPaintCanvas().getOpenedFile() != null){
                                
                                Main.paintController.getPaintCanvas().autoSaveCanvasToFile(file);
                            }
                            else{}
                        }
                        catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                });
                
            }
    };}
    private void createScheduledTimer(){
        autoSaveTimer = new Timer("Auto Save Timer", true);
       
        autoSaveTimer.scheduleAtFixedRate(autoSaveTask, delayInMilliseconds, periodInMilliseconds);
    }
    private void createLabelTimer(){ 
        labelTimer = new Timer("Label Timer", false);
        
        labelTimer.schedule(labelTask, 0, 1000);
    }
    private void createLabelTask(){
        
        labelTask = new TimerTask(){
            @Override
            public void run(){
                if(count==0){
                    count = 30;
                    System.out.println("RESET");
                }
                System.out.println(count +"s!");
                count--;
                
            }
    };}
    public Timer getAutoSaveTimer(){
        return autoSaveTimer;
    }
    */
    /*
    public AutoSaveThread(){
        System.out.println("auto thread created");
        
        file = new File(autoSaveLocation);
        //beepForAnHour();
        
    }
    public void beepForAnHour() {
     final Runnable beeper = new Runnable() {
        @Override
        public void run() { 
            System.out.println("beep");
            System.out.println(Main.paintController.getPaintCanvas().getCurrentZoom());
            //Main.paintController.getPaintCanvas().autoSaveCanvasToFile(file);
        }
     };
     final ScheduledFuture<?> beeperHandle =
        scheduler.scheduleAtFixedRate(beeper, 10, 30, SECONDS);
        scheduler.schedule(new Runnable() {
            public void run() { beeperHandle.cancel(true); }
     }, 60 * 60, SECONDS);     
   }
    public void shutdownThread(){
        System.out.println("Shutting down");
        scheduler.shutdownNow();
    }
    public ScheduledExecutorService getScheduler(){
        return scheduler;
    }
*/
}
