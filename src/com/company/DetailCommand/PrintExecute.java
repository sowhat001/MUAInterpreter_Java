package com.company.DetailCommand;

import com.company.CmdExecute;
import com.company.Data;
import com.company.OperExecute;

import java.util.List;

public class PrintExecute extends OperExecute
{
    public PrintExecute(CmdExecute cmdExecute)
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
            System.out.println(d.getLiteral());
        }
        else
        {
            throw new Exception("Operator Error: Lack of parameter.");
        }
    }
}