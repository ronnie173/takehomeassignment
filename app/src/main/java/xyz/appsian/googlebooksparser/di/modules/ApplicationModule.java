package xyz.appsian.googlebooksparser.di.modules;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jeromeraymond on 7/29/17.
 */
@Module
public class ApplicationModule {
    private Application application;

    /**
     * Instantiates a new Application module.
     *
     * @param application the application
     */
    public ApplicationModule(Application application) {
        this.application = application;
    }

    /**
     * Provide application context application.
     *
     * @return the application
     */
    @Singleton
    @Provides
    public Application provideApplicationContext()
    {
        return application;
    }

}
