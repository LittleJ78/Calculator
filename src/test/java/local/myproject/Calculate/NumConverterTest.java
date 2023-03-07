package local.myproject.Calculate;

import org.junit.jupiter.api.*;

import javax.swing.text.IconView;

public class NumConverterTest {

    @Test
    public void arabic () {
        Assertions.assertEquals("IV", NumConverter.arabicToRoman(4));
        Assertions.assertEquals("V", NumConverter.arabicToRoman(5));
        Assertions.assertEquals("X", NumConverter.arabicToRoman(10));
        Assertions.assertEquals("XXI", NumConverter.arabicToRoman(21));

    }
    @Test
    public void roman () {
        Assertions.assertEquals(2, NumConverter.romanToArabic("II"));
        Assertions.assertEquals(4, NumConverter.romanToArabic("IV"));
        Assertions.assertEquals(7, NumConverter.romanToArabic("VII"));
        Assertions.assertEquals(40, NumConverter.romanToArabic("XL"));

    }
}
