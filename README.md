Javalin SSCCE
=============

Javalin's websockets functionality does not seem to be usable from Scala.

## The bug

The `message` method on `WsMessageContext` is not callable from
Scala. Compiling this project gives the following error:

```
[error] <...>/javalin-sscce/src/main/scala/com/adamnfish/Main.scala:11:14: ambiguous reference to overloaded definition,
[error] both method message in class WsMessageContext of type [T]()T
[error] and  method message in class WsMessageContext of type ()String
[error] match argument types ()
[error]         wctx.message()
[error]              ^
[error] one error found
[error] (Compile / compileIncremental) Compilation failed
```

This appears to be because the type signature of the plain `message`
method and the inline (reified) `message` method are indistinguishable
to the Scala compiler.

### Reproduction

Compile this project:

```
// from the project root
$ sbt compile
```

Observe the compile error.

## Alternative bug

Line 15 is an alternative implementation that explicitly calls the
inline / reified method with the type fixed to `String`. This version
compiles, but crashes at runtime.

```
java.lang.UnsupportedOperationException: This function has a reified type parameter and thus can only be inlined at compilation time, not called directly.
	at kotlin.jvm.internal.Intrinsics.throwUndefinedForReified(Intrinsics.java:202)
	at kotlin.jvm.internal.Intrinsics.throwUndefinedForReified(Intrinsics.java:196)
	at kotlin.jvm.internal.Intrinsics.reifiedOperationMarker(Intrinsics.java:206)
	at io.javalin.websocket.WsMessageContext.message(WsContext.kt:85)
	at com.adamnfish.Main$.$anonfun$main$2(Main.scala:12)
    ... etc
```

### Reproduction

1. Comment out line 14 (which does not compile)
2. Uncomment line 15
3. Run the server
4. Connect to the server using wscat or similar
5. Send a message and observe the stack trace

Observe the runtime exception, which will be logged to the terminal.

To run the server, execute the following command from the root of
the project.

```
$ sbt run
```

To connect to the server using wscat, execute the following command
from another terminal:

```
$ wscat --connect localhost:7000
```

## Example project layout

This Scala project contains a single source code file, which is a
"Main" to run a Javalin websocket server.

[Main class](src/main/scala/com/adamnfish/Main.scala)

The build pulls in the latest version of Javalin (`3.8.0` at time of
writing).

[build file](build.sbt)

## Recommendation

Using a different name for the plain message method (either instead of
or in addition to the existing one) would allow Scala to select the
correct method.
