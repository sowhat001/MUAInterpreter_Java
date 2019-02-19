package com.company.DetailCommand;

import com.company.CmdExecute;
import com.company.Data;
import com.company.OperExecute;

import java.util.List;

import static com.company.Data.*;

public class OrExecute extends OperExecute
{
    public OrExecute(CmdExecute cmdExecute)
    {
        super(cmdExecute);
        this.haveReturnValue = true;
        this.needValue = 2;
    }

    @Override
    public void execute(Data cmd) throws Exception
    {
        List<Data> getDataSet = cmdExecute.popExecuteStack(this.needValue);
        if(getDataSet.size() == this.needValue)
        {
            Data d1 = getDataSet.get(0);
            Data d2 = getDataSet.get(1);
            if(d1.getType() != BOOL_TYPE || d2.getType() != BOOL_TYPE)
            {
                throw new Exception("Type Error: Illegal type.");
            }
            else
            {
                cmdExecute.pushToExecuteStack(new Data(d1.getBooleanValue() || d2.getBooleanValue()));
            }
        }
        else
        {
            throw new Exception("Operator Error: Lack of parameter.");
        }
    }
}
