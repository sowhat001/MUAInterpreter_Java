package com.company;

import java.util.ArrayList;
import java.util.List;

public class MyArray
{
    private List<Integer> content;

    public MyArray()
    {
        content = new ArrayList<>();
    }

    public void add(int num)
    {
        content.add(num);
    }

    public void minusOne()
    {
        for(int i = content.size() - 1; i >= 0 ;i--)
        {
            int nowValue = content.get(i);
            if (nowValue > 0)
            {
                content.set(i, nowValue - 1);
                break;
            }
        }
    }

    public boolean checkZero()
    {
        for (int i : this.content)
        {
            if (i != 0)
            {
                return false;
            }
        }
        return true;
    }
}