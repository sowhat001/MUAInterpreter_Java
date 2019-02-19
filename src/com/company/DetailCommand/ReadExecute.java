package com.company.DetailCommand;

import com.company.CmdExecute;
import com.company.Data;
import com.company.OperExecute;

import java.util.Scanner;
import static com.company.Data.*;

public class ReadExecute extends OperExecute
{
    public ReadExecute(CmdExecute cmdExecute)
    {
        super(cmdExecute);
        this.haveReturnValue = true;
        this.needValue = 0;
    }

    @Override
    public void execute(Data cmd)
    {
        Scanner read = new Scanner(System.in);
        Data d = new Data(read.next());
        if (d.getType() == OPER_TYPE)
        {
            //System.out.println("From Read: isOperation");
            cmdExecute.pushToCmdStack(d);
        }
        else
        {
            //System.out.println("From Read: other");
            cmdExecute.pushToExecuteStack(d);
        }
    }
}
