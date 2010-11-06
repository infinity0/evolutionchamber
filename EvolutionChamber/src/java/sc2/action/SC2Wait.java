package sc2.action;

/**
** An action that does nothing.
**
** {@link sc2.SC2BuildOrderExecutor} gives special semantics to this class,
** taking it to mean "wait until the next action can succeed".
*/
public class SC2Wait extends SC2Action {

	@Override protected void launch() { }

}
