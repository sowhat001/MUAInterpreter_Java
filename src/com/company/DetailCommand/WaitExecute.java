package com.company.DetailCommand;

import com.company.CmdExecute;
import com.company.Data;
import com.company.OperExecute;

import java.util.List;

import static com.company.Data.*;

public class WaitExecute extends OperExecute
{
    public WaitExecute(CmdExecute cmdExecute)
    {
        super(cmdExecute);
        this.haveReturnValue = false;
        this.needValue = 1;
    }

    @Override
    public void execute(Data cmd) throws Exception
    {
        List<Data> getDataSet = cmdExecute.popExecuteStack(this.needValue);
        if (getDataSet.size() == this.needValue)
        {
            Data d = getDataSet.get(0);
            if ((int)d.getNumberValue() < 0)
            {
                throw new Exception("Wait Error: Wait time illegal. ");
            }
            Thread.sleep((int)d.getNumberValue());
        }
        else
        {
            throw new Exception("Operator Error: Lack of parameter.");
        }
    }
}


