/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerTests;


import Controller.TxtFileWriter;
import java.io.IOException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author 611213417
 */
public class testFile {
    
    public testFile() {
    }
    
    @Test
    public void testSimpleTxt() throws IOException {
        TxtFileWriter fw = new TxtFileWriter("Andrew Bolt");
        assertEquals(fw.fileContents, "Answer: 8 \n Response url: /task/4017");
        
    }
    
    @Test
    public void testServerOutputTxt(){
        
    }
}
