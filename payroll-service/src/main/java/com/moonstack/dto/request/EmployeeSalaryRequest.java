package com.moonstack.dto.request;

import com.moonstack.constant.Message;
import com.moonstack.exception.RequestFailedException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.regex.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeSalaryRequest
{
    private Double baseSalary;
    private Double allowances;
    private Double deductions;
    private Double taxAmounts;

    private String bankName;
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

        if (bankName.isEmpty() || bankName == null)
        {
            throw new RequestFailedException(Message.BANK_NAME+Message.TAB+Message.CANNOT+Message.TAB
                    +Message.BE+Message.TAB+Message.EMPTY+Message.DOT);
        }

        if (accountNumber.isEmpty() || accountNumber == null)
        {
            throw new RequestFailedException(Message.ACCOUNT_NUMBER+Message.TAB+Message.CANNOT+Message.TAB
                    +Message.BE+Message.TAB+Message.LESS+Message.TAB+Message.THAN+Message.TAB+Message.OR
                    +Message.TAB+Message.EQUAL+Message.TAB+Message.TO+Message.TAB+Message.ZERO+Message.DOT);
        }


        String accNoRegex = "^[0-9]{9,18}$";
        boolean isAccNoValid = Pattern.matches(accNoRegex,accountNumber);

        if (!isAccNoValid)
        {
            throw new RequestFailedException(Message.INVALID+Message.TAB+Message.ACCOUNT_NUMBER+Message.DOT);
        }
    }

}
