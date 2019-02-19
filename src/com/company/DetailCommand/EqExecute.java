package com.company.DetailCommand;

import com.company.CmdExecute;
import com.company.Data;
import com.company.OperExecute;

import java.util.List;

import static com.company.Data.*;

public class EqExecute extends OperExecute
{
    public EqExecute(CmdExecute cmdExecute)
    {
        super(cmdExecute);
        this.haveReturnValue = true;
        this.needValue = 2;
    }

    @Override
    public void execute(Data cmd) throws Exception
    {
        List<Data> getDataSet = cmdExecute.popExecuteStack(this.needValue);
        if (getDataSet.size() == this.needValue)
        {
            Data d1 = getDataSet.get(0);
            Data d2 = getDataSet.get(1);
            if (d1.getType() == LIST_TYPE || d2.getType() == LIST_TYPE)
            {
                throw new Exception("Type Error: Illegal type.");
            }
            else
            {
                if (d1.getType() == NUM_TYPE && d2.getType() == NUM_TYPE)
                {
                    if (d1.getNumberValue() == d2.getNumberValue())
                    {
                        cmdExecute.pushToExecuteStack(new Data("true"));
                    }
                    else
                    {
                        cmdExecute.pushToExecuteStack(new Data("false"));
                    }
                }
                else
                {
                    if (d1.getLiteral().compareTo(d2.getLiteral()) == 0)
                    {
                        cmdExecute.pushToExecuteStack(new Data("true"));
                    }
                    else
                    {
                        cmdExecute.pushToExecuteStack(new Data("false"));
                    }
                }
            }
        }
        else
        {
            throw new Exception("Operator Error: Lack of parameter.");
        }
    }
}