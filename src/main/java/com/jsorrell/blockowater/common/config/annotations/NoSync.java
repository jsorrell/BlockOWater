package com.jsorrell.blockowater.common.config.annotations;

import java.lang.annotation.*;

/**
 * If annotation exists, this config will not be synced to the client upon
 * connection to a server.
 *
 * This is useful for configs that have no need to be the same between client
 * and server (or are purely client-sided).
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface NoSync {

}
