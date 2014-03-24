package uk.ac.ncl.cs.groupproject.services;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import uk.ac.ncl.cs.groupproject.communication.LogManager;

/**
 * @Auther: Li Zequn
 * Date: 18/03/14
 */

@Component
public class Bootstrap implements ApplicationListener<ContextRefreshedEvent> {
    private Logger log = Logger.getLogger(Bootstrap.class);
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        log.info("bootstrap run");
        LogManager.recover();
        //LogManager.beginTimer();
    }
}