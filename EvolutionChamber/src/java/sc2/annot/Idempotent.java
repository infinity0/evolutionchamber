package sc2.annot;

import java.lang.annotation.*;

/**
** Denotes a method which is idempotent, i.e. multiple executions have the same
** effect as one execution.
*/
@Target(value={ElementType.METHOD})
@Documented
public @interface Idempotent {

	/**
	** Whether the idempotency depends on the arguments being the same (and
	** having the same internal state) across multiple calls.
	*/
	boolean arg() default false;

}
