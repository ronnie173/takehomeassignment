package xyz.appsian.googlebooksparser.di.components;

import javax.inject.Singleton;

import dagger.Component;
import xyz.appsian.googlebooksparser.activities.MainActivity;
import xyz.appsian.googlebooksparser.adapters.GoogleBooksApiAdapter;
import xyz.appsian.googlebooksparser.di.modules.NetworkModule;
import xyz.appsian.googlebooksparser.di.modules.ApplicationModule;

/**
 * Created by jeromeraymond on 7/29/17.
 */
@Singleton
@Component(modules = {ApplicationModule.class, NetworkModule.class})
public interface ApplicationComponent {
    /**
     * Inject.
     *
     * @param target the target
     */
    void inject(MainActivity target);

    /**
     * Inject application.
     *
     * @param googleBooksApiAdapter the google books api adapter
     */
    void injectApplication(GoogleBooksApiAdapter googleBooksApiAdapter);
}
