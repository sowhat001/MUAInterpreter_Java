package com.company.DetailCommand;

import com.company.CmdExecute;
import com.company.Data;
import com.company.OperExecute;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.company.Data.*;

public class ButlastExecute extends OperExecute
{
    public ButlastExecute(CmdExecute cmdExecute)
    {
        super(cmdExecute);
        this.haveReturnValue = true;
        this.needValue = 1;
    }

    @Override
    public void execute(Data cmd) throws Exception
    {
        List<Data> getDataSet = cmdExecute.popExecuteStack(this.needValue);
        if (getDataSet.size() == this.needValue)
        {
            Data d = getDataSet.get(0);
            Data newData;
            if (d.getType() == LIST_TYPE)
            {
                List<String> newListValue = new ArrayList<>(d.getListValue());
                if (newListValue.size() > 0)
                {
                    newListValue.remove(newListValue.size() - 1);
                }
                newData = new Data(newListValue);
            }
            else
            {
                if (d.toString().length() > 0)
                {
                    newData = new Data(d.toString().substring(0, d.toString().length() - 1));
                }
                else
                {
                    newData = new Data("");
                }
            }
            cmdExecute.pushToExecuteStack(newData);
        }
        else
        {
            throw new Exception("Operator Error: Lack of parameter.");
        }
    }
}
