package com.company;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.company.Data.*;

public class OperatorBind
{
    public static List<String> keyWords;
    private static HashMap<String, Integer> typeToNum;
    private HashMap<String, OperExecute> detailExecute;
    private static HashMap<String, Data> mainNameSpace;

    public OperatorBind(CmdExecute cmdExecute)
    {
        detailExecute = new HashMap<>();
        keyWords = new ArrayList<>();
        typeToNum = new HashMap<>();
        mainNameSpace = new HashMap<>();

        typeToNum.put("word", WORD_TYPE);
        typeToNum.put("bool", BOOL_TYPE);
        typeToNum.put("number", NUM_TYPE);
        typeToNum.put("list", LIST_TYPE);
        typeToNum.put("undefined", UNDEFINED_TYPE);

        keyWords.add("make");
        keyWords.add("thing");
        keyWords.add("erase");
        keyWords.add("isname");
        keyWords.add("print");
        keyWords.add("read");
        keyWords.add("add");
        keyWords.add("sub");
        keyWords.add("mul");
        keyWords.add("div");
        keyWords.add("mod");
        keyWords.add("eq");
        keyWords.add("gt");
        keyWords.add("lt");
        keyWords.add("and");
        keyWords.add("or");
        keyWords.add("not");

        keyWords.add("readlist");
        keyWords.add("repeat");
        keyWords.add("isnumber");
        keyWords.add("isword");
        keyWords.add("islist");
        keyWords.add("isbool");
        keyWords.add("isempty");
        keyWords.add("random");
        keyWords.add("sqrt");
        keyWords.add("int");
        keyWords.add("output");
        keyWords.add("stop");
        keyWords.add("export");

        keyWords.add("word");
        keyWords.add("if");
        keyWords.add("sentence");
        keyWords.add("list");
        keyWords.add("join");
        keyWords.add("first");
        keyWords.add("last");
        keyWords.add("butfirst");
        keyWords.add("butlast");
        keyWords.add("wait");
        keyWords.add("save");
        keyWords.add("load");
        keyWords.add("erall");
        keyWords.add("poall");
        keyWords.add("pi");
        keyWords.add("run");

        for (String s : keyWords)
        {
            try
            {
                mainNameSpace.put(s, new Data(s));
                String className = "com.company.DetailCommand." + s.substring(0, 1).toUpperCase() + s.substring(1) + "Execute";
                Class executeClass = Class.forName(className);
                Constructor constructor = executeClass.getConstructor(cmdExecute.getClass());
                OperExecute getExecuteClass = (OperExecute) constructor.newInstance(cmdExecute);
                detailExecute.put(s, getExecuteClass);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public static int typeToNum(String choice)
    {
        return typeToNum.getOrDefault(choice, UNDEFINED_TYPE);
    }

    public OperExecute getExecuteClass(String key)
    {
        Data temp = mainNameSpace.getOrDefault(key, null);
        if (temp == null)
        {
            return null;
        }
        else
        {
            if (temp.getType() == OPER_TYPE)
            {
                return detailExecute.getOrDefault(key, null);
            }
            return null;
        }
    }

    public static void addOneOperBind(String key)
    {
        if (mainNameSpace.get(key) != null)
        {
            mainNameSpace.get(key).notOperator();
            mainNameSpace.put(key, null);
        }
    }

    public static Data deleteOneOperBind(String key)
    {
        if (mainNameSpace.get(key) != null)
        {
            mainNameSpace.get(key).notOperator();
            return mainNameSpace.remove(key);
        }
        return null;
    }
}
