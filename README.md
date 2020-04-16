Javalin SSCCE
=============

Javalin's websockets functionality does not seem to be usable from Scala.

## The bug

The `message` method on `WsMessageContext` is not callable from
Scala. Compiling this project gives the following error:

```
[error] <filename-and-location> ambiguous reference to overloaded definition,
[error] both method message in class WsMessageContext of type [T]()T
[error] and  method message in class WsMessageContext of type ()String
[error] match expected type ?
[error]         wctx.message
```

This appears to be because the type signature of the plain `message`
method and the inline (reified) `message` method are indistinguishable
to the Scala compiler.

## Reproduction

Compile this project:

```
// from the project root
sbt compile
```

## Details

This Scala project contains a single source code file, which is a
"Main" to run a Javalin websocket server.

[Main class](src/main/com/adamnfish/Main.scala)

The build pulls in the latest version of Javalin (`3.8.0` at time of
writing).

[build file](build.sbt)

## Recommendation

Using a different name for the plain message method (either instead of
or in addition to the existing one) would allow Scala to select the
correct method.
