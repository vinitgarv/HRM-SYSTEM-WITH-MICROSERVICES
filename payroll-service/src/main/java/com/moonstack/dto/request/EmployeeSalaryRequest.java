package com.moonstack.dto.request;

import com.moonstack.constant.Message;
import com.moonstack.exception.RequestFailedException;

public class EmployeeSalaryRequest
{
    private Double baseSalary;
    private Double allowances;
    private Double deductions;
    private Double taxAmounts;
    private String accountNumber;

    public void validate()
    {
        if (baseSalary <= 0.0)
        {
            throw new RequestFailedException(Message.BASE_SALARY+Message.TAB+Message.CANNOT+Message.TAB
                    +Message.BE+Message.TAB+Message.LESS+Message.TAB+Message.THAN+Message.TAB+Message.OR
                    +Message.TAB+Message.EQUAL+Message.TAB+Message.TO+Message.TAB+Message.ZERO+Message.DOT);
        }

        if (allowances < 0.0)
        {
            throw new RequestFailedException(Message.ALLOWANCES+Message.TAB+Message.CANNOT+Message.TAB
                    +Message.BE+Message.TAB+Message.LESS+Message.TAB+Message.THAN+Message.TAB+Message.OR
                    +Message.TAB+Message.EQUAL+Message.TAB+Message.TO+Message.TAB+Message.ZERO+Message.DOT);
        }

        if (deductions < 0.0)
        {
            throw new RequestFailedException(Message.DEDUCTIONS+Message.TAB+Message.CANNOT+Message.TAB
                    +Message.BE+Message.TAB+Message.LESS+Message.TAB+Message.THAN+Message.TAB+Message.OR
                    +Message.TAB+Message.EQUAL+Message.TAB+Message.TO+Message.TAB+Message.ZERO+Message.DOT);
        }

        if (taxAmounts < 0.0)
        {
            throw new RequestFailedException(Message.TAX_AMOUNT+Message.TAB+Message.CANNOT+Message.TAB
                    +Message.BE+Message.TAB+Message.LESS+Message.TAB+Message.THAN+Message.TAB+Message.OR
                    +Message.TAB+Message.EQUAL+Message.TAB+Message.TO+Message.TAB+Message.ZERO+Message.DOT);
        }

        if (accountNumber.isEmpty())
        {
            throw new RequestFailedException(Message.ACCOUNT_NUMBER+Message.TAB+Message.CANNOT+Message.TAB
                    +Message.BE+Message.TAB+Message.LESS+Message.TAB+Message.THAN+Message.TAB+Message.OR
                    +Message.TAB+Message.EQUAL+Message.TAB+Message.TO+Message.TAB+Message.ZERO+Message.DOT);
        }
    }

}
