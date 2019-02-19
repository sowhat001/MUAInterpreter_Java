package com.company;

import java.util.ArrayList;
import java.util.List;

public class Parse
{
    public static List<String> parseCmd(String cmd) throws Exception
    {
        List<String> ret = new ArrayList<>();
        //清除注释
        if (cmd.contains("//"))
        {
            cmd = cmd.substring(0, cmd.indexOf("//"));
        }

        //若清除注释后不为空
        if (!cmd.equals(""))
        {
            //替换空格
            cmd = cmd.trim().replaceAll("\t", " ").replace(":", "thing \"").replace("\n", " ");
            int bracket = 0;
            int formerSpace = 0;
            for (int i = 0; i < cmd.length(); i++)
            {
                if (cmd.charAt(i) == '[')
                {
                    if (i == 0)
                    {
                        bracket++;
                    }
                    else
                    {
                        if (cmd.charAt(i - 1) != '"')
                        {
                            if(bracket == 0)
                                formerSpace = i;
                            bracket++;
                        }
                    }
                }
                if(cmd.charAt(i) == ']')
                {
                    if(cmd.charAt(i - 1) != '"')
                    {
                        bracket--;
                    }
                }
                if (bracket == 0)
                {
                    if (cmd.charAt(i) == ' ')
                    {
                        ret.add(cmd.substring(formerSpace, i).trim());
                        formerSpace = i;
                    }
                    if(i == cmd.length() - 1)
                    {
                        ret.add(cmd.substring(formerSpace, i + 1).trim());
                    }
                }
            }
        }
//        for(String s:ret)
//        {
//            System.out.println("Parse: " + s);
//        }
        return ret;
    }

    //平衡括号，右括号多就返回负数，左括号多返回正数，合法返回0
    public static int checkBracket(String listContent)
    {
        boolean isWord = false;
        int lBracket = 0;
        int rBracket = 0;
        listContent = listContent.replace("\n", " ").replace("\t", " ");
        for (int i = 0; i < listContent.length(); i++)
        {
            if (!isWord)
            {
                if (listContent.charAt(i) == '[')
                {
                    lBracket++;
                }
                else if (listContent.charAt(i) == ']' && lBracket > 0)
                {
                    rBracket++;
                }
                else if (listContent.charAt(i) == ']' && lBracket <= 0)
                {
                    return -1;
                }
            }
            if (listContent.charAt(i) == '"')
            {
                isWord = true;
            }
            if (listContent.charAt(i) == ' ')
            {
                isWord = false;
            }
        }
        if (lBracket != rBracket)
        {
            return lBracket - rBracket;
        }
        return 0;
    }
}
