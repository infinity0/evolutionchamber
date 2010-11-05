package sc2.annot;

import java.lang.annotation.*;

/**
** Denotes an immutable object.
*/
@Target(value={ElementType.TYPE})
@Documented
public @interface Immutable {

}
