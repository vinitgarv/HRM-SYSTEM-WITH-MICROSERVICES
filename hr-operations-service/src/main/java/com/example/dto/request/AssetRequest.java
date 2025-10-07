package com.example.dto.request;

import com.example.constants.Message;
import com.example.exception.RequestFailedException;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssetRequest
{
    private String assetTag;
    private String assetType;
    private String brand;
    private String model;
    private String serialNumber;

    private LocalDate purchaseDate;
    private Double purchaseCost;
    private String vendorName;

    private String condition; // new, good, damaged, lost

    private String location;
    private String warranty;
    private String notes;


    public void validate()
    {
        if (assetTag == null || assetTag.trim().isEmpty())
        {
            throw new RequestFailedException(Message.ASSET_TAG + Message.TAB + Message.IS + Message.TAB + Message.EMPTY+Message.DOT);
        }

        if (assetTag.length() < 3)
        {
            throw new RequestFailedException(Message.ASSET_TAG + Message.TAB + Message.IS + Message.TAB +
                    Message.TOO + Message.TAB + Message.SHORT + Message.DOT);
        }

        if (assetType == null || assetType.trim().isEmpty())
        {
            throw new RequestFailedException(Message.ASSET_TYPE + Message.TAB + Message.IS + Message.TAB + Message.EMPTY+Message.DOT);
        }

        if (brand == null || brand.trim().isEmpty())
        {
            throw new RequestFailedException(Message.BRAND + Message.TAB + Message.IS + Message.TAB + Message.EMPTY+Message.DOT);
        }

        if (model == null || model.trim().isEmpty())
        {
            throw new RequestFailedException(Message.MODEL + Message.TAB + Message.IS + Message.TAB + Message.EMPTY+Message.DOT);
        }

        if (serialNumber == null || serialNumber.trim().isEmpty())
        {
            throw new RequestFailedException(Message.SERIAL_NUMBER + Message.TAB + Message.IS + Message.TAB + Message.EMPTY+Message.DOT);
        }

        if (purchaseDate == null)
        {
            throw new RequestFailedException(Message.PURCHASE_DATE + Message.TAB + Message.IS + Message.TAB + Message.EMPTY+Message.DOT);
        }

        if (purchaseDate.isAfter(LocalDate.now()))
        {
            throw new RequestFailedException(Message.PURCHASE_DATE + Message.TAB + Message.IS + Message.TAB + Message.INVALID+Message.DOT);
        }

        if (vendorName == null || vendorName.trim().isEmpty())
        {
            throw new RequestFailedException(Message.VENDOR_NAME + Message.TAB + Message.IS + Message.TAB + Message.EMPTY+Message.DOT);
        }

        if (purchaseCost == null || purchaseCost <= 0)
        {
            throw new RequestFailedException(Message.PURCHASE_COST + Message.TAB + Message.IS + Message.TAB + Message.INVALID+Message.DOT);
        }

        if (condition == null || condition.trim().isEmpty())
        {
            throw new RequestFailedException(Message.CONDITION + Message.TAB + Message.IS + Message.TAB + Message.EMPTY+Message.DOT);
        }

        if (location == null || location.trim().isEmpty())
        {
            throw new RequestFailedException(Message.LOCATION + Message.TAB + Message.IS + Message.TAB + Message.EMPTY+Message.DOT);
        }

        if (warranty == null || warranty.trim().isEmpty())
        {
            throw new RequestFailedException(Message.WARRANTY + Message.TAB + Message.IS + Message.TAB + Message.EMPTY+Message.DOT);
        }

        if (notes == null || notes.trim().isEmpty())
        {
            throw new RequestFailedException(Message.NOTES + Message.TAB + Message.IS + Message.TAB + Message.EMPTY+Message.DOT);
        }

    }
}
