package com.epam.izh.rd.online.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class SimpleBigNumbersService implements BigNumbersService {

    /**
     * Метод делит первое число на второе с заданной точностью
     * Например 1/3 с точностью 2 = 0.33
     * @param range точность
     * @return результат
     */
    @Override
    public BigDecimal getPrecisionNumber(int a, int b, int range) {
        BigDecimal bigA=new BigDecimal(a);
        BigDecimal bigB=new BigDecimal(b);
        return bigA.divide(bigB,range, RoundingMode.HALF_UP);
    }

    /**
     * Метод находит простое число по номеру
     *
     * @param range номер числа, считая с числа 2
     * @return простое число
     */
    @Override
    public BigInteger getPrimaryNumber(int range) {
        BigInteger[] simpleNumbers=new BigInteger[range];  //0-ой элемент массива будет числом 1
        simpleNumbers[0]=new BigInteger("1");  //задаём первое значение
        for (int i=1; i<range; i++){
             simpleNumbers[i]=simpleNumbers[i-1].nextProbablePrime();
         }
        return simpleNumbers[range-1]; //т.к. например второе число будет иметь индекс [1]
    }
}
