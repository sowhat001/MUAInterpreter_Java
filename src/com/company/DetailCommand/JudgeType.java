package com.company.DetailCommand;

import com.company.CmdExecute;
import com.company.Data;
import com.company.OperExecute;

import java.util.List;

import static com.company.OperatorBind.typeToNum;

public class JudgeType extends OperExecute
{
    public JudgeType(CmdExecute cmdExecute)
    {
        super(cmdExecute);
        this.haveReturnValue = true;
        this.needValue = 1;
    }

    @Override
    public void execute(Data nowCmd) throws Exception
    {
        List<Data> getDataSet = cmdExecute.popExecuteStack(1);
        if (getDataSet.size() == 1)
        {
            Data d = getDataSet.get(0);
            String judge = nowCmd.getLiteral().substring(2).toLowerCase();
            //System.out.println("Output From JudgeType: now type is: " + judge);
            if (d.getType() == typeToNum(judge) || (typeToNum(judge) == 1 && d.isWord()))
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
            throw new Exception("Operator Error: Lack of parameter.");
        }
    }
}
