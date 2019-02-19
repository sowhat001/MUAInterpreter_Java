package com.company.DetailCommand;

import com.company.CmdExecute;
import com.company.Data;
import com.company.OperExecute;

import java.util.List;

import static com.company.Data.*;

public class ModExecute extends OperExecute
{
    public ModExecute(CmdExecute cmdExecute)
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
            if (d1.getType() == NUM_TYPE && d2.getType() == NUM_TYPE)
            {
                int num1 = (int)d1.getNumberValue();
                int num2 = (int)d2.getNumberValue();
                if (num2 == 0)
                {
                    throw new Exception("Divide Zero Error: Cannot divide by zero.");
                }
                else
                {
                    cmdExecute.pushToExecuteStack(new Data(num1 % num2));
                }
            }
            else
            {
                throw new Exception("Type Error: Illegal type.");
            }
        }
        else
        {
            throw new Exception("Operator Error: Lack of parameter.");
        }
    }
}

