package com.dome.sdkserver.service.game;

import com.dome.sdkserver.metadata.entity.bq.pay.AppInfoEntity;

public interface NewOpenGameInfoService {

    boolean synNewOpenAppInfo(AppInfoEntity entity);

    boolean syncOgpParams(AppInfoEntity appInfoEntity);
}
