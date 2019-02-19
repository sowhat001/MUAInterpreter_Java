package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static com.company.OperatorBind.keyWords;

public class Data
{
    public final static int WORD_TYPE = 1;//1
    public final static int LIST_TYPE = 2;//10
    public final static int BOOL_TYPE = 3;//11
    public final static int OPER_TYPE = 4;
    public final static int NUM_TYPE = 7;//111
    public final static int UNDEFINED_TYPE = 8;//1000
    private int type;
    private String org;
    private double numValue;
    private boolean boolValue;
    private List<String> listValue;
    private String literal;

    public Data(String org)
    {
        org = org.trim();
        this.listValue = new ArrayList<>();
        this.org = org;
        this.literal = org;
        if (!org.equals(""))
        {
            if (org.charAt(0) == '"')
            {//clear the "
                this.literal = org.substring(1);
            }
            Pattern numPattern = Pattern.compile("^[+-]?\\d+(\\.\\d+)?$");
            //all number contains +,-,.
            if (numPattern.matcher(literal).matches())
            {
                this.type = NUM_TYPE;
                this.numValue = Double.valueOf(literal);
            }
            else if (literal.equalsIgnoreCase("true") || literal.equalsIgnoreCase("false"))
            {
                this.type = BOOL_TYPE;
                this.boolValue = Boolean.valueOf(literal);
            }
            else if (org.charAt(0) == '"')
            {
                this.type = WORD_TYPE;
            }
            else if (org.charAt(0) == '[' && org.charAt(org.length() - 1) == ']')
            {
                this.type = LIST_TYPE;
                this.listValue = checkAndGetValue();
            }
            else if(keyWords.contains(org))
            {
                this.type = OPER_TYPE;
            }
            else
            {
                this.type = UNDEFINED_TYPE;
            }
        }
    }

    public Data(double num)
    {
        this.listValue = new ArrayList<>();
        this.org = this.literal = String.valueOf(num);
        this.numValue = num;
        this.type = NUM_TYPE;
    }

    public Data(boolean bool)
    {
        this.listValue = new ArrayList<>();
        this.org = this.literal = String.valueOf(bool);
        this.boolValue = bool;
        this.type = BOOL_TYPE;
    }

    public Data(List<String> list)
    {
        this.listValue = new ArrayList<>(list);
        this.type = LIST_TYPE;
        StringBuffer tempList = new StringBuffer();
        tempList.append("[ ");
        for(String i: list)
        {
            tempList.append(i + " ");
        }
        tempList.append("]");
        this.org = this.literal = tempList.toString();
    }

    @Override
    public String toString()
    {
        return this.org;
    }

    public String getLiteral()
    {
        return this.literal;
    }

    public int getType()
    {
        return this.type;
    }

    public List<String> getListValue() throws Exception
    {
        if (this.type != LIST_TYPE)
        {
            throw new Exception("Type Error: this data is not a list type.");
        }
        else
        {
            return this.listValue;
        }
    }

    public double getNumberValue() throws Exception
    {
        if(this.type != NUM_TYPE)
        {
            throw new Exception("Type Error: this data is not a number type.");
        }
        else
        {
            return this.numValue;
        }
    }

    public boolean getBooleanValue() throws Exception
    {
        if(this.type != BOOL_TYPE)
        {
            throw new Exception("Type Error: this data is not a boolean type.");
        }
        else
        {
            return this.boolValue;
        }
    }

    public boolean isWord()
    {
        return this.type % 2 == 1;
    }

    public boolean isFunction(CmdExecute cmdExecute) throws Exception
    {
        if (cmdExecute.getValue(this.getLiteral()) != null && cmdExecute.getValue(this.getLiteral()).getType() == LIST_TYPE && this.getLiteral().equals(this.toString()))
        {
            List<String> funcBody = cmdExecute.getValue(this.getLiteral()).getListValue();
            //System.out.println("Output From CmdExecute: getValue(nowCmd.getLiteral()).getLiteral(): " + getValue(temp.getLiteral()).getLiteral());
            if (funcBody.size() != 2)
            {
                throw new Exception("Function Error: Format Error");
            }
            else
            {
                Data d1 = new Data(funcBody.get(0));
                Data d2 = new Data(funcBody.get(1));
                if (d1.getType() != LIST_TYPE || d2.getType() != LIST_TYPE)
                {
                    throw new Exception("Function Error: Format Error");
                }
                return true;
            }
        }
        return false;
    }

    public boolean getHaveReturnValue(CmdExecute cmdExecute) throws Exception
    {
        if (isFunction(cmdExecute))
        {
            try
            {
                List<String> funcBody = cmdExecute.getValue(this.getLiteral()).getListValue();
                Data d2 = new Data(funcBody.get(1));
                String detailCommand = d2.getLiteral();
                if (detailCommand.contains("output"))
                {
                    return true;
                }
                return false;
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
        return false;
    }

    private List<String> checkAndGetValue()
    {
        String tempList = this.org.substring(1, this.org.length() - 1);
        if (!tempList.matches("^\\s*$"))
        {
            int bracket = 0;
            tempList = tempList.replace("[", " [ ").replace("]", " ] ").trim();
            String[] splitList = tempList.split("\\s+");
            //get value
            StringBuffer nestedList = new StringBuffer();
            for (String s : splitList)
            {
                //System.out.println("From Data: Elements from splitList: " + s);
                if (bracket > 0)
                {
                    if (s.equals("]"))
                    {
                        bracket--;
                        nestedList.append(s + " ");
                        if (bracket == 0)
                        {
                            this.listValue.add(nestedList.toString());
                            nestedList.setLength(0);
                        }
                    }
                    else if (s.equals("["))
                    {
                        bracket++;
                        nestedList.append(s + " ");
                    }
                    else
                    {
                        nestedList.append(s + " ");
                    }
                }
                else
                {
                    if (s.equals("["))
                    {
                        bracket++;
                        nestedList.append("[ ");
                    }
                    else
                    {
                        this.listValue.add(s.trim());
                    }
                }
            }
        }
        //        for (String temp : listValue)
        //        {
        //            System.out.println("From Data: Elements from list: " + temp);
        //        }
        return this.listValue;
    }

    void notOperator()
    {
        this.type = WORD_TYPE;
    }
}
