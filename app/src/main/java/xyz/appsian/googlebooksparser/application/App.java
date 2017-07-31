package xyz.appsian.googlebooksparser.application;

import android.app.Application;

import xyz.appsian.googlebooksparser.di.components.ApplicationComponent;
import xyz.appsian.googlebooksparser.di.components.DaggerApplicationComponent;
import xyz.appsian.googlebooksparser.di.modules.NetworkModule;
import xyz.appsian.googlebooksparser.di.modules.ApplicationModule;

/**
 * Created by jeromeraymond on 7/29/17.
 */
public class App extends Application {

    private ApplicationComponent component;


    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .networkModule(new NetworkModule())
                .build();

    }

    /**
     * Gets component.
     *
     * @return the component
     */
    public ApplicationComponent getComponent() {
        return component;
    }

    /**
     * Gets application.
     *
     * @return the application
     */
    public Application getApplication()
    {
        return  this;
    }
}
