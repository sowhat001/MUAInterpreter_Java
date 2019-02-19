package com.company.DetailCommand;

import com.company.CmdExecute;
import com.company.Data;
import com.company.OperExecute;

import java.util.ArrayList;
import java.util.List;

import static com.company.Data.LIST_TYPE;

public class ListExecute extends OperExecute
{
    public ListExecute(CmdExecute cmdExecute)
    {
        super(cmdExecute);
        this.haveReturnValue = true;
        this.needValue = 2;
    }

    @Override
    public void execute(Data cmd) throws Exception
    {
        List<Data> getDataSet = cmdExecute.popExecuteStack(this.needValue);
        if (getDataSet.size() == this.needValue)
        {
            Data d1 = getDataSet.get(0);
            Data d2 = getDataSet.get(1);
            List<String> newList = new ArrayList<>();
            newList.add(d1.toString());
            newList.add(d2.toString());
            cmdExecute.pushToExecuteStack(new Data(newList));
        }
        else
        {
            throw new Exception("Operator Error: Lack of parameter.");
        }
    }
}