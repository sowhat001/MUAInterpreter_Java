package com.company.DetailCommand;

import com.company.CmdExecute;
import com.company.Data;
import com.company.OperExecute;

import java.util.List;
import java.util.Random;

import static com.company.Data.*;

public class RandomExecute extends OperExecute
{
    public RandomExecute(CmdExecute cmdExecute)
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
            if (d.getType() != NUM_TYPE)
            {
                throw new Exception("Type Error: Illegal type.");
            }
            else
            {
                cmdExecute.pushToExecuteStack(new Data(d.getNumberValue() * new Random().nextDouble()));
            }
        }
        else
        {
            throw new Exception("Operator Error: Lack of parameter.");
        }
    }
}


