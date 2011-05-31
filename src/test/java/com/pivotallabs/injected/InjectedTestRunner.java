package com.pivotallabs.injected;

import android.app.Application;
import com.google.inject.Injector;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.junit.runners.model.InitializationError;
import roboguice.inject.ContextScope;

public class InjectedTestRunner extends RobolectricTestRunner {

    public InjectedTestRunner(Class<?> testClass) throws InitializationError {
        super(testClass);
    }

    @Override protected Application createApplication() {
        SampleRoboApplication application = (SampleRoboApplication)super.createApplication();
        application.setModule(new RobolectricSampleTestModule());
        return application;
    }

    @Override public void prepareTest(Object test) {
        SampleRoboApplication application = (SampleRoboApplication) Robolectric.application;

        //This project's application does not extend GuiceInjectableApplication therefore we need to enter the ContextScope manually.
        Injector injector = application.getInjector();
        ContextScope scope = injector.getInstance(ContextScope.class);
        scope.enter(application);

        injector.injectMembers(test);
    }
}
