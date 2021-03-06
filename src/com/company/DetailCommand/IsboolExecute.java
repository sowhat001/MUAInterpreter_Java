package com.company.DetailCommand;

import com.company.CmdExecute;
import com.company.Data;
import com.company.OperExecute;

public class IsboolExecute extends OperExecute
{
    public IsboolExecute(CmdExecute cmdExecute)
    {
        super(cmdExecute);
        this.haveReturnValue = true;
        this.needValue = 1;
    }

    @Override
    public void execute(Data nowCmd) throws Exception
    {
        JudgeType j = new JudgeType(cmdExecute);
        j.execute(nowCmd);
    }
}
