package com.fray.evo.util;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EcCacheMap<E, T> implements Map<E, T>
{
	private static final long		serialVersionUID	= 1L;

	HashMap<E, SoftReference<T>>	map					= new HashMap<E, SoftReference<T>>();

	long							lastCleaned			= System.currentTimeMillis();

	public void clear()
	{
		map.clear();
	}

	private void cull()
	{
		long current = System.currentTimeMillis();
		if (current == lastCleaned)
			return;
		if (current < lastCleaned + 1000 * 60)
			return;
		for (E e : new ArrayList<E>(keySet()))
			if (getInner(e) == null)
				remove(e);
		lastCleaned = current;
	}

	public boolean containsKey(Object arg0)
	{
		return map.get(arg0) != null;
	}

	public boolean containsValue(Object arg0)
	{
		return map.containsValue(new WeakReference<T>((T) arg0));
	}

	public Set<java.util.Map.Entry<E, T>> entrySet()
	{
		HashMap<E, T> set = new HashMap<E, T>();
		for (Entry<E, SoftReference<T>> r : map.entrySet())
		{
			T value = r.getValue().get();
			if (value == null)
				continue;
			set.put(r.getKey(), value);
		}
		return set.entrySet();
	}

	public T get(Object arg0)
	{
		cull();
		return getInner(arg0);
	}

	private T getInner(Object arg0)
	{
		SoftReference<T> weakReference = map.get(arg0);
		if (weakReference != null)
			return weakReference.get();
		return null;
	}

	public boolean isEmpty()
	{
		for (SoftReference<T> t : map.values())
			if (t.get() != null)
				return false;
		return true;
	}

	public Set<E> keySet()
	{
		return map.keySet();
	}

	public T put(E arg0, T arg1)
	{
		cull();
		map.put(arg0, new SoftReference<T>(arg1));
		return arg1;
	}

	public void putAll(Map<? extends E, ? extends T> arg0)
	{
		for (E e : arg0.keySet())
			put(e, arg0.get(e));
	}

	public T remove(Object arg0)
	{
		return map.remove(arg0).get();
	}

	public int size()
	{
		int i = 0;
		for (SoftReference<T> t : map.values())
			if (t.get() != null)
				i++;
		return i;
	}

	public Collection<T> values()
	{
		List<T> results = new ArrayList<T>();
		for (SoftReference<T> t : map.values())
		{
			T t2 = t.get();
			if (t2 != null)
				results.add(t2);
		}
		return results;
	}

}
