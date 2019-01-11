package ltd.qcwifi.protocal.spi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.ServiceConfigurationError;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import ltd.qcwifi.protocal.factory.SpiExtensionFactory;

public final class ExtensionLoader<S> implements Iterable<S> {

	private static final String PREFIX = "META-INF/qcwifi_services/";

	// The class or interface representing the service being loaded
	private Class<S> service;

	// The class loader used to locate, load, and instantiate
	// EXTENSION_INSTANCES
	private ClassLoader loader;

	// Cached EXTENSION_INSTANCES, in instantiation order
	private final ConcurrentMap<String, Object> EXTENSION_INSTANCES = SpiExtensionFactory.EXTENSION_INSTANCES;

	private final ConcurrentMap<String, Object> localExtensionInstances = new ConcurrentHashMap<>();

	private final ConcurrentMap<String, Object> SERVICE_URL = SpiExtensionFactory.SERVICE_URL;
	private final ConcurrentMap<String, Object> localServiceUrl = new ConcurrentHashMap<>();

	// The current lazy-lookup iterator
	private LazyIterator lookupIterator;

	/**
	 * Clear this loader's provider cache so that all EXTENSION_INSTANCES will
	 * be reloaded.
	 * 
	 * <p>
	 * After invoking this method, subsequent invocations of the
	 * {@link #iterator() iterator} method will lazily look up and instantiate
	 * EXTENSION_INSTANCES from scratch, just as is done by a newly-created
	 * loader.
	 * 
	 * <p>
	 * This method is intended for use in situations in which new
	 * EXTENSION_INSTANCES can be installed into a running Java virtual machine.
	 */
	public void reload() {
		for (String key : localExtensionInstances.keySet()) {
			EXTENSION_INSTANCES.remove(key);
		}
		localExtensionInstances.clear();
		for (String key : localServiceUrl.keySet()) {
			SERVICE_URL.remove(key);
		}
		localServiceUrl.clear();
		lookupIterator = new LazyIterator(service, loader);
		while (lookupIterator.hasNext()) {
			lookupIterator.next();
		}
	}

	private ExtensionLoader(Class<S> svc, ClassLoader cl) {
		service = svc;
		loader = cl;
		reload();
	}

	private static void fail(Class service, String msg, Throwable cause) throws ServiceConfigurationError {
		throw new ServiceConfigurationError(service.getName() + ": " + msg, cause);
	}

	private static void fail(Class service, String msg) throws ServiceConfigurationError {
		throw new ServiceConfigurationError(service.getName() + ": " + msg);
	}

	private static void fail(Class service, URL u, int line, String msg) throws ServiceConfigurationError {
		fail(service, u + ":" + line + ": " + msg);
	}

	// Parse a single line from the given configuration file, adding the name
	// on the line to the names list.
	//
	private int parseLine(Class service, URL u, BufferedReader r, int lc, List<String> names)
			throws IOException, ServiceConfigurationError {
		String ln = r.readLine();
		if (ln == null) {
			return -1;
		}
		int ci = ln.indexOf('#');
		if (ci >= 0)
			ln = ln.substring(0, ci);
		ln = ln.trim();
		int n = ln.length();
		if (n != 0) {
			if ((ln.indexOf(' ') >= 0) || (ln.indexOf('\t') >= 0))
				fail(service, u, lc, "Illegal configuration-file syntax");
			if (!localExtensionInstances.containsKey(ln) && !names.contains(ln))
				names.add(ln);
		}
		return lc + 1;
	}

	// Parse the content of the given URL as a provider-configuration file.
	//
	// @param service
	// The service type for which EXTENSION_INSTANCES are being sought;
	// used to construct error detail strings
	//
	// @param u
	// The URL naming the configuration file to be parsed
	//
	// @return A (possibly empty) iterator that will yield the provider-class
	// names in the given configuration file that are not yet members
	// of the returned set
	//
	// @throws ServiceConfigurationError
	// If an I/O error occurs while reading from the given URL, or
	// if a configuration-file format error is detected
	//
	private Iterator<String> parse(Class service, URL u) throws ServiceConfigurationError {
		InputStream in = null;
		BufferedReader r = null;
		ArrayList<String> names = new ArrayList<>();
		try {
			in = u.openStream();
			r = new BufferedReader(new InputStreamReader(in, "utf-8"));
			int lc = 1;
			while ((lc = parseLine(service, u, r, lc, names)) >= 0)
				;
		} catch (IOException x) {
			fail(service, "Error reading configuration file", x);
		} finally {
			try {
				if (r != null)
					r.close();
				if (in != null)
					in.close();
			} catch (IOException y) {
				fail(service, "Error closing configuration file", y);
			}
		}
		return names.iterator();
	}

	// Private inner class implementing fully-lazy provider lookup
	//
	private class LazyIterator implements Iterator<S> {

		Class<S> service;
		ClassLoader loader;
		Enumeration<URL> configs = null;
		Iterator<String> pending = null;
		String nextName = null;

		private LazyIterator(Class<S> service, ClassLoader loader) {
			this.service = service;
			this.loader = loader;
		}

		public boolean hasNext() {
			if (nextName != null) {
				return true;
			}
			if (configs == null) {
				try {
					String fullName = PREFIX + service.getName();
					if (loader == null)
						configs = ClassLoader.getSystemResources(fullName);
					else
						configs = loader.getResources(fullName);
				} catch (IOException x) {
					fail(service, "Error locating configuration files", x);
				}
			}
			while ((pending == null) || !pending.hasNext()) {
				if (!configs.hasMoreElements()) {
					return false;
				}
				pending = parse(service, configs.nextElement());
			}
			nextName = pending.next();
			return true;
		}

		public S next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			if (nextName.length() > 0 && nextName.lastIndexOf("=") < 1)
				fail(service, "Provider " + nextName + " '=' not found");
			String cn = nextName.substring(nextName.lastIndexOf("=") + 1);
			String cn_Key = nextName.substring(0, nextName.lastIndexOf("="));

			nextName = null;
			Class<?> c = null;
			try {
				c = Class.forName(cn, false, loader);
			} catch (ClassNotFoundException x) {
				fail(service, "Provider " + cn + " not found");
			}
			if (!service.isAssignableFrom(c)) {
				fail(service, "Provider " + cn + " not a subtype");
			}
			try {
				Object instance = c.newInstance();
				S p = service.cast(instance);
				// EXTENSION_INSTANCES.putIfAbsent(cn, p);
				localExtensionInstances.putIfAbsent(cn_Key, instance);
				EXTENSION_INSTANCES.putIfAbsent(cn_Key, instance);
				SERVICE_URL.putIfAbsent(cn_Key, service);
				localServiceUrl.putIfAbsent(cn_Key, service);
				return p;
			} catch (Throwable x) {
				fail(service, "Provider " + cn + " could not be instantiated", x);
			}
			throw new Error(); // This cannot happen
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

	}

	/**
	 * Lazily loads the available EXTENSION_INSTANCES of this loader's service.
	 * 
	 * <p>
	 * The iterator returned by this method first yields all of the elements of
	 * the provider cache, in instantiation order. It then lazily loads and
	 * instantiates any remaining EXTENSION_INSTANCES, adding each one to the
	 * cache in turn.
	 * 
	 * <p>
	 * To achieve laziness the actual work of parsing the available
	 * provider-configuration files and instantiating EXTENSION_INSTANCES must
	 * be done by the iterator itself. Its {@link java.util.Iterator#hasNext
	 * hasNext} and {@link java.util.Iterator#next next} methods can therefore
	 * throw a {@link ServiceConfigurationError} if a provider-configuration
	 * file violates the specified format, or if it names a provider class that
	 * cannot be found and instantiated, or if the result of instantiating the
	 * class is not assignable to the service type, or if any other kind of
	 * exception or error is thrown as the next provider is located and
	 * instantiated. To write robust code it is only necessary to catch
	 * {@link ServiceConfigurationError} when using a service iterator.
	 * 
	 * <p>
	 * If such an error is thrown then subsequent invocations of the iterator
	 * will make a best effort to locate and instantiate the next available
	 * provider, but in general such recovery cannot be guaranteed.
	 * 
	 * <blockquote style="font-size: smaller; line-height: 1.2"><span style=
	 * "padding-right: 1em; font-weight: bold">Design Note</span> Throwing an
	 * error in these cases may seem extreme. The rationale for this behavior is
	 * that a malformed provider-configuration file, like a malformed class
	 * file, indicates a serious problem with the way the Java virtual machine
	 * is configured or is being used. As such it is preferable to throw an
	 * error rather than try to recover or, even worse, fail
	 * silently.</blockquote>
	 * 
	 * <p>
	 * The iterator returned by this method does not support removal. Invoking
	 * its {@link java.util.Iterator#remove() remove} method will cause an
	 * {@link UnsupportedOperationException} to be thrown.
	 * 
	 * @return An iterator that lazily loads EXTENSION_INSTANCES for this
	 *         loader's service
	 */
	public Iterator<S> iterator() {
		return new Iterator<S>() {

			Iterator<Map.Entry<String, Object>> knownExtensionInstances = localExtensionInstances.entrySet().iterator();

			public boolean hasNext() {
				if (knownExtensionInstances.hasNext())
					return true;
				return lookupIterator.hasNext();
			}

			public S next() {
				if (knownExtensionInstances.hasNext())
					return (S) knownExtensionInstances.next().getValue();
				return lookupIterator.next();
			}

			public void remove() {
				throw new UnsupportedOperationException();
			}

		};
	}

	/**
	 * Creates a new service loader for the given service type and class loader.
	 * 
	 * @param service
	 *            The interface or abstract class representing the service
	 * 
	 * @param loader
	 *            The class loader to be used to load provider-configuration
	 *            files and provider classes, or <tt>null</tt> if the system
	 *            class loader (or, failing that, the bootstrap class loader) is
	 *            to be used
	 * 
	 * @return A new service loader
	 */
	public static <S> ExtensionLoader<S> load(Class<S> service, ClassLoader loader) {
		return new ExtensionLoader<>(service, loader);
	}

	/**
	 * Creates a new service loader for the given service type, using the
	 * current thread's {@linkplain java.lang.Thread#getContextClassLoader
	 * context class loader}.
	 * 
	 * <p>
	 * An invocation of this convenience method of the form
	 * 
	 * <blockquote>
	 * 
	 * <pre>
	 * ServiceLoader.load(<i>service</i>)
	 * </pre>
	 * 
	 * </blockquote>
	 * 
	 * is equivalent to
	 * 
	 * <blockquote>
	 * 
	 * <pre>
	 * ServiceLoader.load(<i>service</i>,
	 *                    Thread.currentThread().getContextClassLoader())
	 * </pre>
	 * 
	 * </blockquote>
	 * 
	 * @param service
	 *            The interface or abstract class representing the service
	 * 
	 * @return A new service loader
	 */
	public static <S> ExtensionLoader<S> load(Class<S> service) {
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		return ExtensionLoader.load(service, cl);
	}

	/**
	 * Creates a new service loader for the given service type, using the
	 * extension class loader.
	 * 
	 * <p>
	 * This convenience method simply locates the extension class loader, call
	 * it <tt><i>extClassLoader</i></tt>, and then returns
	 * 
	 * <blockquote>
	 * 
	 * <pre>
	 * ServiceLoader.load(<i>service</i>, <i>extClassLoader</i>)
	 * </pre>
	 * 
	 * </blockquote>
	 * 
	 * <p>
	 * If the extension class loader cannot be found then the system class
	 * loader is used; if there is no system class loader then the bootstrap
	 * class loader is used.
	 * 
	 * <p>
	 * This method is intended for use when only installed EXTENSION_INSTANCES
	 * are desired. The resulting service will only find and load
	 * EXTENSION_INSTANCES that have been installed into the current Java
	 * virtual machine; EXTENSION_INSTANCES on the application's class path will
	 * be ignored.
	 * 
	 * @param service
	 *            The interface or abstract class representing the service
	 * 
	 * @return A new service loader
	 */
	public static <S> ExtensionLoader<S> loadInstalled(Class<S> service) {
		ClassLoader cl = ClassLoader.getSystemClassLoader();
		ClassLoader prev = null;
		while (cl != null) {
			prev = cl;
			cl = cl.getParent();
		}
		return ExtensionLoader.load(service, prev);
	}

	/**
	 * Returns a string describing this service.
	 * 
	 * @return A descriptive string
	 */
	public String toString() {
		return "java.util.ServiceLoader[" + service.getName() + "]";
	}

}
