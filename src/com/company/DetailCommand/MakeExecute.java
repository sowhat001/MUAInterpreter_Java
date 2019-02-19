package com.company.DetailCommand;

import com.company.*;

import java.util.List;

import static com.company.OperatorBind.addOneOperBind;

public class MakeExecute extends OperExecute
{
    public MakeExecute(CmdExecute cmdExecute)
    {
        super(cmdExecute);
        this.haveReturnValue = false;
        this.needValue = 2;
    }

    @Override
    public void execute(Data cmd)throws Exception
    {
        List<Data> getDataSet = cmdExecute.popExecuteStack(this.needValue);
        if (getDataSet.size() == this.needValue)
        {
            Data key = getDataSet.get(0);
            Data value = getDataSet.get(1);
            if (checkLegalName(key))
            {
                cmdExecute.addOneBind(key.getLiteral(), value);
                addOneOperBind(key.getLiteral());
            }
            else
            {
                throw new Exception("Make Error: This name is illegal.");
            }
        }
        else
        {
            throw new Exception("Operator Error: Lack of parameter.");
        }
    }

    private boolean checkLegalName(Data key)
    {
        String stringKey = key.getLiteral();
        if (stringKey.equals(""))
        {
            return false;
        }
        else
        {
            if (!Character.isLowerCase(stringKey.charAt(0)) && !Character.isUpperCase(stringKey.charAt(0)))
            {
                return false;
            }
        }
        return true;
    }
}
