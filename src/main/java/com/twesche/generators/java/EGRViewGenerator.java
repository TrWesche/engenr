package com.twesche.generators.java;

import com.twesche.enums.EGRGenerate;
import com.twesche.model.EGRGenerateStatus;
import com.twesche.model.EGRTypeData;

public class EGRViewGenerator {

    public EGRGenerateStatus generate(EGRTypeData data)
    {
        System.out.println("EGRViewGenerator Called");
        EGRGenerateStatus generateStatus = new EGRGenerateStatus();
        generateStatus.setDataSource(data);

        if (data.getGenerate().contains(EGRGenerate.VIEW))
        {
            generateStatus.setStepSuccess(true);
        }

        return generateStatus;
    }
}
