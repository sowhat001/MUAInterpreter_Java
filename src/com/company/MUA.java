package com.company;

import java.util.Scanner;

import static com.company.Parse.checkBracket;

public class MUA
{
    private static CmdExecute cmdExecute;

    public static void main(String[] args) throws Exception
    {
        cmdExecute = new CmdExecute();
        new OperatorBind(cmdExecute);
        Parse parse = new Parse();
        Scanner input = new Scanner(System.in);
        System.out.print(">>>");
        StringBuffer cmdBuffer = new StringBuffer();
        while (input.hasNextLine())
        {
            cmdBuffer.append(input.nextLine() + "\n");
            if (checkBracket(cmdBuffer.toString()) == 0)
            {
                String cmdString = cmdBuffer.toString();
                cmdBuffer.setLength(0);
                if (!cmdString.equals("exit"))
                {
                    try
                    {
                        cmdExecute.getCmdSet(parse.parseCmd(cmdString), getGlobalCmdExecute());
                    }

                    catch (Exception e)
                    {
                        cmdBuffer.setLength(0);
                        System.out.println(e.getMessage());
                        System.out.print(">>>");
                        continue;
                    }
                }
                else
                {
                    System.out.println("Goodbye");
                    break;
                }
                System.out.print(">>>");
            }
            else if(checkBracket(cmdBuffer.toString()) < 0)
            {
                System.out.println("List Error: Brackets are not matched");
                cmdBuffer.setLength(0);
                System.out.print(">>>");
            }
            else
            {
                System.out.print("...");
            }
        }
    }

    public static CmdExecute getGlobalCmdExecute()
    {
        return cmdExecute;
    }
}
