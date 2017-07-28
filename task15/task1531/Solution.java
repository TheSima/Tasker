package com.javarush.task.task15.task1531;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/*
Факториал
*/


public class Solution {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int input = Integer.parseInt(reader.readLine());
        reader.close();

        System.out.println(factorial(input));
    }

    public static String factorial(int n) {
        if (n < 0) { return "0"; }
        if ((n == 1) || (n == 0)) return "1";
        return multipleStrInt(factorial(n-1), n);
    }

    public static String multipleStrInt (String str, int m) {
        // Проверка соответствия формату
        if (!checkStrForBigNumeric(str)) throw new NumberFormatException();

        // если str отрицательное, приводим к положительному сохраняем в массив символов; вычисляем знак результата умножения.
        char[] checkChars;
        boolean resultNegativeValue = false;
        if (str.charAt(0) == '-') {
            checkChars = str.substring(1).toCharArray();
            if (m > 0) resultNegativeValue = true;
        }
        else {
            checkChars = str.toCharArray();
            if (m < 0) resultNegativeValue = true;
        }

        // Выполнение посимвольного умножения (с приведённым к положительному checkChars)
        long[] multiLongs = new long[checkChars.length];
        for (int i = 0; i < checkChars.length; i++) {
            multiLongs[i] = Character.getNumericValue(checkChars[checkChars.length -1 - i]) * m; // сохранение произведений в соответствии с разрядом первого множителя
        }

        // Суммирование полученных произведений
        String resultStr = String.valueOf(multiLongs[0]);
        for (int i = 1; i < multiLongs.length; i++) {
            StringBuilder powerTen = new StringBuilder();
            if (multiLongs[i] != 0) {
                for (int j = 0; j < i; j++) {
                    powerTen.append('0');
                }
            }
            resultStr = sumStrStr(resultStr, String.valueOf(multiLongs[i]) + powerTen.toString());
        }

        // Возвращение знака отрицательному числу
        return (resultNegativeValue ? "-" : "") + resultStr; // подстановка знака результата умножения
    }



    public static boolean checkStrForBigNumeric(String str) { // На "-0" будет true
        // Проверки соответствия формату
        if ((str.length() == 0) || ("-".equals(str))) return false; // проверка длинны строки, положительные || отрицательные
        char[] checkChars;
        if (str.charAt(0) == '-') { checkChars = str.substring(1).toCharArray();} // если отрицательное, приводим к положительному
        else { checkChars = str.toCharArray(); }
        if ((checkChars[0] == '0') && (checkChars.length != 1)) return false; // проверка первого на '0', исключение полное значение 0
        for (int i = 0; i < checkChars.length; i++) {
            char checked = checkChars[i];
            if ((checked < '0') || (checked > '9')) { // проверка диапазона значений [0-9]
                return false;
            }
        }
        return true;
    }



    public static String sumStrStr(String aStr, String bStr) { // только > 0
        // Проверка соответствия формату
        if (!checkStrForBigNumeric(aStr)) throw new NumberFormatException();
        if (!checkStrForBigNumeric(bStr)) throw new NumberFormatException();
        // aStr + bStr
        if (bStr.length() > aStr.length()) {
            String buf = bStr;
            bStr = aStr;
            aStr = buf;
        }

        StringBuilder result = new StringBuilder();

        boolean plusOne = false;
        for (int i = 0; i < bStr.length(); i++) {
            byte A = (byte) Character.getNumericValue(aStr.charAt(aStr.length() - i -1));
            byte B = (byte) Character.getNumericValue(bStr.charAt(bStr.length() - i -1));
            byte APlusB = (byte) (A + B);
            if (plusOne) APlusB++;
            plusOne = ((APlusB / 10) > 0);
            result.append(Character.forDigit(APlusB % 10, 10));

        }

        int AMoreB = aStr.length() - bStr.length();
        if (plusOne) {
            if (AMoreB > 0) {
                String bufStr = sumStrStr(aStr.substring(0,AMoreB), "1");
                for (int i = 0; i < bufStr.length(); i++) {
                    result.append(bufStr.charAt(bufStr.length() - 1 - i));
                }
            }
            else result.append('1');
        }
        else {
            if (AMoreB > 0) {
                String bufStr = aStr.substring(0,AMoreB);
                for (int i = 0; i < bufStr.length(); i++) {
                    result.append(bufStr.charAt(bufStr.length() - 1 - i));
                }
            }
        }

        result.reverse();
        return result.toString();

    }

}


