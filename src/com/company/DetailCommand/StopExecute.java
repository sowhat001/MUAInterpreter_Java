package com.company.DetailCommand;

import com.company.CmdExecute;
import com.company.Data;
import com.company.OperExecute;

public class StopExecute extends OperExecute
{
    public StopExecute(CmdExecute cmdExecute)
    {
        super(cmdExecute);
        this.haveReturnValue = false;
        this.needValue = 0;
    }

    @Override
    public void execute(Data cmd)
    {

    }
}