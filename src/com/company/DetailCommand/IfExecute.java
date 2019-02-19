package com.company.DetailCommand;

import com.company.CmdExecute;
import com.company.Data;
import com.company.OperExecute;

import java.util.List;

import static com.company.Data.*;

public class IfExecute extends OperExecute
{
    public IfExecute(CmdExecute cmdExecute)
    {
        super(cmdExecute);
        this.haveReturnValue = false;
        this.needValue = 3;
    }

    @Override
    public void execute(Data cmd) throws Exception
    {
        List<Data> getDataSet = cmdExecute.popExecuteStack(this.needValue);
        //System.out.println("If: datasize" + getDataSet.size());
        if (getDataSet.size() == this.needValue)
        {
            Data d1 = getDataSet.get(0);
            Data d2 = getDataSet.get(1);
            Data d3 = getDataSet.get(2);
            if (d1.getType() == BOOL_TYPE && d2.getType() == LIST_TYPE && d3.getType() == LIST_TYPE)
            {
                if(d1.getBooleanValue())
                {
                    cmdExecute.getCmdSet(d2.getListValue(), cmdExecute.getCallSpace());
                }
                else
                {
                    cmdExecute.getCmdSet(d3.getListValue(), cmdExecute.getCallSpace());
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
