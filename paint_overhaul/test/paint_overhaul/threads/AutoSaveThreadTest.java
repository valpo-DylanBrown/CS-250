/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint_overhaul.threads;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Dylan
 */
public class AutoSaveThreadTest {
    
    public AutoSaveThreadTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of startAutoSaveThread method, of class AutoSaveThread.
     */
    @Test
    public void testStartAutoSaveThread() {
        System.out.println("startAutoSaveThread");
        AutoSaveThread instance = new AutoSaveThread();
        instance.startAutoSaveThread();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of logTool method, of class AutoSaveThread.
     */
    @Test
    public void testLogTool() throws Exception {
        System.out.println("logTool");
        String tool = "";
        AutoSaveThread instance = new AutoSaveThread();
        instance.logTool(tool);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of logFile method, of class AutoSaveThread.
     */
    @Test
    public void testLogFile() throws Exception {
        System.out.println("logFile");
        String fileName = "";
        boolean mode = false;
        AutoSaveThread instance = new AutoSaveThread();
        instance.logFile(fileName, mode);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
/*
public class TimerTest {
    
    public TimerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of runTimer method, of class Timer.
    @Test
    public void testElapsedTime() throws Exception {
        System.out.println("Test that elapsed time is counting upwards");
        Timer instance = new Timer();
        Thread timerThread = new Thread(instance);
        timerThread.start();
        
        Thread.sleep(999L);
        assertEquals(instance.getTimeElapsed(), 0);
        Thread.sleep(100L);
        assertEquals(instance.getTimeElapsed(), 1);
    }

    /**
     * Test of setSeconds method, of class Timer.
    @Test
    public void testSetSeconds() {
        System.out.println("Test that Seconds variable is properly adjusted when setting seconds, but MaxSeconds does not change");
        Timer instance = new Timer();
        Thread timerThread = new Thread(instance);
        timerThread.start();
        
        instance.setSeconds(100);
        assertFalse(instance.getSeconds() == instance.getMaxSeconds());
    }

    /**
     * Test of setMaxSeconds method, of class Timer.
    @Test
    public void testSetMaxSeconds() {
        System.out.println("Test that Max Seconds and Seconds are the same after changing value of MaxSeconds");
        Timer instance = new Timer();
        Thread timerThread = new Thread(instance);
        timerThread.start();
        
        instance.setMaxSeconds(100);
        assertEquals(instance.getSeconds(), instance.getMaxSeconds());
    }
    
}
*/
