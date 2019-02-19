package com.company.DetailCommand;

import com.company.CmdExecute;
import com.company.Data;
import com.company.OperExecute;

import java.util.List;

import static com.company.MUA.getGlobalCmdExecute;

public class OutputExecute extends OperExecute
{
    private CmdExecute fatherSpace;
    private boolean hasReturned;

    public OutputExecute(CmdExecute cmdExecute)
    {
        super(cmdExecute);
        this.haveReturnValue = true;
        this.needValue = 1;
        this.hasReturned = false;
    }

    @Override
    public void execute(Data cmd) throws Exception
    {
        List<Data> getDataSet = cmdExecute.popExecuteStack(this.needValue);
        if (getDataSet.size() == this.needValue)
        {
            Data d = getDataSet.get(0);
            if (cmdExecute != getGlobalCmdExecute())
            {
                cmdExecute.pushToExecuteStack(d);
                if (fatherSpace == null)
                {
                    throw new Exception("Fatal Error: No father namespace!!\n");
                }
                if(hasReturned == true)
                {
                    fatherSpace.popExecuteStack(1);
                }
                //System.out.println("Output:" + fatherSpace);
                fatherSpace.pushToExecuteStack(d);
                hasReturned = true;
            }
            else
            {
                cmdExecute.pushToExecuteStack(d);
            }
        }
        else
        {
            throw new Exception("Operator Error: Lack of parameter.");
        }
    }

    public void getFatherSpace(CmdExecute fatherSpace)
    {
        this.fatherSpace = fatherSpace;
    }
}
