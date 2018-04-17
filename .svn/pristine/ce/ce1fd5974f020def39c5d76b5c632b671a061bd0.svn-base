package com.dome.sdkserver.biz.executor;

import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Executor
 *
 * @author Zhang ShanMin
 * @date 2016/7/11
 * @time 15:45
 */
@Component("executor")
public class Executor {

    @Resource(name = "taskExecutor")
    private TaskExecutor taskExecutor;

    public void executor(Runnable runnable) {
        taskExecutor.execute(runnable);
    }
}
