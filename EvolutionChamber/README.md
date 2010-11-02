## Build-dependencies

To build, you need:

JGAP
:	http://jgap.sourceforge.net/
:	debian package non-existent, you'll have to download from above and put
	jgap.jar into lib/

SwingX
:	https://swingx.dev.java.net/
:	debian package `libswingx-java`

JGoodies Looks
:	http://www.jgoodies.com/
:	debian package `libjgoodies-looks-java`

Apache Commons Collections 3
:	http://commons.apache.org/collections/
:	debian package `libcommons-collections3-java`

## Build

$ ant package

This will create evolutionchamber{,-nodep}.jar in dist/. The former is a 
library with only this package's classes in, the latter is a standalone JAR
containing all the dependencies too, which you can run with `java -jar`.
