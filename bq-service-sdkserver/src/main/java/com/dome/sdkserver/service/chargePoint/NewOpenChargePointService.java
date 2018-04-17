package com.dome.sdkserver.service.chargePoint;

import com.dome.sdkserver.metadata.entity.bq.pay.NewOpenChargePoint;

public interface NewOpenChargePointService {
    boolean synNewOpenChargePoint(NewOpenChargePoint charge);
}
