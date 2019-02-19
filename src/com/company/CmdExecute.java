package com.company;

import com.company.DetailCommand.OutputExecute;
import com.company.DetailCommand.StopExecute;

import java.io.*;
import java.util.*;

import static com.company.Data.*;
import static com.company.MUA.getGlobalCmdExecute;

public class CmdExecute
{
    //cmd set, first pop is the rightmost one
    private Stack<Data> cmdSetStack;
    //execute set, first pop is the first parameter for one operation
    private Stack<Data> executeStack;
    //bind name and value
    public HashMap<String, Data> mainNameSpace;
    //bind operation name and its operation
    private OperatorBind operatorBind;

    private CmdExecute callSpace;

    public CmdExecute()
    {
        cmdSetStack = new Stack<>();
        executeStack = new Stack<>();
        mainNameSpace = new HashMap<>();
        operatorBind = new OperatorBind(this);
    }

    public void getCmdSet(List<String> cmdSet, CmdExecute callSpace) throws Exception
    {
        this.callSpace = callSpace;
        //        System.out.println("CmdExecute: caller: " + callSpace);
        //        System.out.println("CmdExecute: self: " + this);
        clearStack();
        List<String> tempCmdSet = new ArrayList<>(cmdSet);
        while (true)
        {
            List<String> nowCmdSet = new ArrayList<>(tempCmdSet);
            if (nowCmdSet.size() <= 0)
            {
                break;
            }
            boolean newSantance = false;
            boolean hasOp = false;
            MyArray needValue = new MyArray();
            for (String cmdPrimitive : nowCmdSet)
            {
                //System.out.println("CmdExecute: now" + cmdPrimitive);
                Data temp = new Data(cmdPrimitive);
                if (temp.getType() == UNDEFINED_TYPE && (getValue(temp.getLiteral()) == null || getValue(temp.getLiteral()).getType() != LIST_TYPE))
                {//未定义类型，既不属于那几个类型，又不是已经存在的名字，或者已经存在的名字但是不是list
                    clearStack();
                    throw new Exception("Data Type Error: Get a illegal operator.");
                }
                else if (temp.getType() == LIST_TYPE && nowCmdSet.size() == 1)
                {//直接执行，释放list
                    getCmdSet(temp.getListValue(), callSpace);
                    return;
                }
                else
                {
                    if (temp.getType() == OPER_TYPE)
                    {
                        hasOp = true;
                        OperExecute operExecute = operatorBind.getExecuteClass(cmdPrimitive);
                        if (operExecute.getHaveReturnValue())
                        {
                            needValue.minusOne();
                            needValue.add(operExecute.getNeedValue());
                        }
                        else if (!operExecute.getHaveReturnValue() && needValue.checkZero())
                        {
                            needValue.add(operExecute.getNeedValue());
                        }
                        else if (!operExecute.getHaveReturnValue() && !needValue.checkZero())
                        {
                            clearStack();
                            throw new Exception("Operator Error: Lack of parameter.");
                        }
                        if (needValue.checkZero())
                        {
                            newSantance = true;
                        }
                    }
                    else if (temp.isFunction(this))
                    {
                        List<String> funcBody = getValue(temp.getLiteral()).getListValue();
                        //System.out.println("Output From CmdExecute: isFunction");
                        Data d1 = new Data(funcBody.get(0));
                        List<String> parameterList = d1.getListValue();
                        if (temp.getHaveReturnValue(this))
                        {
                            needValue.minusOne();
                            needValue.add(parameterList.size());
                        }
                        else if (!temp.getHaveReturnValue(this) && needValue.checkZero())
                        {
                            needValue.add(parameterList.size());
                        }
                        else if (!temp.getHaveReturnValue(this) && !needValue.checkZero())
                        {
                            clearStack();
                            throw new Exception("Operator Error: Lack of parameter.");
                        }
                        if (needValue.checkZero())
                        {
                            newSantance = true;
                        }
                    }
                    else
                    {
                        if (!needValue.checkZero() && hasOp)
                        {
                            needValue.minusOne();
                        }
                        if (needValue.checkZero())
                        {
                            newSantance = true;
                        }
                    }
                    cmdSetStack.push(temp);
                    tempCmdSet.remove(0);
                    if (newSantance)
                    {
                        //System.out.println("Output From CmdExecute: newSantance: ");
                        break;
                    }
                }
            }
            if (dealOneIntruction(callSpace))
            {
                break;
            }
        }
    }

    private boolean dealOneIntruction(CmdExecute callSpace) throws Exception
    {
        while (!cmdSetStack.empty())
        {
            Data nowCmd = cmdSetStack.pop();
            //System.out.println("now CMD:" + nowCmd.getLiteral());
            if (nowCmd.getType() == OPER_TYPE)
            {
                OperExecute operExecute = operatorBind.getExecuteClass(nowCmd.getLiteral());
                if (operExecute instanceof StopExecute)
                {
                    return true;
                }
                else if (operExecute instanceof OutputExecute)
                {
                    ((OutputExecute) operExecute).getFatherSpace(callSpace);
                    operExecute.execute(nowCmd);
                }
                else
                {
                    if (operExecute != null)
                    {
                        try
                        {
                            operExecute.execute(nowCmd);
                        }

                        catch (Exception e)
                        {
                            System.out.println(e.getMessage());
                            return true;
                        }
                    }
                    else
                    {
                        clearStack();
                        throw new Exception("CmdExecute Error: Illegal operator.");
                    }
                }
            }
            else if (nowCmd.isFunction(this))
            {
                List<String> funcBody = getValue(nowCmd.getLiteral()).getListValue();
                Data d1 = new Data(funcBody.get(0));
                Data d2 = new Data(funcBody.get(1));
                List<String> parameterList = d1.getListValue();
                List<String> codeList = d2.getListValue();
                CmdExecute newFunc = new CmdExecute();
                int paraNum = parameterList.size();
                List<Data> getDataSet = this.popExecuteStack(paraNum);
                if (getDataSet.size() == paraNum)
                {
                    for (String key : getGlobalCmdExecute().mainNameSpace.keySet())
                    {
                        //                        System.out.println(i + "th name: " + key);
                        //                        System.out.println(i + "th value: " + getGlobalCmdExecute().mainNameSpace.get(key));
                        newFunc.addOneBind(key, getGlobalCmdExecute().mainNameSpace.get(key));
                    }
                    for (int i = 0; i < paraNum; i++)
                    {
                        newFunc.addOneBind(parameterList.get(i), getDataSet.get(i));
                    }
                    newFunc.getCmdSet(codeList, this);
                }
                else
                {
                    clearStack();
                    throw new Exception("Function Error: Lack of parameter.");
                }
            }
            else
            {
                //System.out.println("Output From CmdExecute: pushToExecuteStack ");
                pushToExecuteStack(nowCmd);
            }
        }
        clearStack();
        return false;
    }

    private void clearStack()
    {
        while (!executeStack.empty())
        {
            executeStack.pop();
        }

        while (!cmdSetStack.empty())
        {
            cmdSetStack.pop();
        }
    }

    public void pushToExecuteStack(Data item)
    {
        executeStack.push(item);
    }

    public List<Data> popExecuteStack(int num)
    {
        List<Data> ret = new ArrayList<>();
        for (int i = 0; i < num; i++)
        {
            if (!executeStack.empty())
            {
                ret.add(executeStack.pop());
            }
        }
        return ret;
    }

    public void pushToCmdStack(Data item)
    {
        cmdSetStack.push(item);
    }

    public void addOneBind(String key, Data value)
    {
        mainNameSpace.put(key, value);
    }

    public Data deleteOneBind(String key)
    {
        return mainNameSpace.remove(key);
    }

    public Data getValue(String key)
    {
        return mainNameSpace.getOrDefault(key, null);
    }

    public void deleteAllBind()
    {
        for (Iterator<Map.Entry<String, Data>> it = mainNameSpace.entrySet().iterator(); it.hasNext(); )
        {
            Map.Entry<String, Data> item = it.next();
            it.remove();
        }
    }

    public void traverse()
    {
        int i = 0;
        for (String key : mainNameSpace.keySet())
        {
            System.out.println(i + "th name: " + key);
            System.out.println(i + "th value: " + mainNameSpace.get(key));
            i++;
        }
    }

    public void writeToFile() throws Exception
    {
        File file = new File("nameSpace_" + this.hashCode());
        if (!file.exists())
        {
            try
            {
                file.createNewFile();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        Writer writeStream = new FileWriter(file);
        for (String key : mainNameSpace.keySet())
        {
            writeStream.write(key + " ");
            writeStream.write(mainNameSpace.get(key).toString() + "\n");
        }
        writeStream.close();
    }

    public void loadFromFile() throws Exception
    {
        File file = new File("nameSpace_" + this.hashCode());
        if (!file.exists())
        {
            throw new Exception("Load Error: This namespace should be saved first.");
        }
        else
        {
            Reader readStream = new FileReader(file);
            char[] cbuf = new char[1024];
            readStream.read(cbuf);
            String allName = new String(cbuf);
            for (String s : allName.split("\n"))
            {
                if (s.contains(" "))
                {
                    String name = s.substring(0, s.indexOf(" "));
                    String value = s.substring(s.indexOf(" ") + 1);
                    this.addOneBind(name, new Data(value));
                }

            }
            readStream.close();
        }
    }

    public CmdExecute getCallSpace()
    {
        return this.callSpace;
    }
}
