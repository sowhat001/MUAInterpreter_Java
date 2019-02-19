package com.company.DetailCommand;

import com.company.CmdExecute;
import com.company.Data;
import com.company.OperExecute;

import java.util.List;

import static com.company.MUA.getGlobalCmdExecute;

public class ExportExecute extends OperExecute
{
    public ExportExecute(CmdExecute cmdExecute)
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
            if(cmdExecute.getValue(d.getLiteral()) != null)
            {
                CmdExecute global = getGlobalCmdExecute();
                global.addOneBind(d.getLiteral(), cmdExecute.getValue(d.getLiteral()));
            }
            else
            {
                throw new Exception("Namespace Error: This name is not in namespace");
            }
        }
        else
        {
            throw new Exception("Operator Error: Lack of parameter.");
        }
    }
}