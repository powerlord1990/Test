package com.tsystems.javaschool.tasks.calculator;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Calculator {

    /**
     * Evaluate statement represented as string.
     *
     * @param statement mathematical statement containing digits, '.' (dot) as decimal mark,
     *                  parentheses, operations signs '+', '-', '*', '/'<br>
     *                  Example: <code>(1 + 38) * 4.5 - 1 / 2.</code>
     * @return string value containing result of evaluation or null if statement is invalid
     */
    public String evaluate(String statement) {
        // TODO: Implement the logic here
        double sum = 0.0;

        if(statement != null) statement = statement.replaceAll("\\s", "");

        if(!confirmValidity(statement)) return null;

        if(statement.contains("("))	statement = computeParenthesesBlocks(statement);

        if(!confirmValidity(statement)) return null;


        String minuslessStr = statement.replaceAll("-", "+-");

        minuslessStr = correctMisplacedPluses(minuslessStr, statement);

        String[] byPluses = minuslessStr.split("\\+");


        for(int i = 0; i < byPluses.length; i++)
        {
            if(isValidDigit(byPluses[i]))
            {
                if(byPluses[i].equals("")) continue;

                sum = sum  + Double.parseDouble(byPluses[i]);
            }

            if(byPluses[i].contains("*")) sum += getMultiplyResult(byPluses[i]);


            if(byPluses[i].contains("/") && !byPluses[i].contains("*"))
                sum += getDivisionResult(byPluses[i]);

        }
        return roundDigit(sum);
    }

    private static String roundDigit(double digit)
    {
        DecimalFormat df = new DecimalFormat("#.####");
        df.setRoundingMode(RoundingMode.CEILING);

        return df.format(digit);
    }

    private  String computeParenthesesBlocks(String str)
    {
        int parenthesese  = getTotalParenthesesBlock(str);

        while (parenthesese > 0)
        {
            int inrMostOpnPthns = str.lastIndexOf('(');

            int inrMostClsPthns = str.indexOf(')', inrMostOpnPthns);

            String innMstPrtSubStr = str.substring(inrMostOpnPthns, inrMostClsPthns +1);

            String result = computeSingleParenthesesBlock(innMstPrtSubStr);

            if(result == null) str = str.replace(innMstPrtSubStr, "0");
            else str = str.replace(innMstPrtSubStr, result);

            parenthesese  = getTotalParenthesesBlock(str);
        }

        return str;
    }

    private int getTotalParenthesesBlock(String str)
    {
        int counter = 0;
        for(int i = 0; i < str.length(); i++)
            if(str.charAt(i) == '(') counter++;
        return counter;
    }

    private double getMultiplyResult(String string)
    {
        double result = 1.0;

        String[] byMultiplies = string.split("\\*");

        for(String multiply : byMultiplies)
        {
            if(multiply.contains("/")) result *=  getDivisionResult(multiply);
            else result *= Double.parseDouble(multiply);
        }

        return result;
    }

    private double getDivisionResult(String string)
    {
        String[] byDivision = string.split("\\/");
        double divident = Double.parseDouble(byDivision[0]); ;
        for(int i = 0; i < byDivision.length - 1; i++)
            divident /= Double.parseDouble(byDivision[i + 1]);

        return divident;
    }

    private static boolean isValidDigit(String string)
    {
        if(string.contains("/") || string.contains("*")) return false;
        return true;
    }

    private String computeSingleParenthesesBlock(String str)
    {
        String result = "";

        if(str.indexOf("(") != -1)
        {
            int startInd = str.indexOf("(");
            int endInd = str.indexOf(")", startInd + 1);
            String subStr = str.substring(startInd+1, endInd);
            System.out.println("string in1 paretheses: " + subStr);
            result  = evaluate(subStr);;
        }

        return result;
    }

    private String correctMisplacedPluses(String noMinuses, String str)
    {
        StringBuilder retString = new StringBuilder(noMinuses);

        for(int i = 0; i < str.length(); i++)
        {
            if(str.indexOf('-', i) != -1)
            {
                int ind = str.indexOf('-', i);
                if(ind > 0 &&((str.charAt(ind-1) == '*' ||str.charAt(ind-1) == '/')  ||
                        (str.charAt(ind+1) == '*' ||str.charAt(ind +1) == '/')))
                {
                    retString.delete(ind, ind+1);
                    i = i + ind;
                }
            }
        }

        return retString.toString();
    }
    private boolean confirmValidity(String str)
    {
        if(str == null || str.length() == 0) return false;

        if((str.indexOf("..") != -1) ||(str.indexOf("++") != -1)||(str.indexOf("--") != -1)||
                (str.indexOf("**") != -1)||(str.indexOf("//") != -1)) return false;

        if(str.indexOf('(') > str.indexOf(')')) return false;

        int openParth = 0;
        int closeParth = 0;

        if(isDividedByZero(str)) return false;

        for(int i = 0; i < str.length(); i++)
        {
            char ch = str.charAt(i);
            if( ch == '(') openParth++;
            if(ch == ')') closeParth++;
            if(Character.isAlphabetic(ch)) return false;

            if(!Character.isDigit(ch) && ch != '+' && ch != '-' &&  ch != '/' &&  ch != '*' &&  ch != '.'
                    && ch != '(' && ch != ')')  return false;
        }

        if(openParth != closeParth) return false;
        return true;
    }
    private boolean isDividedByZero(String str)
    {
        int ind = str.indexOf('/');
        if(ind < str.length() && str.charAt(ind + 1) == '0') return true;

        String leftString = "";
        ind = ind + 1;
        char ch = str.charAt(ind);

        while(Character.isDigit(ch) && ind < str.length() - 1)
        {
            leftString += ch;
            ind = ind + 1;
            if(ind < str.length() - 1)
                ch = str.charAt(ind);
        }
        int minusIndex = ind;

        if(str.charAt(minusIndex) == '-' && ind < str.length())
        {
            String rightSubString = "";
            minusIndex += 1;
            ch = str.charAt(minusIndex);

            while(Character.isDigit(ch) && minusIndex < str.length())
            {
                rightSubString += ch;
                minusIndex = minusIndex + 1;

                if(minusIndex < str.length() - 1)
                    ch = str.charAt(minusIndex);
            }
            return leftString.equals(rightSubString);
        }
        return false;
    }
}
