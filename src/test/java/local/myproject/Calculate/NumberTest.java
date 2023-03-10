package local.myproject.Calculate;


import org.junit.jupiter.api.*;

public class NumberTest {

    Operand num1 = new Operand(Operands.Arabic,20);
    Operand num2 = new Operand(Operands.Arabic,40);
    Operand nullArabic = new Operand(Operands.Arabic,0);
    Operand num3 = new Operand(Operands.Roman,20);
    Operand num4 = new Operand(Operands.Roman,40);
    Operand nullRoman = new Operand(Operands.Roman,0);

    @Test
    void testCalcArabic()
    {
        Assertions.assertEquals(  60 , num1.apply("+", num2).get());
        Assertions.assertEquals(  -20 , num1.apply("-", num2).get());
        Assertions.assertEquals(  20 , num2.apply("-", num1).get());
        Assertions.assertEquals(  0.5 , num1.apply("/", num2).get());
        Assertions.assertEquals(  800 , num1.apply("*", num2).get());
        Assertions.assertEquals(  -20 , num1.apply("-", num4).get());
    }

    @Test
    void testCalcRoman()
    {
        Assertions.assertEquals(  60 , num3.apply("+", num4).get());
        Assertions.assertEquals(  20 , num4.apply("-", num3).get());
        Assertions.assertEquals(  0 , num3.apply("-", num4).get());
        Assertions.assertEquals(  0.5 , num3.apply("/", num4).get());
        Assertions.assertEquals(  800 , num3.apply("*", num4).get());
        Assertions.assertEquals(  0 , num3.apply("-", num2).get());
    }

}
