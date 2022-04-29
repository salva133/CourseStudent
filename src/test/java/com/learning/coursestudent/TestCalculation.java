package com.learning.coursestudent;

import org.junit.jupiter.api.Test;

public class TestCalculation {

    @Test
    public void testIncrement() {
        int a = 0;
        int b = 4;
        int c1 = 5;
        int c2 = 5;

        a = b * --c1; // a = 4 * (5-1);
        // Equals:
        // c1=c1-1;
        // a=b*c1;
        System.out.println(a);
        a = b * c2--; // a = 4 * 5 - 1;
        // Equals:
        // a=b*c2;
        // c2=c2-1;
        System.out.println(a);
        for (int i = 0; i < 10; i++) {
            System.out.println(i); // 0 1 2 3 4 5 6 7 8 9
        }
        for (int i = 0; i < 10; ++i) {
            System.out.println(i); // 0 1 2 3 4 5 6 7 8 9
        }
    }
}