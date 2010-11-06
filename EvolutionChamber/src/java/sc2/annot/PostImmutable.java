package sc2.annot;

import java.lang.annotation.*;

/**
** Denotes an object which is immutable after a one-time post-initialisation.
*/
@Target(value={ElementType.TYPE})
@Documented
public @interface PostImmutable {

	/**
	** The post-initialisation method. It should either be strictly idempotent
	** or throw an error on subsequent runs.
	**
	** TODO better representation than just String, maybe add Class[] for
	** method signatures too...
	*/
	String post() default "";

}
