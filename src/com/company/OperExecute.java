package com.company;

public class OperExecute
{
    protected CmdExecute cmdExecute;
    protected boolean haveReturnValue;
    protected int needValue;
    public OperExecute(CmdExecute cmdExecute)
    {
        this.cmdExecute = cmdExecute;
    }
    public void execute(Data nowCmd) throws Exception
    {
    }

    public int getNeedValue()
    {
        return this.needValue;
    }

    public boolean getHaveReturnValue()
    {
        return this.haveReturnValue;
    }
}
