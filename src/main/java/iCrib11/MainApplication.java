package iCrib11;

import Firebase.FirebaseInitialize;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/")
public class MainApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
    	System.out.println("=======================================================0");

    	
    	FirebaseInitialize.initialize();
        Set<Class<?>> set = new HashSet<>();
        set.add( CribsController.class);
        set.add( UsersController.class);
        
        return set;
    }
}