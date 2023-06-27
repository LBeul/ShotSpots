package com.lbeul.shotspots_v2.utils;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class MathUtilTest {

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { 2.3443543634, 3, 2.344 }, { 0.0, 5, 0.00000 }, {-3.66666666, 4, -3.6667}
        });
    }

    private final int precision;
    private final double in;
    private final double out;

    public MathUtilTest(double in, double precision, double out) {
        this.precision = (int) precision;
        this.in = in;
        this.out = out;
    }

    @Test
    public void testRounding() {
        double result = MathUtil.round(in, precision);
        assertEquals(result, out, 0);
    }
}