package junit.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import junit.test.main.JunitMain;

public class   JunitTestCase {
    JunitMain jm;
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    	System.out.println("setUpBeforeClass()");
    }
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    	System.out.println("tearDownAfterClass()");
    }
    @Before
    public void setUp() throws Exception {
    	System.out.println("setUp()");
    }
    @After
    public void tearDown() throws   Exception {
    	System.out.println("tearDown()");
    }

    @Test
    public void test() {
    	jm = new JunitMain();
    	assertSame(3,   jm.add(1, 2));
    }
}
