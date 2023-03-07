package local.myproject.Calculate;



import org.junit.jupiter.api.*;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    private Operand num;
    /**
     * Rigorous Test :-)
     */
    @BeforeAll
    static void setup(){
        System.out.println("@BeforeAll executed");
    }

    @BeforeEach
    void setupThis(){
        num = new Operand(TypeNum.Arabic,5);
        System.out.println("@BeforeEach executed");
    }

    @Test
    void testCalcOne()
    {
        System.out.println("======TEST ONE EXECUTED=======");
        Assertions.assertEquals(  7 , num.apply("+", new Operand(TypeNum.Arabic, 2)).get());
    }

    @Test
    void testCalcTwo()
    {
        System.out.println("======TEST TWO EXECUTED=======");
        Assertions.assertEquals(  3 , num.apply("-", new Operand(TypeNum.Arabic, 2)).get());
    }

    @AfterEach
    void tearThis(){
        System.out.println("@AfterEach executed");
    }

    @AfterAll
    static void tear(){
        System.out.println("@AfterAll executed");
    }
}
