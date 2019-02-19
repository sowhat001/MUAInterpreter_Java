package com.company.DetailCommand;

import com.company.CmdExecute;
import com.company.Data;
import com.company.OperExecute;

import java.util.List;

import static com.company.Data.*;

public class IsemptyExecute extends OperExecute
{
    public IsemptyExecute(CmdExecute cmdExecute)
    {
        super(cmdExecute);
        this.haveReturnValue = true;
        this.needValue = 1;
    }

    @Override
    public void execute(Data nowCmd)throws Exception
    {
        List<Data> getDataSet = cmdExecute.popExecuteStack(this.needValue);
        if (getDataSet.size() == this.needValue)
        {
            Data d = getDataSet.get(0);
            if (d.getType() == LIST_TYPE)
            {
                if (d.getListValue().size() == 1 && d.getListValue().get(0).isEmpty() || d.getListValue().size() == 0)
                {
                    cmdExecute.pushToExecuteStack(new Data("true"));
                }
                else
                {
                    cmdExecute.pushToExecuteStack(new Data("false"));
                }
            }
            else
            {
                if (d.getLiteral().isEmpty())
                {
                    cmdExecute.pushToExecuteStack(new Data("true"));
                }
                else
                {
                    cmdExecute.pushToExecuteStack(new Data("false"));
                }
            }
        }
        else
        {
            throw new Exception("Operator Error: Lack of parameter.");
        }
    }
}
