package com.company.DetailCommand;

import com.company.CmdExecute;
import com.company.Data;
import com.company.OperExecute;

import java.util.List;

public class IsnameExecute extends OperExecute
{
    public IsnameExecute(CmdExecute cmdExecute)
    {
        super(cmdExecute);
        this.haveReturnValue = true;
        this.needValue = 1;
    }

    @Override
    public void execute(Data cmd) throws Exception
    {
        List<Data> getDataSet = cmdExecute.popExecuteStack(this.needValue);
        if (getDataSet.size() == this.needValue)
        {
            Data d = getDataSet.get(0);
            if (cmdExecute.getValue(d.getLiteral()) != null)
            {
                //System.out.println(true);
                cmdExecute.pushToExecuteStack(new Data("true"));
            }
            else
            {
                //System.out.println(false);
                cmdExecute.pushToExecuteStack(new Data("false"));
            }
        }
        else
        {
            throw new Exception("Operator Error: Lack of parameter.");
        }
    }
}