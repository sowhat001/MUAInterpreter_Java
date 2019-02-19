package com.company.DetailCommand;

import com.company.CmdExecute;
import com.company.Data;
import com.company.OperExecute;

import java.util.List;

public class ThingExecute extends OperExecute
{
    public ThingExecute(CmdExecute cmdExecute)
    {
        super(cmdExecute);
        this.haveReturnValue = true;
        this.needValue = 1;
    }

    @Override
    public void execute(Data cmd) throws Exception
    {
        List<Data> getDataSet = cmdExecute.popExecuteStack(1);
        if (getDataSet.size() == 1)
        {
            Data d = getDataSet.get(0);
            if (cmdExecute.getValue(d.getLiteral()) != null)
            {
                cmdExecute.pushToExecuteStack(cmdExecute.getValue(d.getLiteral()));
            }
            else
            {
                throw new Exception("Namespace Error: This name is not in namespace");
            }
        }
        else
        {
            throw new Exception("Operator Error: Lack of parameter.");
        }
    }
}
