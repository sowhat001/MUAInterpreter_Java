package com.company.DetailCommand;

import com.company.CmdExecute;
import com.company.Data;
import com.company.OperExecute;

public class LoadExecute extends OperExecute
{
    public LoadExecute(CmdExecute cmdExecute)
    {
        super(cmdExecute);
        this.haveReturnValue = false;
        this.needValue = 0;
    }

    @Override
    public void execute(Data cmd) throws Exception
    {
        cmdExecute.loadFromFile();
    }
}