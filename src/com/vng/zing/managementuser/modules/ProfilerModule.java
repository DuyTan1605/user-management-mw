/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.zing.managementuser.modules;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;
import com.vng.zing.dmp.common.interceptor.ApiProfiler;
import com.vng.zing.dmp.common.interceptor.ApiProfilerInterceptor;
import com.vng.zing.dmp.common.interceptor.ThreadProfiler;
import com.vng.zing.dmp.common.interceptor.ThreadProfilerInterceptor;
import com.vng.zing.managementuser.handlers.UserHandler;

/**
 *
 * @author tanhd
 */
public class ProfilerModule extends AbstractModule {

    @Override
    protected void configure() {
        ThreadProfilerInterceptor threadProfilerInterceptor = new ThreadProfilerInterceptor();
        ApiProfilerInterceptor apiProfilerInterceptor = new ApiProfilerInterceptor();

        bindInterceptor(Matchers.any(), Matchers.annotatedWith(ThreadProfiler.class), threadProfilerInterceptor);
        bindInterceptor(Matchers.any(), Matchers.annotatedWith(ApiProfiler.class), apiProfilerInterceptor);
    }

}
