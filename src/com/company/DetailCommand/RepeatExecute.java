package com.company.DetailCommand;

import com.company.*;

import java.util.List;

import static com.company.Data.*;

public class RepeatExecute extends OperExecute
{
    public RepeatExecute(CmdExecute cmdExecute)
    {
        super(cmdExecute);
        this.haveReturnValue = false;
        this.needValue = 2;
    }

    @Override
    public void execute(Data cmd) throws Exception
    {
        List<Data> getDataSet = cmdExecute.popExecuteStack(this.needValue);
        if (getDataSet.size() == this.needValue)
        {
            Data times = getDataSet.get(0);
            Data codeList = getDataSet.get(1);
            if (times.getType() == NUM_TYPE && codeList.getType() == LIST_TYPE)
            {
                int runtimes = (int)times.getNumberValue();
                List<String> runcodes = codeList.getListValue();
                //System.out.println("Output From RepeatExecute: runtimes: " + runtimes);
                if (runtimes > 0)
                {
                    for (int i = 0; i < runtimes; i++)
                    {
                        cmdExecute.getCmdSet(runcodes, cmdExecute.getCallSpace());
                        //System.out.println("Output From RepeatExecute: ith: " + i);
                    }
                }
                else
                {
                    throw new Exception("Parameter Error: Illegal Parameter.");
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
