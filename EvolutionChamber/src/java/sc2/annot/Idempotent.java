package sc2.annot;

import java.lang.annotation.*;

/**
** Denotes a method which is idempotent, i.e. multiple executions (with the
** same arguments, with the same states) have the same effect as one execution.
*/
@Target(value={ElementType.METHOD})
@Documented
public @interface Idempotent {

}
