package com.company.DetailCommand;

import com.company.CmdExecute;
import com.company.Data;
import com.company.OperExecute;

import java.util.Scanner;

import static com.company.Parse.checkBracket;

public class ReadlistExecute extends OperExecute
{
    public ReadlistExecute(CmdExecute cmdExecute)
    {
        super(cmdExecute);
        this.haveReturnValue = true;
        this.needValue = 0;
    }

    @Override
    public void execute(Data cmd) throws Exception
    {
        Scanner read = new Scanner(System.in);
        StringBuffer listBuffer = new StringBuffer();
        String listString = "";
        while (read.hasNextLine())
        {
            listBuffer.append(read.nextLine() + "\n");
            if (checkBracket(listBuffer.toString()) == 0)
            {
                listString = listBuffer.toString().trim().replace("\n"," " ).replace("\t", " ");
                listBuffer.setLength(0);
                break;
            }
            else if(checkBracket(listBuffer.toString()) > 0)
            {
                System.out.print("...");
            }
            else
            {
                throw new Exception("List Error: Brackets are not matched");
            }
        }
        listString = "[" + listString + " ]";
        Data d = new Data(listString);
        cmdExecute.pushToExecuteStack(d);
    }
}
