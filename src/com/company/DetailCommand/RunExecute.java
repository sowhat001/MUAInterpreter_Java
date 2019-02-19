package com.company.DetailCommand;

import com.company.*;

import java.util.List;

import static com.company.Data.*;

public class RunExecute extends OperExecute
{
    public RunExecute(CmdExecute cmdExecute)
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
            Data codeList = getDataSet.get(0);
            if (codeList.getType() == LIST_TYPE)
            {
                List<String> runcodes = codeList.getListValue();
                cmdExecute.getCmdSet(runcodes, cmdExecute.getCallSpace());
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
