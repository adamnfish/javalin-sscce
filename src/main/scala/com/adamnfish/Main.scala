package com.adamnfish

import io.javalin.Javalin

object Main {
  def main(args: Array[String]): Unit = {
    val app = Javalin.create().start(7000)

    app.ws("/", { ws =>
      ws.onMessage { wctx =>
        wctx.message()
//        wctx.message[String]()
      }
    })

    Runtime.getRuntime.addShutdownHook(new Thread(() => {
      app.stop()
    }))
  }
}